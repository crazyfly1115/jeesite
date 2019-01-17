package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.StorageService;
import com.thinkgem.jeesite.modules.ips.dao.StorageServiceDao;

/**
 * 数据存储Service
 * @author zhangsy
 * @version 2019-01-16
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class StorageServiceService extends CrudService<StorageServiceDao, StorageService> {

}