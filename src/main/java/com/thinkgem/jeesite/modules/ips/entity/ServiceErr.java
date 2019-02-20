package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 错误日志Entity
 * @author zhangsy
 * @version 2019-02-20
 * 尚渝网络
 */
public class ServiceErr extends DataEntity<ServiceErr> {
	
	private static final long serialVersionUID = 1L;
	private String taskId;		// 任务id
	private String errType;		// 类型
	private String errMsg;		// 错误日志
	private Date bornTime;		// 时间
	private String hostIp;		// 地址
	
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
	
	@Length(min=0, max=100, message="错误日志长度必须介于 0 和 100 之间")
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBornTime() {
		return bornTime;
	}

	public void setBornTime(Date bornTime) {
		this.bornTime = bornTime;
	}
	
	@Length(min=0, max=100, message="地址长度必须介于 0 和 100 之间")
	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	
}