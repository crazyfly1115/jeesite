package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.CollectField;
import org.apache.ibatis.annotations.Param;

/**
 * 字段管理DAO接口
 * @author zhangsy
 * @version 2019-01-03
 * 尚渝网络
 */
@MyBatisDao
public interface CollectFieldDao extends CrudDao<CollectField> {

    void deleteByTableId(@Param("tableId") String tableId);
}