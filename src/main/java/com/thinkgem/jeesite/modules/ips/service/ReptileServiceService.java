package com.thinkgem.jeesite.modules.ips.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.modules.zookeeper.ClintUtil;
import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.ReptileService;
import com.thinkgem.jeesite.modules.ips.dao.ReptileServiceDao;

/**
 * 服务器管理Service
 * @author zhangsy
 * @version 2019-01-03
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class ReptileServiceService extends CrudService<ReptileServiceDao, ReptileService> {

    /**
     * @Author zhangsy
     * @Description  ReptileServiceService /
     * @Date 14:39 2019/1/7
     * @Param [server:第一个节点,action: 执行的操作,data:数据]
     * @return java.util.List<com.thinkgem.jeesite.modules.ips.entity.ReptileService>
     * @Company 重庆尚渝网络科技
     * @version v1000
     **/
    public List<ReptileService> getServerByZookeaper(String server,String action,String data){

        try {

            String partntRoot="";
            String URLPath="";
           ZooKeeper zooKeeper= ZookeeperSession.getZooKeeper();
            List<String> listPath=new ArrayList<String>();
            listPath=zooKeeper.getChildren(ZookeeperSession.rootPath,null);//root
            for (String ss:listPath){
                String path=ZookeeperSession.rootPath+"/"+ss+"/"+"restapi";
                partntRoot=path;
                List<String> actionList=zooKeeper.getChildren(path,null);
                AssertUtil.notEmpty(actionList,path+"下没有获取到子节点");
                for (String ssC:actionList){
                    if(ssC.contains(action)){
                        URLPath=ssC;
                    }
                }
            logger.debug("zook获取的path {}",listPath);
             }
             AssertUtil.notEmpty(partntRoot);
             AssertUtil.notEmpty(URLPath,"zookeeper没有找到子节点");

             logger.debug("zookeeper操作路径{}",partntRoot+URLPath);

             zooKeeper.setData(partntRoot+"/"+URLPath,data.getBytes(),-1);
             String res=ClintUtil.postClint(URLDecoder.decode(URLPath),data);
             if(res.contains("no"))throw new RuntimeException("通知应用服务器失败,服务器响应"+res);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("连接Zookeeper失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("设置Zookeeper失败");
        }
        return null;
    }
}