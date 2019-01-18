package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.modules.ips.dao.CollectFieldDao;
import com.thinkgem.jeesite.modules.ips.entity.CollectField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.CollectTable;
import com.thinkgem.jeesite.modules.ips.dao.CollectTableDao;

/**
 * 数据库表Service
 * @author zhangsy
 * @version 2019-01-05
 *  尚渝网络
 */
@Deprecated
@Service
@Transactional(readOnly = true)
public class CollectTableService extends CrudService<CollectTableDao, CollectTable> {
    @Autowired
    private CollectFieldService collectFieldService;
    @Override
    @Transactional
    public void save(CollectTable entity) {
        super.save(entity);

        collectFieldService.deleteByTableId(entity.getId());
        for (CollectField cf : entity.getCollectFieldList()){
            cf.setCollectTableId(entity);
            collectFieldService.save(cf);
        }
    }

    @Override
    public CollectTable get(String id) {
        CollectTable  ct=super.get(id);
        AssertUtil.notNull(ct,id+"未查询到数据");

        CollectField cf=new CollectField();
        cf.setCollectTableId(ct);
        ct.setCollectFieldList(collectFieldService.findList(cf));
        return super.get(id);
    }
}