package com.thinkgem.jeesite.modules.ips.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
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

    private  static  final  String  SQLBLZ="action,add,aggregate,all,alter,after,and,as,asc,avg,avg_row_length,auto_increment,between,bigint,bit,binary,blob,bool,both,by,cascade,case,char,character,change,check,checksum,column,columns,comment,constraint,create,cross,current_date,current_time,current_timestamp,data,database,databases,date,datetime,day,day_hour,day_minute,day_second,dayofmonth,dayofweek,dayofyear,dec,decimal,default,delayed,delay_key_write,delete,desc,describe,distinct,distinctrow,double,drop,end,else,escape,escaped,enclosed,enum,explain,exists,fields,file,first,float,float4,float8,flush,foreign,from,for,full,function,global,grant,grants,group,having,heap,high_priority,hour,hour_minute,hour_second,hosts,identified,ignore,in,index,infile,inner,insert,insert_id,int,integer,interval,int1,int2,int3,int4,int8,into,if,is,isam,join,key,keys,kill,last_insert_id,leading,left,length,like,lines,limit,load,local,lock,logs,long,longblob,longtext,low_priority,max,max_rows,match,mediumblob,mediumtext,mediumint,middleint,min_rows,minute,minute_second,modify,month,monthname,myisam,natural,numeric,no,not,null,on,optimize,option,optionally,or,order,outer,outfile,pack_keys,partial,password,precision,primary,procedure,process,processlist,privileges,read,real,references,reload,regexp,rename,replace,restrict,returns,revoke,rlike,row,rows,second,select,set,show,shutdown,smallint,soname,sql_big_tables,sql_big_selects,sql_low_priority_updates,sql_log_off,sql_log_update,sql_select_limit,sql_small_result,sql_big_result,sql_warnings,straight_join,starting,status,string,table,tables,temporary,terminated,text,then,time,timestamp,tinyblob,tinytext,tinyint,trailing,to,type,use,using,unique,unlock,unsigned,update,usage,values,varchar,variables,varying,varbinary,with,write,when,where,year,year_month,zerofill";

    /**
     * @Author zhangsy
     * @Description  格式化jsonto SQL
     * @Date 16:21 2019/1/17
     * @Param [json, tableName, isDrop]
     * @return java.lang.String
     * @Company 重庆尚渝网络科技
     * @version v1000
     **/
    public   List<String>   ParserJsonToSql(String json,String tableName,boolean isDrop){
        try{

            List<String> sqls=new ArrayList<String>();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement=parser.parse(json);
            JsonArray sub=jsonElement.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("subitem").getAsJsonArray();
            List<Subitem> subitemList=new Gson().fromJson(new Gson().toJsonTree(sub),new TypeToken<List<Subitem>>(){}.getType());
             getSQL(subitemList,tableName,isDrop,sqls);
            return  sqls;
        }catch (RuntimeException e){
            throw  e;
        }catch (Exception e){
            logger.error("解析策略文件错误",e);
            throw  new RuntimeException("解析爬虫文件出错,请检查爬虫文件");
        }
    }
    private  String  getSQL(List<Subitem> subitemList,String table,boolean isDrop,List sqls) {
        if(!StringUtils.strIsRigthTable(table))throw  new RuntimeException("表名只能由下划线字母数字组成,请检查cw文件中name 是否含有中文");
        String sql="";
        if(isDrop){
           sql+= "DROP TABLE IF EXISTS "+table+"; ";
        }
       sql="CREATE TABLE "+table+"(";
        for (Subitem subitem:subitemList){
            checkKeyWord(subitem.getName());
            String dataType=subitem.getData_type();
            if(StringUtils.isNotBlank(dataType)){
                dataType=dataType;
            }else{
                dataType="varchar(255)";
            }
            sql=sql+subitem.getName()+ " "+ dataType +" DEFAULT NULL COMMENT '"+subitem.getField_des()+"',";

                //判断是否为主键 增加唯一索引;
            if("0".equals(subitem.getIs_update())){
                sql+="UNIQUE KEY UK_"+subitem.getName()+" ("+subitem.getName()+"), ";
            }
        }
        sql+="	id varchar(64) NOT NULL DEFAULT '' COMMENT '主键',\n" +
                "  grab_time datetime DEFAULT NULL COMMENT '抓取时间',\n" +
                "  version int DEFAULT 0 COMMENT '版本',\n" +
                "  fk_id varchar(64)  DEFAULT NULL COMMENT '外键',\n" +
                "  create_by varchar(64) NOT NULL DEFAULT '1' COMMENT '创建者',\n" +
                "  create_date datetime NOT NULL COMMENT '创建时间',\n" +
                "  update_by varchar(64) NOT NULL COMMENT '更新者',\n" +
                "  update_date datetime NOT NULL COMMENT '更新时间',\n" +
                "  remarks varchar(255) DEFAULT NULL COMMENT '备注信息',\n" +
                "  del_flag char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',\n" +
                "  PRIMARY KEY (id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务数据'";
        sqls.add(sql);

        //添加日志表
        sql="CREATE TABLE "+table+"_log (";
        for (Subitem subitem:subitemList){
            checkKeyWord(subitem.getName());
            sql=sql+subitem.getName()+" varchar(255) DEFAULT NULL COMMENT '"+subitem.getField_des()+"',";
        }
        sql+="	id varchar(64) NOT NULL DEFAULT '',\n" +
                "  log_id varchar(64) NOT NULL DEFAULT '',\n" +
                "  grab_time datetime DEFAULT NULL,\n" +
                "  version int DEFAULT 0 COMMENT '版本',\n" +
                "  fk_id varchar(64)  DEFAULT NULL,\n" +
                "  create_by varchar(64) NOT NULL DEFAULT '1' COMMENT '创建者',\n" +
                "  create_date datetime NOT NULL COMMENT '创建时间',\n" +
                "  update_by varchar(64) NOT NULL COMMENT '更新者',\n" +
                "  update_date datetime NOT NULL COMMENT '更新时间',\n" +
                "  remarks varchar(255) DEFAULT NULL COMMENT '备注信息',\n" +
                "  del_flag char(1) NOT NULL DEFAULT '0' COMMENT '删除标记',\n" +
                "  PRIMARY KEY (id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务数据日志表'";
        sqls.add(sql);


        for (Subitem subitem:subitemList){
            if (subitem.getSubitem() != null) {
                if("log".equals(subitem.getName()))throw  new RuntimeException("log为表关键字,配置文件中不能有以log为key的name");
                getSQL(subitem.getSubitem(),table+=("_"+subitem.getName()),isDrop,sqls);
            }
        }
        return sql;
    }

    private  void checkKeyWord(String name){
        AssertUtil.notNull(name,"配置文件节点name不能为空,请检查爬虫文件");
        if(SQLBLZ.toUpperCase().indexOf(name.toLowerCase())>-1){
            throw  new RuntimeException("爬虫关键字与数据库关键字有冲突,"+name);
        }
        String[] keys=new String[]{"id","grab_time","create_by","update_by","update_date","create_date","remarks","del_flag"};
        for (String key: keys){
            if(key.toLowerCase().equals(name.toLowerCase())){
                throw  new RuntimeException("爬虫文件中有关键字冲突:"+name);
            }
        }
        if(StringUtils.IshaveUpperCase(name)) throw  new RuntimeException("爬虫文件文件中规则定义不适用驼峰命名法,请使用下划线拆开:"+name);
    }


    /**
      *获取主键
     **/
    public   Map   ParserJsonToPk(String json,String tableName){
        try{

            List<String> sqls=new ArrayList<String>();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement=parser.parse(json);
            JsonArray sub=jsonElement.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("subitem").getAsJsonArray();
            List<Subitem> subitemList=new Gson().fromJson(new Gson().toJsonTree(sub),new TypeToken<List<Subitem>>(){}.getType());
            Map map=new HashMap();
            getPk(subitemList,tableName,map);
            return  map;
        }catch (RuntimeException e){
            throw  e;
        }catch (Exception e){
            logger.error("解析策略文件错误",e);
            throw  new RuntimeException("解析爬虫文件出错,请检查爬虫文件");
        }
    }
    private  void  getPk(List<Subitem> subitemList,String table,Map map) {
        List<String> pks=new ArrayList<String>();
        for (Subitem subitem:subitemList){
            checkKeyWord(subitem.getName());
            if("0".equals(subitem.getIs_update())){
                pks.add(subitem.getName());
            }
        }
        map.put(table,pks);
        for (Subitem subitem:subitemList){
            if (subitem.getSubitem() != null) {
                getPk(subitem.getSubitem(),table+=("_"+subitem.getName()),map);
            }
        }

    }
    public  List<Subitem> getSubitem(String json){
        List<String> sqls=new ArrayList<String>();
        JsonParser parser = new JsonParser();
        JsonElement jsonElement=parser.parse(json);
        JsonArray sub=jsonElement.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("subitem").getAsJsonArray();
        List<Subitem> subitemList=new Gson().fromJson(new Gson().toJsonTree(sub),new TypeToken<List<Subitem>>(){}.getType());
        return subitemList;
    }
}