package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.ReptileTask;

/**
 * 爬虫任务DAO接口
 * @author zhangsy
 * @version 2019-01-04
 * 尚渝网络
 */
@MyBatisDao
public interface ReptileTaskDao extends CrudDao<ReptileTask> {
	
}