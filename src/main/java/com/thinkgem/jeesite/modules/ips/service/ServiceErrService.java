package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.ServiceErr;
import com.thinkgem.jeesite.modules.ips.dao.ServiceErrDao;

/**
 * 错误日志Service
 * @author zhangsy
 * @version 2019-02-13
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class ServiceErrService extends CrudService<ServiceErrDao, ServiceErr> {

}