package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.StorageService;

/**
 * 数据存储DAO接口
 * @author zhangsy
 * @version 2019-01-16
 * 尚渝网络
 */
@MyBatisDao
public interface StorageServiceDao extends CrudDao<StorageService> {
	
}