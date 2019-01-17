package com.thinkgem.jeesite.modules.ips.service;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.modules.ips.entity.Subitem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.Crawler;
import com.thinkgem.jeesite.modules.ips.dao.CrawlerDao;

/**
 * 爬虫管理Service
 * @author zhagnsy
 * @version 2019-01-15
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class CrawlerService extends CrudService<CrawlerDao, Crawler> {
    /**
     * @Author zhangsy
     * @Description  格式化jsonto SQL
     * @Date 16:21 2019/1/17
     * @Param [json, tableName, isDrop]
     * @return java.lang.String
     * @Company 重庆尚渝网络科技
     * @version v1000
     **/
    public  String   ParserJsonToSql(String json,String tableName,boolean isDrop){
        JsonParser parser = new JsonParser();
        JsonElement jsonElement=parser.parse(json);
        JsonArray sub=jsonElement.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("subitem").getAsJsonArray();
        List<Subitem> subitemList=new Gson().fromJson(new Gson().toJsonTree(sub),new TypeToken<List<Subitem>>(){}.getType());
        return getSQL(subitemList,tableName,isDrop);
    }
    private  String  getSQL(List<Subitem> subitemList,String table,boolean isDrop) {
        String sql="";
        if(isDrop){
           sql+= "DROP TABLE IF EXISTS "+table+"; ";
        }
       sql="CREATE TABLE "+table+"(";
        for (Subitem subitem:subitemList){
            checkKeyWord(subitem.getName());
            sql=sql+subitem.getName()+" varchar(255) DEFAULT NULL COMMENT '"+subitem.getField_des()+"',";
        }
        sql+="	id varchar(64) NOT NULL DEFAULT '',\n" +
                "  grab_time datetime DEFAULT NULL,\n" +
                "  create_by varchar(64) NOT NULL DEFAULT '1' COMMENT '创建者',\n" +
                "  create_date datetime NOT NULL COMMENT '创建时间',\n" +
                "  update_by varchar(64) NOT NULL COMMENT '更新者',\n" +
                "  update_date datetime NOT NULL COMMENT '更新时间',\n" +
                "  remarks varchar(255) DEFAULT NULL COMMENT '备注信息',\n" +
                "  del_flag char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',\n" +
                "  PRIMARY KEY (id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务数据';";
        for (Subitem subitem:subitemList){
            if (subitem.getSubitem() != null) {
                sql+= getSQL(subitem.getSubitem(),table+=subitem.getName(),isDrop);
            }
        }
        return sql;
    }

    private  void checkKeyWord(String name){
        AssertUtil.notNull(name,"配置文件节点name不能为空,请检查爬虫文件");
        String[] keys=new String[]{"id","grab_time","create_by","update_by","update_date","create_date","remarks","del_flag"};
        for (String key: keys){
            if(key.toLowerCase().equals(name.toLowerCase())){
                throw  new RuntimeException("爬虫文件中有关键字冲突:"+name);
            }
        }
    }

}