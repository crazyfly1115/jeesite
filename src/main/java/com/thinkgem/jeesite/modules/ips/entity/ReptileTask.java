package com.thinkgem.jeesite.modules.ips.entity;

import com.google.gson.JsonElement;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 爬虫任务Entity
 * @author zhangsy
 * @version 2019-01-21
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
	private String poiType;		// 关键字
	private Website websiteId;		// 网站
	private Database databaseId;		// 数据库
	private String tableName;		// 存储主表
	private Crawler crawlerId;		// 爬虫
	private String storageServiceId;		// 存储服务
	private String taskType;		// 任务类型
	private String websiteUrl;//website_url 网站url
	private String crawlerUrl;//crawler_url 爬虫url
	private String complateTime;//
	private String crawlerJson;//任务内容
	private List<ServiceTask> serviceList;
	
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
	
	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}
	
	public Website getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Website websiteId) {
		this.websiteId = websiteId;
	}
	
	public Database getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Database databaseId) {
		this.databaseId = databaseId;
	}

	@Length(min=0, max=200, message="存储主表长度必须介于 0 和 200 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	public Crawler getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(Crawler crawlerId) {
		this.crawlerId = crawlerId;
	}

	/*public StorageService getStorageServiceId() {
		return storageServiceId;
	}

	public void setStorageServiceId(StorageService storageServiceId) {
		this.storageServiceId = storageServiceId;
	}*/
	
	@Length(min=0, max=10, message="任务类型长度必须介于 0 和 10 之间")
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	@Length(min=0, max=64, message="存储服务长度必须介于 0 和 64 之间")
	public String getStorageServiceId() {
		return storageServiceId;
	}

	public void setStorageServiceId(String storageServiceId) {
		this.storageServiceId = storageServiceId;
	}

	public List<ServiceTask> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceTask> serviceList) {
		this.serviceList = serviceList;
	}

    public String getCrawlerUrl() {
        return crawlerUrl;
    }

    public void setCrawlerUrl(String crawlerUrl) {
        this.crawlerUrl = crawlerUrl;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

	public String getCrawlerJson() {
		return crawlerJson;
	}

	public void setCrawlerJson(String crawlerJson) {
		this.crawlerJson = crawlerJson;
	}

	public String getComplateTime() {
		return complateTime;
	}

	public void setComplateTime(String complateTime) {
		this.complateTime = complateTime;
	}
}