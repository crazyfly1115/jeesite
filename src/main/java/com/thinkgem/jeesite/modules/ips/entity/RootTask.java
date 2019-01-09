package com.thinkgem.jeesite.modules.ips.entity;
import com.thinkgem.jeesite.common.persistence.DataEntity;

import java.util.Date;
import java.util.List;

/**
 * Auto-generated: 2019-01-07 19:54:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class RootTask extends DataEntity<RootTask> {

    private static final long serialVersionUID = 1L;
    private String task_id;
    private String task_name;
    private String task_url;
    private String task_file;
    private Date task_stime;
    private Task_bind_db task_bind_db;
    private boolean task_status;
    private String task_type;
    private String poi_key;
    private List<String> poi_kword;
    private Date task_start_time;
    private int retry_times;
    private String ftp_dir;
    private String service_name;
    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }
    public String getTask_name() {
        return task_name;
    }

    public void setTask_url(String task_url) {
        this.task_url = task_url;
    }
    public String getTask_url() {
        return task_url;
    }

    public void setTask_file(String task_file) {
        this.task_file = task_file;
    }
    public String getTask_file() {
        return task_file;
    }

    public void setTask_stime(Date task_stime) {
        this.task_stime = task_stime;
    }
    public Date getTask_stime() {
        return task_stime;
    }

    public void setTask_bind_db(Task_bind_db task_bind_db) {
        this.task_bind_db = task_bind_db;
    }
    public Task_bind_db getTask_bind_db() {
        return task_bind_db;
    }

    public void setTask_status(boolean task_status) {
        this.task_status = task_status;
    }
    public boolean getTask_status() {
        return task_status;
    }

    public void setTask_type(String task_type) {
        this.task_type = task_type;
    }
    public String getTask_type() {
        return task_type;
    }

    public void setPoi_key(String poi_key) {
        this.poi_key = poi_key;
    }
    public String getPoi_key() {
        return poi_key;
    }

    public void setPoi_kword(List<String> poi_kword) {
        this.poi_kword = poi_kword;
    }
    public List<String> getPoi_kword() {
        return poi_kword;
    }

    public void setTask_start_time(Date task_start_time) {
        this.task_start_time = task_start_time;
    }
    public Date getTask_start_time() {
        return task_start_time;
    }

    public void setRetry_times(int retry_times) {
        this.retry_times = retry_times;
    }
    public int getRetry_times() {
        return retry_times;
    }

    public void setFtp_dir(String ftp_dir) {
        this.ftp_dir = ftp_dir;
    }
    public String getFtp_dir() {
        return ftp_dir;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public boolean isTask_status() {
        return task_status;
    }
}