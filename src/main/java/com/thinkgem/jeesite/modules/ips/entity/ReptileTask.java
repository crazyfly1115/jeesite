package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 爬虫任务Entity
 * @author zhangsy
 * @version 2019-01-07
 * 尚渝网络
 */
public class ReptileTask extends DataEntity<ReptileTask> {
	
	private static final long serialVersionUID = 1L;
	private String taskName;		// 任务名称
	private Date taskBegin;		// 任务开始时间
	private Date taskEnd;		// 任务结束时间
	private String taskState;		// 状态
	private String startMode;		// 启动方式
	private String taskCron;		// cron表达式
	private String retryTimes;		// 重试次数
	private String urlLayers;		// 抓取层数
	private String taskPython;		// python路径
	private String taskParse;		// 任务解析文件路径
	private CollectData collectDataId;		// 网站采集数据
	private List<ReptileService> serviceList;
	
	public ReptileTask() {
		super();
	}

	public ReptileTask(String id){
		super(id);
	}

	@Length(min=0, max=100, message="任务名称长度必须介于 0 和 100 之间")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskBegin() {
		return taskBegin;
	}

	public void setTaskBegin(Date taskBegin) {
		this.taskBegin = taskBegin;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTaskEnd() {
		return taskEnd;
	}

	public void setTaskEnd(Date taskEnd) {
		this.taskEnd = taskEnd;
	}
	
	@Length(min=0, max=50, message="状态长度必须介于 0 和 50 之间")
	public String getTaskState() {
		return taskState;
	}

	public void setTaskState(String taskState) {
		this.taskState = taskState;
	}
	
	@Length(min=0, max=10, message="启动方式长度必须介于 0 和 10 之间")
	public String getStartMode() {
		return startMode;
	}

	public void setStartMode(String startMode) {
		this.startMode = startMode;
	}
	
	@Length(min=0, max=100, message="cron表达式长度必须介于 0 和 100 之间")
	public String getTaskCron() {
		return taskCron;
	}

	public void setTaskCron(String taskCron) {
		this.taskCron = taskCron;
	}
	
	@Length(min=0, max=11, message="重试次数长度必须介于 0 和 11 之间")
	public String getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(String retryTimes) {
		this.retryTimes = retryTimes;
	}
	
	@Length(min=0, max=11, message="抓取层数长度必须介于 0 和 11 之间")
	public String getUrlLayers() {
		return urlLayers;
	}

	public void setUrlLayers(String urlLayers) {
		this.urlLayers = urlLayers;
	}
	
	@Length(min=0, max=300, message="python路径长度必须介于 0 和 300 之间")
	public String getTaskPython() {
		return taskPython;
	}

	public void setTaskPython(String taskPython) {
		this.taskPython = taskPython;
	}
	
	@Length(min=0, max=300, message="任务解析文件路径长度必须介于 0 和 300 之间")
	public String getTaskParse() {
		return taskParse;
	}

	public void setTaskParse(String taskParse) {
		this.taskParse = taskParse;
	}
	
	public CollectData getCollectDataId() {
		return collectDataId;
	}

	public void setCollectDataId(CollectData collectDataId) {
		this.collectDataId = collectDataId;
	}

	public List<ReptileService> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ReptileService> serviceList) {
		this.serviceList = serviceList;
	}
}