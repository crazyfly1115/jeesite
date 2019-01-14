package com.thinkgem.jeesite.modules.gen.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ips.entity.Database;

import java.util.List;

public class SerachBean  extends DataEntity<SerachBean> {
    private String tableName;
    private List<SerachColumn> col;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<SerachColumn> getCol() {
        return col;
    }

    public void setCol(List<SerachColumn> col) {
        this.col = col;
    }

    public SerachBean(String tableName, List<SerachColumn> col) {
        this.tableName = tableName;
        this.col = col;
    }

    public SerachBean() {
        super();
    }
}
