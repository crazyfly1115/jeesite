package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 采集数据Entity
 * @author zhangsy
 * @version 2019-01-04
 * 尚渝网络
 */
public class CollectData extends DataEntity<CollectData> {
	
	private static final long serialVersionUID = 1L;
	private Website websiteId;		// 网站主键
	private String collectTypeId;		// 网站采集类型主键
	private CollectTable collectTableId;		// 网站采集表主键
	private String isFormat;		// 是否格式化数据任务
	private String ftpUrl;		// ftp地址
	private String ftpUser;		// ftp用户名
	private String ftpPsw;		// ftp密码
	private String ftpPort;		// ftp端口号
	private String ftpDir;		// ftp文件夹名称
	private String poiType;		// 数据分类
	
	public CollectData() {
		super();
	}

	public CollectData(String id){
		super(id);
	}

	public Website getWebsiteId() {
		return websiteId;
	}

	public void setWebsiteId(Website websiteId) {
		this.websiteId = websiteId;
	}
	
	@Length(min=0, max=64, message="网站采集类型主键长度必须介于 0 和 64 之间")
	public String getCollectTypeId() {
		return collectTypeId;
	}

	public void setCollectTypeId(String collectTypeId) {
		this.collectTypeId = collectTypeId;
	}
	
	public CollectTable getCollectTableId() {
		return collectTableId;
	}

	public void setCollectTableId(CollectTable collectTableId) {
		this.collectTableId = collectTableId;
	}
	
	@Length(min=0, max=6, message="是否格式化数据任务长度必须介于 0 和 6 之间")
	public String getIsFormat() {
		return isFormat;
	}

	public void setIsFormat(String isFormat) {
		this.isFormat = isFormat;
	}
	
	@Length(min=0, max=500, message="ftp地址长度必须介于 0 和 500 之间")
	public String getFtpUrl() {
		return ftpUrl;
	}

	public void setFtpUrl(String ftpUrl) {
		this.ftpUrl = ftpUrl;
	}
	
	@Length(min=0, max=100, message="ftp用户名长度必须介于 0 和 100 之间")
	public String getFtpUser() {
		return ftpUser;
	}

	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	
	@Length(min=0, max=100, message="ftp密码长度必须介于 0 和 100 之间")
	public String getFtpPsw() {
		return ftpPsw;
	}

	public void setFtpPsw(String ftpPsw) {
		this.ftpPsw = ftpPsw;
	}
	
	@Length(min=0, max=11, message="ftp端口号长度必须介于 0 和 11 之间")
	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	
	@Length(min=0, max=500, message="ftp文件夹名称长度必须介于 0 和 500 之间")
	public String getFtpDir() {
		return ftpDir;
	}

	public void setFtpDir(String ftpDir) {
		this.ftpDir = ftpDir;
	}
	
	@Length(min=0, max=200, message="数据分类长度必须介于 0 和 200 之间")
	public String getPoiType() {
		return poiType;
	}

	public void setPoiType(String poiType) {
		this.poiType = poiType;
	}
	
}