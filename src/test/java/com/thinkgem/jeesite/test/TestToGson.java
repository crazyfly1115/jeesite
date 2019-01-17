package com.thinkgem.jeesite.test;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedHashTreeMap;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.thinkgem.jeesite.modules.ips.entity.Subitem;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestToGson {
    @Test
    public   void  test() throws InvocationTargetException, IllegalAccessException {
        String json="{\n" +
                "	\"name\": \"百度POI\",\n" +
                "	\"method\": \"GET\",\n" +
                "	\"type\": \"BDPOI\",\n" +
                "	\"key\": \"Ucmrifav2HUpUFp3FgFbTEkk4NGEuWTo\",\n" +
                "	\"parameter\": \"query=重庆&tag=美食&region=重庆&output=json&ak=Ucmrifav2HUpUFp3FgFbTEkk4NGEuWTo&city_limit=true&page_num=20\",\n" +
                "	\"inlet\": \"http://api.map.baidu.com/place/v2/search\",\n" +
                "	\"domain\": \"http://api.map.baidu.com\",\n" +
                "	\"withjs\": false,\n" +
                "	\"islogin\": false,\n" +
                "	\"selectlocation\": [\".fpp_cooking li\"],\n" +
                "	\"nlocation\": {\n" +
                "		\"type\": \"auto\",\n" +
                "		\"pre\": \"pg\",\n" +
                "		\"tag\": \"page_num\"\n" +
                "	},\n" +
                "	\"items\": [{\n" +
                "		\"name\": \"pois\",\n" +
                "		\"type\": \"item\",\n" +
                "		\"html\": \"json\",\n" +
                "		\"json\": \"results\",\n" +
                "		\"subitem\": [{\n" +
                "			\"name\": \"uid\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"uid\",\n" +
                "			\"field_des\": \"POI唯一健\",\n" +
                "			\"is_update\": \"1\",\n" +
                "			\"subitem\":[\n" +
                "			{\n" +
                "			\"name\": \"name\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"name\",\n" +
                "			\"field_des\": \"POI名称\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}\n" +
                "		]\n" +
                "		}, {\n" +
                "			\"name\": \"name\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"name\",\n" +
                "			\"field_des\": \"POI名称\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"loca_lat\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"location.lat\",\n" +
                "			\"field_des\": \"经纬度经度\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"loca_lng\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"location.lng\",\n" +
                "			\"field_des\": \"经纬度纬度\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"address\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"address\",\n" +
                "			\"field_des\": \"地址\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"province\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"province\",\n" +
                "			\"field_des\": \"省份\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"city\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"city\",\n" +
                "			\"field_des\": \"城市\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"area\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"area\",\n" +
                "			\"field_des\": \"地区\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"telephone\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"telephone\",\n" +
                "			\"field_des\": \"电话号码\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"tag\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"detail_info.tag\",\n" +
                "			\"field_des\": \"标签\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"price\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"detail_info.price\",\n" +
                "			\"field_des\": \"人均消费\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}, {\n" +
                "			\"name\": \"overall_rating\",\n" +
                "			\"type\": \"value\",\n" +
                "			\"html\": \"json\",\n" +
                "			\"json\": \"detail_info.overall_rating\",\n" +
                "			\"field_des\": \"总评分\",\n" +
                "			\"is_update\": \"1\"\n" +
                "		}]\n" +
                "	}]\n" +
                "}";
//        Map map=new Gson().fromJson(json,HashMap.class);
//        List<Map> list=(List<Map>) map.get("items");
//
//        List ss=(List) list.get(0).get("subitem");
//
//
//        for ( Object o: ss){
//
//            System.out.println(o instanceof LinkedTreeMap);
//            Subitem s=new Subitem();
//            BeanUtils.populate(s, (Map)o);
//            while (s.getSubitem()!=null){
//
//            }
//            System.out.println(s);
//        }
//        List<Subitem> s=new ArrayList<Subitem>();
//        System.out.println(s);
//
//        for (Subitem sb:s){
//            System.out.println(sb instanceof  Subitem);
//            System.out.println(sb.getField_des());
//        }
        JsonParser parser = new JsonParser();
        JsonElement jsonElement=parser.parse(json);
        JsonArray sub=jsonElement.getAsJsonObject().get("items").getAsJsonArray().get(0).getAsJsonObject().get("subitem").getAsJsonArray();
        List<Subitem> subitemList=new  Gson().fromJson(new Gson().toJsonTree(sub),new TypeToken<List<Subitem>>(){}.getType());
        System.out.println(getSQL(subitemList,"tb_baidu"));
    }
    private  String  getSQL(List<Subitem> subitemList,String table) {
        String sql="DROP TABLE IF EXISTS "+table+"; CREATE TABLE "+table+"(";
        for (Subitem subitem:subitemList){

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
              sql+= getSQL(subitem.getSubitem(),table+=subitem.getName());
            }
        }
        return sql;
    }
}
