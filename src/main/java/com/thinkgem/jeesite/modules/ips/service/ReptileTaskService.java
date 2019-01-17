package com.thinkgem.jeesite.modules.ips.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.Encoding;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ips.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.dao.ReptileTaskDao;

/**
 * 爬虫任务Service
 * @author zhangsy
 * @version 2019-01-04
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class ReptileTaskService extends CrudService<ReptileTaskDao, ReptileTask> {
    @Autowired
    private ServiceTaskService serviceTaskService;

    @Autowired
    private ReptileServiceService reptileService;
    @Autowired
    private CollectFieldService collectFieldService;
    @Autowired
    private  CrawlerService crawlerService;
    @Autowired
    private  DatabaseService databaseService;
    @Autowired
    private DuridService duridService;
    @Override
    @Transactional
    public void save(ReptileTask entity) {
        // TODO 1.验证是否选择数据库,以及是否选择数据表,以及是否选择爬虫策略.
        AssertUtil.notNull(entity.getCrawlerId(),"请选择至少一个爬虫");
        AssertUtil.notNull(entity.getDatabaseId(),"请选择一个存储数据服务");

        AssertUtil.notNull(entity.getCrawlerId().getId(),"爬虫服务id为空");
        AssertUtil.notNull(entity.getDatabaseId().getId(),"存储数据id为空");

        Crawler crawler=crawlerService.get(entity.getCrawlerId().getId());
        String json=StringUtils.readToString(Global.getUserfilesBaseDir()+crawler.getCrawlerUrl());
        System.out.println(json);


        Database database=databaseService.get(entity.getDatabaseId().getId());
        duridService.createTable(database);



        super.save(entity);
        serviceTaskService.deleteByTaskId(entity.getId());
        for (ReptileService rs:entity.getServiceList()){
            ServiceTask sc=new ServiceTask();
            sc.setTaskId(entity.getId());
            sc.setServiceId(rs.getId());
            serviceTaskService.save(sc);
        }
    }
    public void taskAdd(ReptileTask reptileTask){

        List<Map> list=dao.getTask(reptileTask.getId());
        if(list==null||list.size()==0) throw new RuntimeException("该任务指定爬虫服务");
        for (Map map:list){

          List<CollectField> collectFieldList= collectFieldService.findListByTableId(map.get("table_id").toString());
          List<String> pks=new ArrayList<String>();
          List<String> columns=new ArrayList<String>();
          for (CollectField collectField:collectFieldList){
              columns.add(collectField.getFieldCode());
              if("1".equals(collectField.getIsUpdate())){
                pks.add(collectField.getFieldCode());
              }
          }
            Map db=(Map) map.get("task_bind_db");
            db.put("pk",pks);
            db.put("columns",columns);

            map.put("task_bind_db",db);
            map.put("task_topic","TaskTopic");
            map.put("task_tag","TaskTag");
            map.put("poi_kword",dao.getKeyWords());
            reptileService.updateServerByZookeaper((String)map.get("service_name"),"task_add",new JsonMapper(JsonInclude.Include.ALWAYS).toJson(map));

//        String a="{\n" +
//                "    \"task_id\": \"task_id20190117zyxy\",\n" +
//                "    \"task_name\": \"百度POI-职业学院\",\n" +
//                "    \"task_url\": \"http://api.map.baidu.com/place/v2/search?scop=2&query=%e7%be%8e%e9%a3%9f&region=chongqing&output=json&ak=Ucmrifav2HUpUFp3FgFbTEkk4NGEuWTo&city_limit=true&page_num=0\",\n" +
//                "    \"task_file\": \"/poi/baidu/v1.0/baidupoi.cw\",\n" +
//                "    \"task_stime\": \"0/20 * * * * ?\",\n" +
//                "    \"task_topic\":\"TaskTopic\",\n" +
//                "    \"task_tag\":\"TaskTag\",\n" +
//                "    \"task_bind_db\": {\n" +
//                "        \"db_ip\": \"127.0.0.1\",\n" +
//                "        \"db_port\": \"3306\",\n" +
//                "        \"db_name\": \"crawler\",\n" +
//                "        \"db_username\": \"crawler\",\n" +
//                "        \"db_password\": \"crawler\",\n" +
//                "        \"tb_name\": \"tb_baidu_poi_food_01\",\n" +
//                "        \"child_tb\": [{\n" +
//                "                \"field_name\": \"address\",\n" +
//                "                \"child_tb_name\": \"tb_child_address\",\n" +
//                "                \"db_ip\": \"192.168.1.2\",\n" +
//                "                \"db_port\": \"3306\",\n" +
//                "                \"db_name\": \"crawler\",\n" +
//                "                \"db_username\": \"crawler\",\n" +
//                "                \"db_password\": \"crawler\",\n" +
//                "                \"tb_name\": \"tb_baidu_poi_food_01\"\n" +
//                "            },\n" +
//                "            {\n" +
//                "                \"field_name\": \"food_type\",\n" +
//                "                \"child_tb_name\": \"tb_child_food_type\"\n" +
//                "            }\n" +
//                "        ]\n" +
//                "    },\n" +
//                "    \"task_status\": false,\n" +
//                "    \"task_type\": \"BDPOI\",\n" +
//                "    \"poi_key\": \"Ucmrifav2HUpUFp3FgFbTEkk4NGEuWTo\",\n" +
//                "    \"poi_kword\": [\"职业学院\"],\n" +
//                "    \"task_start_time\": \"2019-01-01 00:00:00\",\n" +
//                "    \"retry_times\": 3,\n" +
//                "    \"ftp_dir\": \"\"\n" +
//                "}";
//            System.out.println(Encoding.getEncoding(a));
//            System.out.println(Encoding.getEncoding(new JsonMapper(JsonInclude.Include.ALWAYS).toJson(map)));
//            reptileService.updateServerByZookeaper((String)map.get("service_name"),"task_add",a);
        }

    }

    public void changeState(ReptileTask reptileTask,int state) {
        List<Map> list=dao.getTask(reptileTask.getId());
        for (Map map:list){
            Map rs=new HashMap();
            rs.put("task_name",map.get("task_name"));
            rs.put("schedule_type",state);
            rs.put("task_id",map.get("task_id"));
            reptileService.updateServerByZookeaper((String)map.get("service_name"),"task_schedule",new JsonMapper(JsonInclude.Include.ALWAYS).toJson(rs));
        }
    }
}