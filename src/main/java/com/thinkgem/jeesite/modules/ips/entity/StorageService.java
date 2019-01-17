package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据存储Entity
 * @author zhangsy
 * @version 2019-01-16
 * 尚渝网络
 */
public class StorageService extends DataEntity<StorageService> {
	
	private static final long serialVersionUID = 1L;
	private String serviceIp;		// 服务ip
	private String isDefault;		// 是否默认
	
	public StorageService() {
		super();
	}

	public StorageService(String id){
		super(id);
	}

	@Length(min=0, max=200, message="服务ip长度必须介于 0 和 200 之间")
	public String getServiceIp() {
		return serviceIp;
	}

	public void setServiceIp(String serviceIp) {
		this.serviceIp = serviceIp;
	}
	
	@Length(min=0, max=11, message="是否默认长度必须介于 0 和 11 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}