package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import com.thinkgem.jeesite.modules.ips.entity.ReptileService;
import com.thinkgem.jeesite.modules.ips.entity.ServiceTask;
import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.ReptileTask;
import com.thinkgem.jeesite.modules.ips.dao.ReptileTaskDao;

/**
 * 爬虫任务Service
 * @author zhangsy
 * @version 2019-01-04
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class ReptileTaskService extends CrudService<ReptileTaskDao, ReptileTask> {
    @Autowired
    private ServiceTaskService serviceTaskService;
    @Override
    @Transactional
    public void save(ReptileTask entity) {
        super.save(entity);
        serviceTaskService.deleteByTaskId(entity.getId());
        for (ReptileService rs:entity.getServiceList()){
            ServiceTask sc=new ServiceTask();
            sc.setTaskId(entity.getId());
            sc.setServiceId(rs.getId());
            serviceTaskService.save(sc);
        }
    }
}