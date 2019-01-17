package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.Crawler;

/**
 * 爬虫管理DAO接口
 * @author zhagnsy
 * @version 2019-01-15
 * 尚渝网络
 */
@MyBatisDao
public interface CrawlerDao extends CrudDao<Crawler> {
	
}