package com.thinkgem.jeesite.modules.ips.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.Encoding;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ips.entity.ReptileTask;
import com.thinkgem.jeesite.modules.zookeeper.ClintUtil;
import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import com.thinkgem.jeesite.modules.zookeeper.py.PyRes;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
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

    @Autowired
    private CrawlerService crawlerService;
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
        Map task_bind_db=crawlerService.ParserJsonToPk(StringUtils.readToString(Global.getUserfilesBaseDir()+task.getCrawlerId().getCrawlerUrl()),task.getTableName());

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

}