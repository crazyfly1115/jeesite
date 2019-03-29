package com.thinkgem.jeesite.modules.ips.entity;

import java.util.List;

/**
 * Auto-generated: 2019-01-16 17:1:46
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Subitem {

    private String name;
    private String type;
    private String html;
    private String json;
    private String field_des;
    private String is_update;
    private String data_type;
    private List<Subitem> subitem;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public void setHtml(String html) {
        this.html = html;
    }
    public String getHtml() {
        return html;
    }

    public void setJson(String json) {
        this.json = json;
    }
    public String getJson() {
        return json;
    }

    public void setField_des(String field_des) {
        this.field_des = field_des;
    }
    public String getField_des() {
        return field_des;
    }

    public void setIs_update(String is_update) {
        this.is_update = is_update;
    }
    public String getIs_update() {
        return is_update;
    }

    public List<Subitem> getSubitem() {
        return subitem;
    }

    public void setSubitem(List<Subitem> subitem) {
        this.subitem = subitem;
    }

    public String getData_type() {
        return data_type;
    }

    public void setData_type(String data_type) {
        this.data_type = data_type;
    }

    @Override
    public String toString() {
        return "Subitem{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", html='" + html + '\'' +
                ", json='" + json + '\'' +
                ", field_des='" + field_des + '\'' +
                ", is_update='" + is_update + '\'' +
                ", subitem=" + subitem +
                '}';
    }
}
