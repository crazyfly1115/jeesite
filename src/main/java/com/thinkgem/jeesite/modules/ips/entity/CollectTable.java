package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表Entity
 * @author zhangsy
 * @version 2019-01-05
 * 尚渝网络
 */
public class CollectTable extends DataEntity<CollectTable> {
	
	private static final long serialVersionUID = 1L;
	private Database databaseId;		// 数据库
	private String tableName;		// 表描述
	private String tableCode;		// 表名称
	private String tableType;

	private List<CollectField> collectFieldList=new ArrayList<CollectField>();

	public CollectTable() {
		super();
	}

	public CollectTable(String id){
		super(id);
	}

	public Database getDatabaseId() {
		return databaseId;
	}

	public void setDatabaseId(Database databaseId) {
		this.databaseId = databaseId;
	}
	
	@Length(min=0, max=100, message="表描述长度必须介于 0 和 100 之间")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	@Length(min=0, max=100, message="表名称长度必须介于 0 和 100 之间")
	public String getTableCode() {
		return tableCode;
	}

	public void setTableCode(String tableCode) {
		this.tableCode = tableCode;
	}
	
	@Length(min=0, max=10, message="表类型，见数据字典长度必须介于 0 和 10 之间")
	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public List<CollectField> getCollectFieldList() {
		return collectFieldList;
	}

	public void setCollectFieldList(List<CollectField> collectFieldList) {
		this.collectFieldList = collectFieldList;
	}
}