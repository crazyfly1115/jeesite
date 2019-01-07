package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.Database;

/**
 * 数据库DAO接口
 * @author zhangsy
 * @version 2019-01-02
 * 尚渝网络
 */
@MyBatisDao
public interface DatabaseDao extends CrudDao<Database> {
	
}