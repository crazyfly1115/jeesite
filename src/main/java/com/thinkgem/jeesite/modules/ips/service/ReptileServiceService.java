package com.thinkgem.jeesite.modules.ips.service;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.Encoding;
import com.thinkgem.jeesite.modules.zookeeper.ClintUtil;
import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import com.thinkgem.jeesite.modules.zookeeper.py.PyRes;
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
    public List<ReptileService> updateServerByZookeaper(String server,String action,String data){

        try {

            String partntRoot="";
            String URLPath="";
           ZooKeeper zooKeeper= ZookeeperSession.getZooKeeper();
            List<String> listPath=new ArrayList<String>();
//            listPath=zooKeeper.getChildren(ZookeeperSession.rootPath,null);//root
//            for (String ss:listPath){
                String path=ZookeeperSession.rootPath+"/"+server+"/"+"restapi";
                partntRoot=path;
                List<String> actionList=zooKeeper.getChildren(path,null);
                AssertUtil.notEmpty(actionList,path+"下没有获取到子节点");
                for (String ssC:actionList){
                    if(ssC.contains(action)){
                        URLPath=ssC;
                    }
                }
             logger.debug("zook获取的path {}",listPath);
//             }
             AssertUtil.notEmpty(partntRoot);
             AssertUtil.notEmpty(URLPath,"zookeeper没有找到子节点");

             logger.debug("zookeeper操作路径{}",partntRoot+URLPath);
            System.out.println(Encoding.getEncoding(data));
             zooKeeper.setData(partntRoot+"/"+URLPath,data.getBytes(),-1);
             String res=ClintUtil.postClint(URLDecoder.decode(URLPath),data);
             PyRes pyRes=new Gson().fromJson(res,PyRes.class);
             logger.debug("请求路径:{}请求内容:{}",URLDecoder.decode(URLPath),data);
             logger.debug("服务器响应:{}/n 请求内容,{}",res,data);
             if(false==pyRes.getSuccess())throw new RuntimeException("通知应用服务器失败,服务器响应"+res);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("连接Zookeeper失败");
        }catch (RuntimeException e) {
            e.printStackTrace();
            throw  e;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("设置Zookeeper失败"+e.getMessage());
        }
        return null;
    }

    //更新配置信息

   public void serviceConfig(ReptileService reptileService){

       Map map=new HashMap();
       map.put("serviceIp",reptileService.getServiceIp());
       map.put("proxy_ips",reptileService.getProxyServerIp());
       map.put("ftp_ip",reptileService.getFtpIp());
       map.put("ftp_port",reptileService.getFtpPort());
       map.put("ftp_uname",reptileService.getFtpUsername());
       map.put("ftp_upwd",reptileService.getFtpUpwd());

       updateServerByZookeaper(reptileService.getServiceName(),"service_config",new Gson().toJson(map));
   }

    public List<ReptileService> getServer() {
        List<ReptileService> rsList=new ArrayList<ReptileService>();
        ZooKeeper zooKeeper= null;
        try {
            zooKeeper = ZookeeperSession.getZooKeeper();
            List<String> listPath=new ArrayList<String>();
            listPath=zooKeeper.getChildren(ZookeeperSession.rootPath,null);//root
            for (String ss:listPath){
                ReptileService rs=new ReptileService();
                rs.setServiceIp(ss.substring(ss.lastIndexOf("_")+1,ss.length()));
                rs.setServiceName(ss);
                rsList.add(rs);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("连接Zookeeper失败");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("设置Zookeeper失败");
        }
        return  rsList;
    }
}