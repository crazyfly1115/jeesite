package com.thinkgem.jeesite.modules.ips.entity;
/**
 * Copyright 2019 bejson.com
 */
import java.util.List;

/**
 * Auto-generated: 2019-01-07 19:54:40
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Task_bind_db {

    private String db_url;
    private String db_username;
    private String db_password;
    private String tb_name;
    private List<Child_tb> child_tb;
//    public void setDb_ip(String db_ip) {
//        this.db_ip = db_ip;
//    }
//    public String getDb_ip() {
//        return db_ip;
//    }
//
//    public void setDb_port(String db_port) {
//        this.db_port = db_port;
//    }
//    public String getDb_port() {
//        return db_port;
//    }
//
//    public void setDb_name(String db_name) {
//        this.db_name = db_name;
//    }
//    public String getDb_name() {
//        return db_name;
//    }

    public String getDb_url() {
        return db_url;
    }

    public void setDb_url(String db_url) {
        this.db_url = db_url;
    }

    public void setDb_username(String db_username) {
        this.db_username = db_username;
    }
    public String getDb_username() {
        return db_username;
    }

    public void setDb_password(String db_password) {
        this.db_password = db_password;
    }
    public String getDb_password() {
        return db_password;
    }

    public void setTb_name(String tb_name) {
        this.tb_name = tb_name;
    }
    public String getTb_name() {
        return tb_name;
    }

    public void setChild_tb(List<Child_tb> child_tb) {
        this.child_tb = child_tb;
    }
    public List<Child_tb> getChild_tb() {
        return child_tb;
    }


}