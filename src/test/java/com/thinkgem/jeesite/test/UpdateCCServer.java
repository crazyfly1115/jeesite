package com.thinkgem.jeesite.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.modules.zookeeper.ClintUtil;
import org.apache.velocity.runtime.directive.Foreach;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCCServer {

    public static void main(String[] args) {

//        List<Map> list=new Gson().fromJson(json,new TypeToken<List<Map>>(){}.getType());
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(list.get(i).get("task_id"));
//            delete(list.get(i).get("task_id").toString());
//        }
//        System.out.println(list);
        String id="ea7d0103a593472686013c6f79eb7bb9";
        delete(id);
//        updateTime();

    }
    private static void  updateTime(){
        String url="http://49.4.7.57:9527/sjgzimp/taskserver";
        Map map=new HashMap();
        //323d1f847ae74a019c074ca80d46159c
        // ea7d0103a593472686013c6f79eb7bb9
        map.put("task_id","323d1f847ae74a019c074ca80d46159c");
        map.put("task_status","ON");
        map.put("mq_eachpulltime","10");
        String json=new Gson().toJson(map);
        String rs=ClintUtil.postClint(url,json);
        System.out.println(rs);
    }
    private static  void  delete(String id){
        String url="http://49.4.7.57:9527/sjgzimp/taskserver";
        Map map=new HashMap();
        map.put("task_id",id);
        map.put("task_status","DEL");
        map.put("mq_eachpulltime","10");
        String json=new Gson().toJson(map);
        String rs=ClintUtil.postClint(url,json);
        System.out.println(rs);
    }
}
