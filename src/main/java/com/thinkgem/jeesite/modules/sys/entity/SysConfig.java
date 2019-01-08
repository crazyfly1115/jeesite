package com.thinkgem.jeesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 配置Entity
 * @author zhangsy
 * @version 2018-12-28
 * 尚渝网络
 */
public class SysConfig extends DataEntity<SysConfig> {
	
	private static final long serialVersionUID = 1L;
	private String configKey;		// 键
	private String configValue;		// 值
	private String des;		// 描述
	
	public SysConfig() {
		super();
	}

	public SysConfig(String id){
		super(id);
	}

	@Length(min=1, max=64, message="键长度必须介于 1 和 64 之间")
	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}
	
	@Length(min=1, max=2000, message="值长度必须介于 1 和 2000 之间")
	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
	@Length(min=0, max=255, message="描述长度必须介于 0 和 255 之间")
	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}
	
}