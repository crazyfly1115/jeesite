package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.ServiceTask;
import com.thinkgem.jeesite.modules.ips.dao.ServiceTaskDao;

/**
 * 爬虫服务任务管关联Service
 * @author zhangsy
 * @version 2019-01-07
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class ServiceTaskService extends CrudService<ServiceTaskDao, ServiceTask> {

    public void deleteByTaskId(String taskid) {
        dao.deleteByTaskId(taskid);
    }
}