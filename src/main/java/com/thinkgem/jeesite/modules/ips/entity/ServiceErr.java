package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 错误日志Entity
 * @author zhangsy
 * @version 2019-02-13
 * 尚渝网络
 */
public class ServiceErr extends DataEntity<ServiceErr> {
	
	private static final long serialVersionUID = 1L;
	private String taskId;		// 任务id
	private String errType;		// 类型
	private String err;		// 错误描述
	
	public ServiceErr() {
		super();
	}

	public ServiceErr(String id){
		super(id);
	}

	@Length(min=0, max=200, message="任务id长度必须介于 0 和 200 之间")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	
	@Length(min=0, max=500, message="类型长度必须介于 0 和 500 之间")
	public String getErrType() {
		return errType;
	}

	public void setErrType(String errType) {
		this.errType = errType;
	}
	
	@Length(min=0, max=100, message="错误描述长度必须介于 0 和 100 之间")
	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}
	
}