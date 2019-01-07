package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.CollectData;

/**
 * 采集数据DAO接口
 * @author zhangsy
 * @version 2019-01-04
 * 尚渝网络
 */
@MyBatisDao
public interface CollectDataDao extends CrudDao<CollectData> {
	
}