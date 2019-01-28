package com.thinkgem.jeesite.modules.zookeeper;


import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZookeeperSession  implements Watcher {
    private static ZooKeeper zooKeeper;
    public static final String rootPath=Global.getConfig("zookeeperRootPath");
    public static final String ZookeeperAdder=Global.getConfig("ZookeeperAdder");
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("doit");
            logger.debug("已经链接上zookeeper,等待执行操作!");
        }
        try {
            List<String> list_crawlr=zooKeeper.getChildren("/crawler",true);
            for (String p:list_crawlr){
                String p1="/crawler/"+p+"/restapi";
                List<String> list_rest_api=zooKeeper.getChildren(p1,true);
                if(list_rest_api==null||list_rest_api.size()==0){
                    logger.debug("{},该节点已经是失去链接",p1);
                }
                for (String api:list_rest_api){
                    String p2=p1+"/"+api;
                    byte[] bytes=zooKeeper.getData(p2,true,null);
                    if(bytes!=null){
                        System.out.println(new String(bytes));
                    }

                    logger.debug("{}节点发生数据变化",p2);
                }
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public  void  createZ(){
            getZooKeeper();
    }
    public static ZooKeeper getZooKeeper() {
        AssertUtil.notNull(ZookeeperAdder);
        try {
            if(zooKeeper!=null&&zooKeeper.getState().isConnected())return  zooKeeper;
            zooKeeper = new ZooKeeper(ZookeeperAdder,5000, new ZookeeperSession());
        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("连接zookeeper失败,无法获取容器数据");
        }
        return zooKeeper;
    }
    public static void main(String[] args) throws KeeperException {
        try {
            zooKeeper = new ZooKeeper(ZookeeperAdder,5000, new ZookeeperSession());
            List<String> listPath=new ArrayList<String>();
            ls(zooKeeper,rootPath,listPath);
            for (String path:listPath){
                if(path.contains("task_add")){

                    zooKeeper.setData(path,"1234".getBytes(),-1);
                    byte[] rs=zooKeeper.getData(path,null,new Stat());
                     System.out.println(new String(rs));
                }
//                System.out.println(path);
//                List<String>  cpaht=zooKeeper.getChildren(rootPath+"/"+path,false);
//                System.out.println(new String(zooKeeper.getData(rootPath+"/"+path+"/restapi",true,new Stat())));
            }
//            System.out.println(zooKeeper.getState());
//            Thread.sleep(5000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ls( ZooKeeper zk ,String path,List<String> all) throws Exception {
        System.out.println(path);
        all.add(path);
        List<String> list = zk.getChildren(path, null);
        //判断是否有子节点
        if (list.isEmpty() || list == null) {
            return;
        }
        for (String s : list) {
                ls(zk,path + "/" + s,all);
        }

    }
}
