package com.thinkgem.jeesite.modules.ips.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 字段管理Entity
 * @author zhangsy
 * @version 2019-01-03
 * 尚渝网络
 */
public class CollectField extends DataEntity<CollectField> {
	
	private static final long serialVersionUID = 1L;
	private CollectTable collectTableId;		// 网站采集表主键
	private String fieldCode;		// 字段代码
	private String fieldName;		// 字段名称
	private String fieldLength;		// 字段长度
	private String isUpdate;		// 是否是判断修改数据字段
	private String slaveCollectTableId;		// 网站采集子表主键
	
	public CollectField() {
		super();
	}

	public CollectField(String id){
		super(id);
	}
	@JsonIgnore
	public CollectTable getCollectTableId() {
		return collectTableId;
	}

	public void setCollectTableId(CollectTable collectTableId) {
		this.collectTableId = collectTableId;
	}
	
	@Length(min=0, max=64, message="字段代码长度必须介于 0 和 64 之间")
	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}
	
	@Length(min=0, max=64, message="字段名称长度必须介于 0 和 64 之间")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Length(min=0, max=11, message="字段长度长度必须介于 0 和 11 之间")
	public String getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}
	
	@Length(min=0, max=11, message="是否是判断修改数据字段长度必须介于 0 和 11 之间")
	public String getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	
	@Length(min=0, max=64, message="网站采集子表主键长度必须介于 0 和 64 之间")
	public String getSlaveCollectTableId() {
		return slaveCollectTableId;
	}

	public void setSlaveCollectTableId(String slaveCollectTableId) {
		this.slaveCollectTableId = slaveCollectTableId;
	}
	
}