package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 执行包上传管理Entity
 * @author zhgnsy
 * @version 2019-01-15
 * 尚渝网络
 */
public class PythonPkg extends DataEntity<PythonPkg> {
	
	private static final long serialVersionUID = 1L;
	private String pythonName;		// 执行包描述
	private String pythonUrl;		// 上传路径
	private String isDefault;		// 是否默认1:默认，0：非默认
	
	public PythonPkg() {
		super();
	}

	public PythonPkg(String id){
		super(id);
	}


	@Length(min=0, max=200, message="执行包描述长度必须介于 0 和 200 之间")
	public String getPythonName() {
		return pythonName;
	}

	public void setPythonName(String pythonName) {
		this.pythonName = pythonName;
	}
	
	@Length(min=0, max=500, message="上传路径长度必须介于 0 和 500 之间")
	public String getPythonUrl() {
		return pythonUrl;
	}

	public void setPythonUrl(String pythonUrl) {
		this.pythonUrl = pythonUrl;
	}
	
	@Length(min=0, max=11, message="是否默认1:默认，0：非默认长度必须介于 0 和 11 之间")
	public String getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	
}