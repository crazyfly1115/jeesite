package com.thinkgem.jeesite.modules.gen.entity;

import com.thinkgem.jeesite.common.persistence.DataEntity;
import com.thinkgem.jeesite.modules.ips.entity.Database;

import java.util.List;

public class SerachBean  extends DataEntity<SerachBean> {
    private String tableName;
    private String fkId;
    private String logId;
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

    public String getFkId() {
        return fkId;
    }

    public void setFkId(String fkId) {
        this.fkId = fkId;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public SerachBean() {
        super();
    }
}
