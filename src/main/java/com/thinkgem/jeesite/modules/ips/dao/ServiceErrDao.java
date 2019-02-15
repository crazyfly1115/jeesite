package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.ServiceErr;

/**
 * 错误日志DAO接口
 * @author zhangsy
 * @version 2019-02-13
 * 尚渝网络
 */
@MyBatisDao
public interface ServiceErrDao extends CrudDao<ServiceErr> {
	
}