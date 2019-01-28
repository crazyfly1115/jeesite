package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 网站数据Entity
 * @author zhangsy
 * @version 2019-01-03
 * 尚渝网络
 */
public class Website extends DataEntity<Website> {
	
	private static final long serialVersionUID = 1L;
	private String websiteName;		// 网站名称
	private String websiteUrl;		// 网站URL
	private String websiteType;		// 网站类型
	private String websiteToken;		// token
	private Crawler crawlerId;		// 爬虫容器
	private PythonPkg pythonPkgId;		//执行包
	
	public Website() {
		super();
	}

	public Website(String id){
		super(id);
	}

	@Length(min=0, max=200, message="网站名称长度必须介于 0 和 200 之间")
	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	
	@Length(min=0, max=500, message="网站URL长度必须介于 0 和 500 之间")
	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	
	@Length(min=0, max=100, message="网站类型长度必须介于 0 和 100 之间")
	public String getWebsiteType() {
		return websiteType;
	}

	public void setWebsiteType(String websiteType) {
		this.websiteType = websiteType;
	}

	public Crawler getCrawlerId() {
		return crawlerId;
	}

	public void setCrawlerId(Crawler crawlerId) {
		this.crawlerId = crawlerId;
	}


	public PythonPkg getPythonPkgId() {
		return pythonPkgId;
	}

	public void setPythonPkgId(PythonPkg pythonPkgId) {
		this.pythonPkgId = pythonPkgId;
	}

}