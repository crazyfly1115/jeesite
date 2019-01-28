package com.thinkgem.jeesite.modules.ips.service;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.ips.entity.*;
import org.activiti.engine.TaskService;
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

    @Autowired
    private  StorageServiceService storageService;
    @Override
    @Transactional
    public void save(ReptileTask entity) {
        // TODO 1.验证是否选择数据库,以及是否选择数据表,以及是否选择爬虫策略.
        AssertUtil.notNull(entity.getCrawlerId(),"请选择至少一个爬虫");
        AssertUtil.notNull(entity.getDatabaseId(),"请选择一个存储数据服务");

        AssertUtil.notNull(entity.getCrawlerId().getId(),"爬虫服务id为空");
        AssertUtil.notNull(entity.getDatabaseId().getId(),"存储数据id为空");


        AssertUtil.notNull(entity.getTableName(),"表名不能为空");
        Crawler crawler=crawlerService.get(entity.getCrawlerId().getId());


        String json=StringUtils.readToString(Global.getUserfilesBaseDir()+crawler.getCrawlerUrl());

        Database database=databaseService.get(entity.getDatabaseId().getId());
        if(duridService.existTable(database,entity.getTableName())){
            logger.debug("cw:文件内容{}",json);

            List<String> sqls=crawlerService.ParserJsonToSql(json,entity.getTableName(),true);
            for (String sql : sqls){

                logger.debug("任务执行建表SQL:{}",sql);
                duridService.createTable(database,sql);
            }
        }

        //替换cw 文件中的关键字
        //并生成文件

        overidCw(entity,entity.getPoiType());

        super.save(entity);
        serviceTaskService.deleteByTaskId(entity.getId());
        for (ServiceTask rs:entity.getServiceList()){
            ServiceTask sc=new ServiceTask();
            sc.setTaskId(entity.getId());
            sc.setServiceIp(rs.getServiceIp());
            serviceTaskService.save(sc);
        }
    }
    /**
     * 启动一个爬虫任务
     * @param
     * @return
     */
    public void startTask(ReptileTask reptileTask){

        //todo 1.先调用存储服务接口,2在调用爬虫容器接口

        //赋值未完成
        reptileTask=get(reptileTask.getId());


        storageService.noticeDataInput(reptileTask);

        Map map=getTask(reptileTask.getId());

        if(reptileTask.getServiceList()==null||reptileTask.getServiceList().size()==0) throw new RuntimeException("该任务未指定爬虫服务");
        //
        for (ServiceTask serviceTask:reptileTask.getServiceList()){
            //todo  暂时将py 地址写死,等正式后将参数修改
            map.put("python_file","/python/poi/v1/stcrawler.zip");
            reptileService.updateServerByZookeaper(serviceTask.getServiceIp(),"task_add",new JsonMapper(JsonInclude.Include.ALWAYS).toJson(map));

        }

    }

    public void changeState(ReptileTask reptileTask,int state) {
        reptileTask=get(reptileTask.getId());
        Map map=getTask(reptileTask.getId());
        for (ServiceTask serviceTask:reptileTask.getServiceList()){
            Map rs=new HashMap();
            rs.put("task_name",map.get("task_name"));
            rs.put("schedule_type",state);
            rs.put("task_id",map.get("task_id"));



            reptileService.updateServerByZookeaper(serviceTask.getServiceIp(),"task_schedule",new JsonMapper(JsonInclude.Include.ALWAYS).toJson(rs));
        }
    }

    @Override
    public ReptileTask get(String id) {
        ReptileTask reptileTask=super.get(id);
        ServiceTask serviceTask=new ServiceTask();
        serviceTask.setTaskId(id);
        reptileTask.setServiceList(serviceTaskService.findList(serviceTask));
        reptileTask.setDatabaseId(databaseService.get(reptileTask.getDatabaseId().getId()));
        reptileTask.setCrawlerId(crawlerService.get(reptileTask.getCrawlerId().getId()));
        return super.get(id);
    }

    public  Map getTask(String id){
        Map map=dao.getTask(id);
        map.put("task_file",Global.getUserfilesWebUrl()+map.get("task_file"));
        return map;
    }
    /**
     *
     * 覆盖cw 文件中的分类
     **/
    private void overidCw(ReptileTask reptileTask,String keyword){
        String[] keys=StringUtils.split(keyword,",");
        JsonArray ar=new JsonArray();
        for (String key:keys){
            ar.add(key);
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonElement=parser.parse(StringUtils.readToString(Global.getUserfilesBaseDir()+reptileTask.getCrawlerId().getCrawlerUrl()));
        String type= jsonElement.getAsJsonObject().get("task_type").getAsString();


        String webUrl=jsonElement.getAsJsonObject().get("inlet").getAsString();

        AssertUtil.notNull(type,"cw文件中没有找到task_type");
        AssertUtil.notNull(webUrl,"cw文件中没有找到网站url");
        reptileTask.setWebsiteUrl(webUrl);

        if(WebsiteService.GDPOI.equals(type)||WebsiteService.BDPOI.equals(type)){
            jsonElement.getAsJsonObject().get("poi_kword").getAsJsonArray().addAll(ar);

            //将上传文件保存到一个目标文件当中
            Calendar date = Calendar.getInstance();
            String newfileName=DateUtils.getDate("yyyyMMddHHmmssSSS")+".cw";
            String newpath=date.get(Calendar.YEAR) + File.separator + (date.get(Calendar.MONTH)+1) + File.separator+ date.get(Calendar.DAY_OF_MONTH)+File.separator;
            File newfile=new File(Global.getUserfilesBaseDir() + File.separator +newpath);
            if(!newfile.exists()){
                newfile.mkdirs();
            }
            String path=Global.getUserfilesBaseDir() + File.separator +newpath+ newfileName;
            FileUtils.writeToFile(path,new Gson().toJson(jsonElement),false);
            reptileTask.setCrawlerUrl(newpath.replaceAll("\\\\","/")+ newfileName);
        }else{
            reptileTask.setCrawlerUrl(reptileTask.getCrawlerId().getCrawlerUrl());
        }


    }
}