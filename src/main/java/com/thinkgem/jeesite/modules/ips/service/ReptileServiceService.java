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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.utils.DictUtils;
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

import static com.thinkgem.jeesite.modules.sys.utils.DictUtils.getDictList;

/**
 * 爬虫容器
 * @author zhangsy
 * @version 2019-01-03
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class ReptileServiceService extends CrudService<ReptileServiceDao, ReptileService> {

    /*private ReptileServiceService() {
    }*/

    private static class SingletonClassInstance {
        private static ZooKeeper zooKeeper = null;

        public static ZooKeeper getInstance() {
            if(zooKeeper==null) {
                synchronized (ZooKeeper.class) {
                    if (zooKeeper == null||!zooKeeper.getState().isConnected()) {
                        try {
                            zooKeeper = ZookeeperSession.getZooKeeper();
                        } catch (Exception e) {
                            e.printStackTrace();
                            //logger.error("",e);
                            throw  new RuntimeException("连接Zookeeper失败");
                        }
                    }

                }
            }

            return zooKeeper;
        }

    }

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
           ZooKeeper zooKeeper= SingletonClassInstance.getInstance();
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
             AssertUtil.notEmpty(URLPath,"zookeeper没有找到子节点,该服务不可用");

//             logger.debug("zookeeper操作路径{}",partntRoot+URLPath);
//             zooKeeper.setData(partntRoot+"/"+URLPath,data.getBytes(),-1);
             String res=ClintUtil.postClint(URLDecoder.decode(URLPath,"utf-8"),data);
             PyRes pyRes=new Gson().fromJson(res,PyRes.class);
             logger.debug("请求路径:{}请求内容:{}",URLDecoder.decode(URLPath,"utf-8"),data);
             logger.debug("服务器响应:{} ",res);
             if(pyRes==null||false==pyRes.getSuccess())throw new RuntimeException("通知应用服务器失败,服务器响应"+res);
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

   public void serviceConfig(String ip){

       Map map=getConfig();
       map.put("serviceIp",ip);
       updateServerByZookeaper(ip,"service_config",new Gson().toJson(map));
   }
   public Map getConfig(){
       Map map=new HashMap();
       List<Dict> dlIp=DictUtils.getDictList("dl_ip");
       List<String> dllist=new ArrayList<String>();
       for (Dict d:dlIp){
           dllist.add(d.getLabel());
       }
       map.put("proxy_ips",dllist);
       map.put("ftp_ip",DictUtils.getDictLabel("ftp_ip","ftp","业务字典中未找到ftp_ip"));
       map.put("ftp_port",DictUtils.getDictLabel("ftp_port","ftp","业务字典中未找到ftp_port"));
       map.put("ftp_uname",DictUtils.getDictLabel("ftp_uname","ftp","业务字典中未找到ftp_uname"));
       map.put("ftp_upwd",DictUtils.getDictLabel("ftp_upwd","ftp","业务字典中未找到ftp_upwd"));
       return map;
   }
    public List<ReptileService> getServer() {
        List<ReptileService> rsList=new ArrayList<ReptileService>();
        ZooKeeper zooKeeper= SingletonClassInstance.getInstance();
        try {
            //zooKeeper = ZookeeperSession.getZooKeeper();
            List<String> listPath=new ArrayList<String>();
            listPath=zooKeeper.getChildren(ZookeeperSession.rootPath,null);//root
            for (String ss:listPath){
                ReptileService rs=new ReptileService();
                rs.setServiceIp(ss);
                rs.setServiceName(ss);
                List<String> child=zooKeeper.getChildren(ZookeeperSession.rootPath+"/"+ss+"/restapi",null);
                rs.setServiceState("失去连接");
                if(child==null||child.size()==0){

                }else {
                    rs.setServiceState("注册成功");
                }
                rsList.add(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("",e);
            throw  new RuntimeException("设置Zookeeper失败");
        }
        return  rsList;
    }
}