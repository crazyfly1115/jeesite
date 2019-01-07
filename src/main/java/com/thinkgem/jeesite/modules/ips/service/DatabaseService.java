package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import com.thinkgem.jeesite.modules.ips.dao.DatabaseDao;

/**
 * 数据库Service
 * @author zhangsy
 * @version 2019-01-02
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class DatabaseService extends CrudService<DatabaseDao, Database> {

}