package com.thinkgem.jeesite.modules.sys.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.sys.entity.SysConfig;

/**
 * 配置DAO接口
 * @author zhangsy
 * @version 2018-12-28
 * 尚渝网络
 */
@MyBatisDao
public interface SysConfigDao extends CrudDao<SysConfig> {
	
}