package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.Website;
import com.thinkgem.jeesite.modules.ips.dao.WebsiteDao;

/**
 * 网站数据Service
 * @author zhangsy
 * @version 2019-01-03
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class WebsiteService extends CrudService<WebsiteDao, Website> {

}