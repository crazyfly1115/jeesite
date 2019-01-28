/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.RegEx;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.bean.Ret;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.Dict;
import com.thinkgem.jeesite.modules.sys.service.DictService;

/**
 * 字典Controller
 * @author ThinkGem
 * @version 2014-05-16
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/dict")
public class DictController extends BaseController {

	@Autowired
	private DictService dictService;

	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(Dict dict, HttpServletRequest request, HttpServletResponse response, Model model) {
		String isYw=request.getParameter("isYw");
		/*if(StringUtils.isBlank(isYw)){
			isYw="";
		}*/
		List<String> typeList = dictService.findTypeList(isYw);
        Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
		return  new Ret().putMap("data",page).putMap("typeList",typeList).toString();
	}
	/*
	 * @Author zhangsy
	 * @Description  DictController
	 * @Date 15:13 2019/1/27
	 * @Param [dict, request, response, model]
	 * @return java.lang.String
	 * @Company 重庆尚渝网络科技
	 * @version v1000
	 * 查找字典类型
	 **/
	@RequiresPermissions("sys:dict:view")
	@RequestMapping(value = {"findTypeList"})
	@ResponseBody
	public String findTypeList(String isYw) {
		List<Map<String ,String>> typeList = dictService.findTypeMap(isYw);
		//Page<Dict> page = dictService.findPage(new Page<Dict>(request, response), dict);
		if(typeList.size()==0 ||typeList==null){
			return null;
		}
		return  new Ret("data",typeList).toString();
	}


	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(Dict dict, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			return new Ret(1,"演示模式，不允许操作！").toString();
		}
		if (!beanValidator(model, dict)){
			return new Ret(1,model.asMap().toString()).toString();
		}
		dictService.save(dict);
		return  new Ret().putMap("data",dict).toString();
	}
	
	@RequiresPermissions("sys:dict:edit")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(Dict dict, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			return new Ret(1,"演示模式，不允许操作！").toString();
		}
		dictService.delete(dict);
		return new Ret(0,"删除成功").toString();
	}
	
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String type, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		Dict dict = new Dict();
		dict.setType(type);
		List<Dict> list = dictService.findList(dict);
		for (int i=0; i<list.size(); i++){
			Dict e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", StringUtils.replace(e.getLabel(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
	/**
	 * @Author zhangsy
	 * @Description  获取字典数据，不传类型获取全部数据
	 * @Date 15:59 2018/12/27
	 * @Param [type]
	 * @return java.util.List<com.thinkgem.jeesite.modules.sys.entity.Dict>
	 * @Company 重庆尚渝网络科技
	 * @version v1000
	 **/
	@ResponseBody
	@RequestMapping(value = "listData")
	public String  listData(@RequestParam(required=false) String type) {

			Dict dict = new Dict();
			List<Dict> datalist=dictService.findList(dict);//数据
			//{ type:[{value:xz,lable:xx,sort:xx}]}
			Map<String,List<Map>> map=new HashMap<String, List<Map>>();

			for (Dict t:datalist){
				Map mt=new HashMap();
				mt.put("value",t.getValue());
				mt.put("label",t.getLabel());
				mt.put("type",t.getType());
				mt.put("sort",t.getSort());
				if(map.containsKey(t.getType())){

					map.get(t.getType()).add(mt);
				}else{
					List mtlist=new ArrayList<Map>();
					mtlist.add(mt);
					map.put(t.getType(),mtlist);
				}
			}
		if(StringUtils.isBlank(type)){
			return  new  Ret().putMap("data",map).toString();
		}else{
			return  new  Ret().putMap("data",map.get(type)).toString();
		}
	}

}
