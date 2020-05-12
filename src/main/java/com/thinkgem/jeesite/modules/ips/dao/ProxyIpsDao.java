package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.ProxyIps;

import java.util.List;
import java.util.Map;

/**
 * 代理ipDAO接口
 * @author zhangsy
 * @version 2019-03-29
 * 尚渝网络
 */
@MyBatisDao
public interface ProxyIpsDao extends CrudDao<ProxyIps> {
	 List<Map> getips(ProxyIps proxyIps);
}