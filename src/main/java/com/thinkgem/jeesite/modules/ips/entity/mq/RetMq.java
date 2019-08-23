package com.thinkgem.jeesite.modules.ips.entity.mq;

import java.util.Map;

/**
 * @Author zhangsy
 * @Description  获取Mq上的返回数据类型
 * @Date 10:30 2019/4/15
 * @Param 
 * @return 
 * @Company 重庆尚渝网络科技
 * @version v1000
 **/
public class RetMq {

    private int status;
    private Map<String,Map<String,Object>> data;
    private String errMsg;
    public void setStatus(int status) {
        this.status = status;
    }
    public int getStatus() {
        return status;
    }

    public Map<String, Map<String, Object>> getData() {
        return data;
    }

    public void setData(Map<String, Map<String, Object>> data) {
        this.data = data;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
    public String getErrMsg() {
        return errMsg;
    }

}