package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.CollectTable;

/**
 * 数据库表DAO接口
 * @author zhangsy
 * @version 2019-01-05
 * 尚渝网络
 */
@MyBatisDao
public interface CollectTableDao extends CrudDao<CollectTable> {
	
}