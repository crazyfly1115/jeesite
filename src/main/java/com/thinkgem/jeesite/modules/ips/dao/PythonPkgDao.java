package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.PythonPkg;

/**
 * 执行包上传管理DAO接口
 * @author zhgnsy
 * @version 2019-01-15
 * 尚渝网络
 */
@MyBatisDao
public interface PythonPkgDao extends CrudDao<PythonPkg> {
	
}