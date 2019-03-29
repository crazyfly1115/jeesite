package com.thinkgem.jeesite.modules.ips.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.Encoding;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ips.entity.ReptileTask;
import com.thinkgem.jeesite.modules.ips.entity.RootTask;
import com.thinkgem.jeesite.modules.zookeeper.ClintUtil;
import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import com.thinkgem.jeesite.modules.zookeeper.py.PyRes;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.StorageService;
import com.thinkgem.jeesite.modules.ips.dao.StorageServiceDao;

/**
 * 存储容器
 * @author zhangsy
 * @version 2019-01-16
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class StorageServiceService extends CrudService<StorageServiceDao, StorageService> {

    private final static   String RootPath="/DATAINPUT";
    private static Stat stat = new Stat();

    @Autowired
    private CrawlerService crawlerService;

    public Date transferLongToDate(String dateFormat, String millSec) {

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        long l=Long.parseLong(millSec);
        Date date = new Date(l);
        return date;
     }
    /**
     * 获取zookeeper注册的服务节点
     * @param
     * @return
     */
    public List<StorageService> getListByZookeeper(){
        List<StorageService> list=new ArrayList<StorageService>();
        ZooKeeper zooKeeper=ZookeeperSession.getZooKeeper();
        try {
            List<String> path=zooKeeper.getChildren(RootPath,false);
            C:for (String s:path){
                //没有子节点的暂时隐藏
                List<String> childPath= zooKeeper.getChildren(RootPath+"/"+s,false);
                if(childPath==null||childPath.size()==0){
                    continue C;
                }
                StorageService storageService=new StorageService();
                storageService.setServiceIp(s);
                list.add(storageService);
            }
            return  list;
        } catch (KeeperException e) {
            e.printStackTrace();
            logger.error("zookeeeper 异常",e);
            throw  new RuntimeException("zookeeper异常"+KeeperException.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("zookeeeper 异常",e);
            throw  new RuntimeException("zookeeper异常"+InterruptedException.class);
        }
    }

    /**
     * 获取zookeeper注册的服务节点的详细信息
     * @param
     * @return
     */
    public List<Map<String,String>> getZookeeperMsg(String path){

        ZooKeeper zooKeeper=ZookeeperSession.getZooKeeper();
        List<Map<String,String>> listTask=new ArrayList<Map<String, String>>();
        byte[] rs= new byte[0];
        path=RootPath+"/"+path;
        try {
            rs = zooKeeper.getData(path,null,stat);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(rs!=null){
            String tx=new String(rs);
            logger.debug("获取的zk存储数据:"+tx);
            if (tx.indexOf("[")>-1){
                tx=tx.substring(tx.indexOf("[")+1,tx.length());
                tx=tx.substring(tx.indexOf("["),tx.length());
                logger.debug("格式化后的数据:"+tx);
                Gson gson=new Gson();
                listTask=new Gson().fromJson(tx, new TypeToken<List<Map<String, String>>>(){}.getType());
                //[{"task_status":"ON","task_lastid":"10","task_begin_time":"1548239964982","task_id":"9b9d24e0b8fb4aefb9b9513cc9090472"}]
                if(listTask!=null && listTask.size()>0){
                    return listTask;
                }
            }

        }
        return listTask;
    }
    /**
     * 通知存储服务器
     * @param
     * @return
     */
    public void noticeDataInput( ReptileTask task){
        AssertUtil.notEmpty(task,"任务数据不能为空");
        String name=task.getStorageServiceId();

        //校验该存储容器是否存活
        ZooKeeper zooKeeper=ZookeeperSession.getZooKeeper();
        try {
           List<String> path= zooKeeper.getChildren(RootPath+"/"+name,false);
           if(path==null||path.size()==0)throw  new RuntimeException("该容器节点已经失去连接,请检查存储服务");
        } catch (KeeperException e) {
            e.printStackTrace();
            logger.error("zookeeeper 异常",e);
            throw  new RuntimeException("zookeeper异常"+KeeperException.class);
        } catch (InterruptedException e) {
            e.printStackTrace();
            logger.error("zookeeeper 异常",e);
            throw  new RuntimeException("zookeeper异常"+InterruptedException.class);
        }

        String url="http://"+name.substring(name.lastIndexOf("_")+1,name.length())+"/sjgzimp/taskserver";
        Map map=new HashMap();

        //解析文件 生成主键
        Map task_bind_db=crawlerService.ParserJsonToPk(task.getCrawlerId().getCrawlerJson(),task.getTableName());

        task_bind_db.put("tb_name",task.getTableName());


        map.put("task_bind_db",task_bind_db);

        map.put("task_id",task.getId());
        map.put("db_url",task.getDatabaseId().getDatabaseUrl());
        map.put("db_username",task.getDatabaseId().getLoginUser());
        map.put("db_password",task.getDatabaseId().getLoginPsw());
        map.put("task_status","ON");
        map.put("mq_serveraddr",Global.getConfig("mq_serveraddr"));
        map.put("mq_eachpulltime","10000");
        map.put("mq_nameserveraddr",Global.getConfig("mq_nameserveraddr"));
        String json=new Gson().toJson(map);
        String rs=ClintUtil.postClint(url,json);


        PyRes pyRes=new Gson().fromJson(rs,PyRes.class);
        logger.debug("请求存储服务路径:{}请求内容:{}",url,json);
        logger.debug("服务器响应:{} ",rs);
        if(rs==null)throw  new RuntimeException("请求存储服务器无返回值");
        if(false==pyRes.getSuccess())throw new RuntimeException("通知应用服务器失败,服务器响应"+pyRes.getErrormsg());
    }
    public void deleteDataInput( ReptileTask task){
        AssertUtil.notEmpty(task,"任务数据不能为空");
        String name=task.getStorageServiceId();


        String url="http://"+name.substring(name.lastIndexOf("_")+1,name.length())+"/sjgzimp/taskserver";
        Map map=new HashMap();

        map.put("task_id",task.getId());
        map.put("task_status","STOP");
        String json=new Gson().toJson(map);
        String rs=ClintUtil.postClint(url,json);


        PyRes pyRes=new Gson().fromJson(rs,PyRes.class);
        logger.debug("请求存储服务路径:{}请求内容:{}",url,json);
        logger.debug("服务器响应:{} ",rs);
        if(rs==null)throw  new RuntimeException("请求存储服务器无返回值");
        if(false==pyRes.getSuccess())throw new RuntimeException("通知应用服务器失败,服务器响应"+pyRes.getErrormsg());
    }
}