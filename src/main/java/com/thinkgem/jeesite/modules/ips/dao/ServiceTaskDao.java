package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.ServiceTask;
import org.apache.ibatis.annotations.Param;

/**
 * 爬虫服务任务管关联DAO接口
 * @author zhangsy
 * @version 2019-01-18
 * 尚渝网络
 */
@MyBatisDao
public interface ServiceTaskDao extends CrudDao<ServiceTask> {
    void deleteByTaskId(@Param("taskId") String taskid);
}