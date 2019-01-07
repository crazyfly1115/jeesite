package com.thinkgem.jeesite.modules.ips.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据库Entity
 * @author zhangsy
 * @version 2019-01-02
 * 尚渝网络
 */
public class Database extends DataEntity<Database> {
	
	private static final long serialVersionUID = 1L;
	private String databaseName;		// 数据库名称
	private String databaseUrl;		// 数据库URL
	private String databasePort;		// 数据库端口号
	private String loginUser;		// 登陆用户名
	private String loginPsw;		// 登陆密码
	
	public Database() {
		super();
	}

	public Database(String id){
		super(id);
	}

	@Length(min=0, max=100, message="数据库名称长度必须介于 0 和 100 之间")
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	
	@Length(min=0, max=200, message="数据库URL长度必须介于 0 和 200 之间")
	public String getDatabaseUrl() {
		return databaseUrl;
	}

	public void setDatabaseUrl(String databaseUrl) {
		this.databaseUrl = databaseUrl;
	}
	
	@Length(min=0, max=11, message="数据库端口号长度必须介于 0 和 11 之间")
	public String getDatabasePort() {
		return databasePort;
	}

	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}
	
	@Length(min=0, max=100, message="登陆用户名长度必须介于 0 和 100 之间")
	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}
	
	@Length(min=0, max=100, message="登陆密码长度必须介于 0 和 100 之间")
	public String getLoginPsw() {
		return loginPsw;
	}

	public void setLoginPsw(String loginPsw) {
		this.loginPsw = loginPsw;
	}
	
}