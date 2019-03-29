package com.thinkgem.jeesite.modules.ips.util.zhima;

import com.thinkgem.jeesite.modules.ips.entity.ProxyIps;

import java.util.List;
import java.util.Map;

/**
 * Copyright 2019 bejson.com
 */
public class zhimaRet {

    private int code;
    private boolean success;
    private String msg;
    private List<ProxyIps> data;
    public void setCode(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public void setData(List<ProxyIps> data) {
        this.data = data;
    }
    public List<ProxyIps> getData() {
        return data;
    }

}