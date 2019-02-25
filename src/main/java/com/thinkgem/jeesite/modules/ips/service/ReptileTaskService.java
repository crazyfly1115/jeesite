package com.thinkgem.jeesite.modules.ips.service;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.*;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.modules.gen.entity.GenTableColumn;
import com.thinkgem.jeesite.modules.ips.entity.*;
import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.method.P;
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
@Lazy(false)
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

        //判断表名,作为规则限制 主表必须带一个下划线 且不能再首尾
        int  num=StringUtils.countMatches(entity.getTableName(),"_");
        if(num!=1)throw  new RuntimeException("表名有且只能有一个下划线");
        if(StringUtils.endsWith(entity.getTableName(),"_"))throw  new RuntimeException("表名下划线不能为在结束位置");
        if(!StringUtils.strIsRigthTable(entity.getTableName()))throw  new RuntimeException("表名只能由下划线字母数字组成");

        Crawler crawler=crawlerService.get(entity.getCrawlerId().getId());


        String json=crawler.getCrawlerJson();//StringUtils.readToString(Global.getUserfilesBaseDir()+crawler.getCrawlerUrl());

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

//        overidCw(entity,entity.getPoiType());
        //这里的任务虽然有爬虫Json的配置文件字段,但是并没有使用,最后看定论
        //观点:cw文件修改后爬虫任务需要重新新增不

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
            //  暂时将py 地址写死,等正式后将参数修改
            map.put("python_file",Global.getUserfilesWebUrl()+map.get("python_file"));
            map.put("task_status",true);
            reptileService.updateServerByZookeaper(serviceTask.getServiceIp(),"task_add",new GsonBuilder()
                    .serializeNulls()
                    .create().toJson(map));

        }

    }

    public void deleteTask(String id,int state) {
        ReptileTask reptileTask=get(id);
        AssertUtil.notNull(reptileTask,"未查询到相关数据");
        Map map=getTask(reptileTask.getId());
        for (ServiceTask serviceTask:reptileTask.getServiceList()){
            Map rs=new HashMap();
            rs.put("task_name",map.get("task_name"));
            rs.put("schedule_type",state);
            rs.put("task_id",map.get("task_id"));
            reptileService.updateServerByZookeaper(serviceTask.getServiceIp(),"task_schedule",new GsonBuilder()
                    .serializeNulls()
                    .create().toJson(rs));
        }

        storageService.deleteDataInput(reptileTask);
    }

    @Override
    public void deleteByIds(String[] ids) {
        for (String id:ids){
            //删除爬虫任务
            deleteTask(id,2);
            //删除容器中的任务

            dao.deleteById(id, DataEntity.DEL_FLAG_DELETE);
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
        map.put("task_config",overidCw(dao.get(id)));
        return map;
    }
    /**
     *
     * 覆盖cw 文件中的分类
     * 暂时:
     **/
    private JsonElement overidCw(ReptileTask reptileTask){
        String[] keys=StringUtils.split(reptileTask.getPoiType(),",");
        JsonArray ar=new JsonArray();
        for (String key:keys){
            ar.add(key);
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonElement=parser.parse(reptileTask.getCrawlerId().getCrawlerJson());
        String type= jsonElement.getAsJsonObject().get("task_type").getAsString();


        String webUrl=jsonElement.getAsJsonObject().get("inlet").getAsString();

        AssertUtil.notNull(type,"cw文件中没有找到task_type");
        AssertUtil.notNull(webUrl,"cw文件中没有找到网站url");


        if(WebsiteService.GDPOI.equals(type)||WebsiteService.BDPOI.equals(type)){
            reptileTask.setWebsiteUrl(webUrl);
            jsonElement.getAsJsonObject().get("poi_kword").getAsJsonArray().addAll(ar);
        }
        if(WebsiteService.GENERAL.equals(type)){//通用网站处理
            AssertUtil.notEmpty(reptileTask.getWebsiteUrl(),"通用爬虫时爬取链接不能为空");
            jsonElement.getAsJsonObject().get("keyword").getAsJsonArray().addAll(ar);
            jsonElement.getAsJsonObject().addProperty("inlet",reptileTask.getWebsiteUrl());
        }
        return jsonElement.getAsJsonObject();

    }
    /*
     *
     * @version v1000
     * 获取zk 上的爬虫状态
     **/
    public List getTaskState(String id) {
        ReptileTask reptileTask=get(id);
        AssertUtil.notNull(reptileTask,"未查询到相关任务");
        List<Map> list=new ArrayList<Map>();
        for (ServiceTask serviceTask:reptileTask.getServiceList()){

            try {
                String path=ZookeeperSession.rootPath+"/"+serviceTask.getServiceIp()+"/tasks/"+reptileTask.getId();
                String json=new String(ZookeeperSession.getZooKeeper().getData(path,false,new Stat()));
                logger.debug("获取任务的JSOn:"+json);
                json=json.substring(json.indexOf("{"),json.length());
                logger.debug("获取任务的格式化JSOn:"+json);
                Map map=new  GsonBuilder().create().fromJson(json,Map.class);
                Map rs=new HashMap();
                String state=map.get("status").toString();
                if(state.equals("START"))state="执行中";
                if(state.equals("WARN"))state="异常结束";
                if(state.equals("STOP"))state="正常结束";
                rs.put("执行状态",state);
                rs.put("爬取数量",(Double)map.get("excute_count"));
                list.add(rs);
            } catch (KeeperException e) {
                if(e instanceof KeeperException.NoNodeException){
                    throw  new RuntimeException("该任务已经下线,请重新部署后查看");
                }
                e.printStackTrace();
                throw  new RuntimeException("获取Zk数据异常,请检查zk服务器KeeperException" );
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw  new RuntimeException("获取Zk数据异常,请检查zk服务器 InterruptedException");
            }
        }
        return  list;
    }
    /***
     同步Zk 上的数据
     * @version v1000
     **/
    @Scheduled(cron = "0/30 * * * * ?")
    public  void saveTaskData(){
        //todo 回写zk的数据到数据库
        try {
            List<String> serverList=ZookeeperSession.getZooKeeper().getChildren(ZookeeperSession.rootPath,false);
            for (String server:serverList){
                String taskPath=ZookeeperSession.rootPath+"/"+server+"/tasks";
                List<String> taskList=ZookeeperSession.getZooKeeper().getChildren(taskPath,false);
                for (String taskId:taskList){
                    String json=new String(ZookeeperSession.getZooKeeper().getData(taskPath+"/"+taskId,false,new Stat()));
                    logger.debug("获取任务的Json:"+json);
                    json=json.substring(json.indexOf("{"),json.length());
                    logger.debug("获取任务的格式化Json:"+json);
                    Map map=new  GsonBuilder().create().fromJson(json,Map.class);
                    logger.debug("同步zk数据节点返回");
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
            logger.error(reptileService.getClass().getName(),e);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error(reptileService.getClass().getName(),e);
        }

    }
    /*
     *根据任务id获取当前表的字段数据,包含多级字段
     * @version v1000
     **/
    public List getColumnByid(List<GenTableColumn> columnList,String id ,String tableName){
        ReptileTask reptileTask=get(id);
        List<Map> rsList=new ArrayList<Map>();

        List<Subitem> list=crawlerService.getSubitem(reptileTask.getCrawlerId().getCrawlerJson());
        for (GenTableColumn ctc:columnList){
            Map map=new HashMap();
            map.put("name",ctc.getName());
            map.put("fieldName",ctc.getComments());
            map.put("fieldLength",ctc.getDataLength());
            map.put("childTable","1");
            if( StringUtils.countMatches(tableName,"_")==1){//表示是第一层
                for (Subitem s:list){
                    if(s.getSubitem()!=null&&s.getName().equals(ctc.getName())){
                        map.put("childTable","0");
                    }
                }
            }
            rsList.add(map);
        }

        return rsList;
    }

}