package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import com.thinkgem.jeesite.modules.ips.dao.DatabaseDao;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 数据库Service
 * @author zhangsy
 * @version 2019-01-02
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class DatabaseService extends CrudService<DatabaseDao, Database> {

    @Autowired
    private DatabaseDao databaseDao;
    /**
     * 保存数据（插入或更新）
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(Database entity) {
        if("1".equals(entity.getIsDefault())){
            dao.updateDefAll(DataEntity.DEL_FLAG_NORMAL);
        }
        if (entity.getIsNewRecord()){
            entity.preInsert();
            dao.insert(entity);
        }else{
            entity.preUpdate();
            dao.update(entity);
        }
    }

    public Database findDef(){
        Database database=null;
        List<Database> list=databaseDao.getDef(DataEntity.DEL_FLAG_NORMAL);
        if(list==null || list.size()==0){
            List<Database> l=databaseDao.findDef(DataEntity.DEL_FLAG_NORMAL);
            if(l==null || l.size()==0){
                return null;
            }else {
                database=l.get(0);
            }
        }else{
            database=list.get(0);
        }

        return database;
    }



}