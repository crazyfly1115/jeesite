package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 代理ipEntity
 * @author zhangsy
 * @version 2019-03-29
 * 尚渝网络
 */
public class ProxyIps extends DataEntity<ProxyIps> {
	
	private static final long serialVersionUID = 1L;
	private String ip;		// ip
	private int port;		// 端口
	private String expireTime;		// 过期时间
	private String expire_time;//冗余
	private List list;
	
	public ProxyIps() {
		super();
	}

	public ProxyIps(String id){
		super(id);
	}

	@Length(min=0, max=200, message="ip长度必须介于 0 和 200 之间")
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getExpire_time() {
		return expire_time;
	}

	public void setExpire_time(String expire_time) {
		this.expire_time = expire_time;
	}
}