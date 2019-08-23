package com.thinkgem.jeesite.modules.ips.service;


import com.fcibook.quick.http.QuickHttp;
import com.google.gson.Gson;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ips.dao.ProxyIpsDao;
import com.thinkgem.jeesite.modules.ips.entity.ProxyIps;
import com.thinkgem.jeesite.modules.ips.util.zhima.zhimaRet;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


@Service
@Transactional(readOnly = true)
public class ProxyService extends CrudService<ProxyIpsDao, ProxyIps> {
    //获取代理ip
    @Transactional(readOnly = false)
    public List<Map> getIps(String num, String taskId, String useIp){
        ProxyIps proxyIps=new ProxyIps();
        if(useIp==null)useIp="";
        List<String> listString = Arrays.asList(useIp.split(","));
        proxyIps.setList(listString);
        List<Map> list= dao.getips(proxyIps);
        if(list.size()==0){
            saveIps();
        }
        list= dao.getips(proxyIps);
        logger.debug("{}任务来获取了ip,获取的ip{}",taskId,new Gson().toJson(list));
        return list;
    }
    //
    public void saveIps(){
        String url="http://webapi.http.zhimacangku.com/getip?num=1&type=2&pro=&city=0&yys=0&port=1&time=1&ts=1&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1&regions=";
       String text=new QuickHttp().url(url)
                            .get().text();
       if(StringUtils.isBlank(text))throw  new RuntimeException("获取芝麻代理IP失败,返回数据为空");
       logger.debug("芝麻返回数据:{}",text);
        zhimaRet zhimaRet=new Gson().fromJson(text, zhimaRet.class);
        if(zhimaRet==null)throw  new RuntimeException("获取芝麻代理IP失败,返回数据为空");
        if(zhimaRet.getCode()==0){
                for (ProxyIps proxyIps:zhimaRet.getData()){
                    proxyIps.setExpireTime(proxyIps.getExpire_time());
                   save(proxyIps);
                }
        }else {
            throw  new RuntimeException("获取芝麻代理IP失败,返回数据:"+zhimaRet.getMsg());
        }
    }

}