package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 爬虫服务任务管关联Entity
 * @author zhangsy
 * @version 2019-01-07
 * 尚渝网络
 */
public class ServiceTask extends DataEntity<ServiceTask> {
	
	private static final long serialVersionUID = 1L;
	private String serviceId;		// 爬虫服务主键
	private String taskId;		// 爬虫任务主键
	
	public ServiceTask() {
		super();
	}

	public ServiceTask(String id){
		super(id);
	}

	@Length(min=0, max=64, message="爬虫服务主键长度必须介于 0 和 64 之间")
	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	@Length(min=0, max=64, message="爬虫任务主键长度必须介于 0 和 64 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
}