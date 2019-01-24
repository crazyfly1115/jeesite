package com.thinkgem.jeesite.modules.ips.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据库DAO接口
 * @author zhangsy
 * @version 2019-01-02
 * 尚渝网络
 */
@MyBatisDao
public interface DatabaseDao extends CrudDao<Database> {

    public int updateDefAll(@Param("DEL_FLAG_NORMAL")String DEL_FLAG_NORMAL);

    public List<Database> getDef(@Param("DEL_FLAG_NORMAL")String DEL_FLAG_NORMAL);

    public List<Database> findDef(@Param("DEL_FLAG_NORMAL")String DEL_FLAG_NORMAL);


	
}