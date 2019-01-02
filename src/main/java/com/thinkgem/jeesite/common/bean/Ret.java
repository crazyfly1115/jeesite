package com.thinkgem.jeesite.common.bean;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thinkgem.jeesite.common.mapper.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 封装返回的类
 *
 * @author crazyfly21
 *
 */
public class Ret {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private int ret = 0;
    private String msg = "";
    private String errCode;//错误代码
    private Map<String, Object> data=new HashMap<String, Object>();
    public Ret() {
        super();
    }

    public Ret(int ret, String msg) {
        super();
        this.ret = ret;
        this.msg = msg;
    }

    public Ret(int ret, String msg, String errCode) {
        this.ret = ret;
        this.msg = msg;
        this.errCode = errCode;
    }

    public Ret(String key , Object data) {
        this.data.put(key, data);
    }
    public int getRet() {
        return ret;
    }

    public void setRet(int ret) {
        this.ret = ret;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getdata() {
        return data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setMap(Map<String, Object> map) {
        this.data = map;
    }
    public Ret putMap(String key,Object obj){
        this.data.put(key, obj);
        return this;
    }
    public void putAllMap(Map<String, Object> map){
        this.data.putAll(map);
    }
    @Override
    public String toString() {
//		GsonBuilder gsonBuilder = new GsonBuilder();
////		gsonBuilder.setDateFormat("yyyy-MM-dd HH:mm:ss");
//		gsonBuilder.serializeNulls(); //重点
//		Gson gson = gsonBuilder.create();
//		ObjectMapper mapper = new ObjectMapper();
//        String json = null;
//		try {
//			json = mapper.writeValueAsString(this);
//			mapper.setSerializationInclusion(Include.NON_DEFAULT);
//		} catch (JsonProcessingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(JsonMapper.toJsonString(this));
        String json=new JsonMapper(JsonInclude.Include.ALWAYS).toJson(this);
        if (logger.isDebugEnabled()){
            logger.debug("返回参数:{}",json);
        }
        return  json;
    }
}
