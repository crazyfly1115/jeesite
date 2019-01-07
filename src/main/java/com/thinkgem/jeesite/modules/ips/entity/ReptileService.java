package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 服务器管理Entity
 * @author zhangsy
 * @version 2019-01-07
 * 尚渝网络
 */
public class ReptileService extends DataEntity<ReptileService> {
	
	private static final long serialVersionUID = 1L;
	private String serviceIp;		// 服务器IP
	private String servicePort;		// 服务器端口
	private String serviceName;		// 服务名称
	private String proxyServerIp;		// 代理服务器IP
	private String ftpIp;		// ftp_ip
	private String ftpPort;		// ftp_port
	private String ftpUsername;		// ftp_username
	private String ftpUpwd;		// ftp_upwd
	
	public ReptileService() {
		super();
	}

	public ReptileService(String id){
		super(id);
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
	
	@Length(min=0, max=100, message="服务名称长度必须介于 0 和 100 之间")
	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	@Length(min=0, max=500, message="代理服务器IP长度必须介于 0 和 500 之间")
	public String getProxyServerIp() {
		return proxyServerIp;
	}

	public void setProxyServerIp(String proxyServerIp) {
		this.proxyServerIp = proxyServerIp;
	}
	
	@Length(min=0, max=255, message="ftp_ip长度必须介于 0 和 255 之间")
	public String getFtpIp() {
		return ftpIp;
	}

	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}
	
	@Length(min=0, max=255, message="ftp_port长度必须介于 0 和 255 之间")
	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	
	@Length(min=0, max=255, message="ftp_username长度必须介于 0 和 255 之间")
	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}
	
	@Length(min=0, max=255, message="ftp_upwd长度必须介于 0 和 255 之间")
	public String getFtpUpwd() {
		return ftpUpwd;
	}

	public void setFtpUpwd(String ftpUpwd) {
		this.ftpUpwd = ftpUpwd;
	}
	
}