package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 服务器管理Entity
 * @author zhangsy
 * @version 2019-01-15
 * 尚渝网络
 */
public class ReptileService extends DataEntity<ReptileService> {
	
	private static final long serialVersionUID = 1L;
	private String serviceName;		// 服务名称
	private String serviceIp;		// 服务器IP
	private String servicePort;		// 服务器端口
	private String proxyServerIp;		// 代理服务器IP
	private String serviceState;		// 服务状态
	
	public ReptileService() {
		super();
	}

	public ReptileService(String id){
		super(id);
	}

	@Length(min=0, max=100, message="服务名称长度必须介于 0 和 100 之间")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Length(min=0, max=100, message="服务器IP长度必须介于 0 和 100 之间")
	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	
	@Length(min=0, max=11, message="服务器端口长度必须介于 0 和 11 之间")
	public String getServicePort() {
		return servicePort;
	}

	public void setServicePort(String servicePort) {
		this.servicePort = servicePort;
	}
	
	@Length(min=0, max=500, message="代理服务器IP长度必须介于 0 和 500 之间")
	public String getProxyServerIp() {
		return proxyServerIp;
	}

	public void setProxyServerIp(String proxyServerIp) {
		this.proxyServerIp = proxyServerIp;
	}
	
	@Length(min=0, max=50, message="服务状态长度必须介于 0 和 50 之间")
	public String getServiceState() {
		return serviceState;
	}

	public void setServiceState(String serviceState) {
		this.serviceState = serviceState;
	}
	
}