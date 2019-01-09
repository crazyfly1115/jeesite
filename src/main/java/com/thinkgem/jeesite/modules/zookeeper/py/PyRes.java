package com.thinkgem.jeesite.modules.zookeeper.py;

public class PyRes {

    private static final long serialVersionUID = 1L;
    private boolean success;
    private int errorcode;
    private String errormsg;
    private String data;
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public boolean getSuccess() {
        return success;
    }

    public void setErrorcode(int errorcode) {
        this.errorcode = errorcode;
    }
    public int getErrorcode() {
        return errorcode;
    }

    public void setErrormsg(String errormsg) {
        this.errormsg = errormsg;
    }
    public String getErrormsg() {
        return errormsg;
    }

    public void setData(String data) {
        this.data = data;
    }
    public String getData() {
        return data;
    }
}
