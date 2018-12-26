package com.thinkgem.jeesite.modules.zookeeper;


import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ZookeeperSession  implements Watcher {
    private static ZooKeeper zooKeeper;
    private static final String rootPath="/crawler";
    private static final String ZookeeperAdder="113.204.4.244:5573";

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println(watchedEvent.getState());
        if(watchedEvent.getState().equals(Event.KeeperState.SyncConnected)) {
            System.out.println("doit");




        }
        System.out.println("接收内容："+watchedEvent.toString());
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
