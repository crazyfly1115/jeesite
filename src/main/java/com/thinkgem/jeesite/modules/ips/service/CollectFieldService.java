package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import com.thinkgem.jeesite.modules.ips.entity.CollectTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.CollectField;
import com.thinkgem.jeesite.modules.ips.dao.CollectFieldDao;

/**
 * 字段管理Service
 * @author zhangsy
 * @version 2019-01-03
 *  尚渝网络
 */
@Deprecated
@Service
@Transactional(readOnly = true)
public class CollectFieldService extends CrudService<CollectFieldDao, CollectField> {

    public void deleteByTableId(String id) {
        dao.deleteByTableId(id);
    }

    public List<CollectField> findListByTableId(String table_id) {
        CollectTable c=new CollectTable();
        c.setId(table_id);
        CollectField cf=new CollectField();
        cf.setCollectTableId(c);
        return findList(cf);
    }
}