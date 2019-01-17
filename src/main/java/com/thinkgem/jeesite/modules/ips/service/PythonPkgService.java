package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.PythonPkg;
import com.thinkgem.jeesite.modules.ips.dao.PythonPkgDao;

/**
 * 执行包上传管理Service
 * @author zhgnsy
 * @version 2019-01-15
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class PythonPkgService extends CrudService<PythonPkgDao, PythonPkg> {

}