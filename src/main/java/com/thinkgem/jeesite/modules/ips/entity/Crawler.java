package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 爬虫管理Entity
 * @author zhagnsy
 * @version 2019-02-13
 * 尚渝网络
 */
public class Crawler extends DataEntity<Crawler> {
	
	private static final long serialVersionUID = 1L;
	private String crawlerName;		// 爬虫配置
	private PythonPkg pythonPkgId;		// 爬虫配置文件
	private String isDefault;		// 是否默认
	private String crawlerJson;		// 文件内容
	private String crawlerUrl;		// 爬虫
	
	public Crawler() {
		super();
	}

	public Crawler(String id){
		super(id);
	}

	@Length(min=0, max=200, message="爬虫配置长度必须介于 0 和 200 之间")
	public String getCrawlerName() {
		return crawlerName;
	}

	public void setCrawlerName(String crawlerName) {
		this.crawlerName = crawlerName;
	}
	
	public PythonPkg getPythonPkgId() {
		return pythonPkgId;
	}

	public void setPythonPkgId(PythonPkg pythonPkgId) {
		this.pythonPkgId = pythonPkgId;
	}
	
	@Length(min=0, max=11, message="是否默认长度必须介于 0 和 11 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
	public String getCrawlerJson() {
		return crawlerJson;
	}

	public void setCrawlerJson(String crawlerJson) {
		this.crawlerJson = crawlerJson;
	}
	
	@Length(min=0, max=1000, message="爬虫长度必须介于 0 和 1000 之间")
	public String getCrawlerUrl() {
		return crawlerUrl;
	}

	public void setCrawlerUrl(String crawlerUrl) {
		this.crawlerUrl = crawlerUrl;
	}
	
}