package com.thinkgem.jeesite.modules.ips.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.MalformedJsonException;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.ips.entity.Subitem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.ips.entity.Website;
import com.thinkgem.jeesite.modules.ips.dao.WebsiteDao;

/**
 * 网站数据Service
 * @author zhangsy
 * @version 2019-01-03
 *  尚渝网络
 */
@Service
@Transactional(readOnly = true)
public class WebsiteService extends CrudService<WebsiteDao, Website> {

    public   static  final  String BDPOI="BDPOI";
    public  static final String GDPOI="GDPOI";
    //根据模板类型  分类数据
    public List getTypelist(String id) {

        String type=getType(id);
        if(BDPOI.equals(type)){
            return dao.getType("bd");
        }
        if(GDPOI.equals(type)){
            return  dao.getType("gd");
        }
        throw  new RuntimeException("该类型没有分类列表");
    }
    //根据模板类型
    public String getType(String id) {

        Website website=super.get(id);
        AssertUtil.notNull(website,"没有查询到相关模板数据");

        website.getCrawlerId().getCrawlerUrl();

        String json=StringUtils.readToString(Global.getUserfilesBaseDir()+website.getCrawlerId().getCrawlerUrl());
        String type=ParserJsonToType(json);
        return type;
    }



    /**
     *获取类型
     **/
    public String ParserJsonToType(String json){
        try {
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(json);
            return jsonElement.getAsJsonObject().get("task_type").getAsString();
        }catch (JsonSyntaxException e){
            e.printStackTrace();
            throw  new RuntimeException("解析爬虫文件出错,请检查爬虫文件");
        } catch (RuntimeException e){
            throw  e;
        }catch (Exception e){
            logger.error("解析策略文件错误",e);
            throw  new RuntimeException("解析爬虫文件出错,请检查爬虫文件");
        }
    }
}