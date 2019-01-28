package com.thinkgem.jeesite.modules.ips.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.bean.Ret;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ips.entity.Website;
import com.thinkgem.jeesite.modules.ips.service.WebsiteService;

import java.util.List;

/**
 * 网站数据Controller
 * @author zhangsy
 * @version 2019-01-03
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/website")
public class WebsiteController extends BaseController {

	@Autowired
	private WebsiteService websiteService;

	@RequiresPermissions("ips:website:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 Website website=gson.fromJson(json, Website.class);

		beanValidator(website);
	
		websiteService.save(website);

	    return new Ret(0,MsgSuccess).putMap("data",website).toString();
	}
	@RequiresPermissions("ips:website:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		websiteService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:website:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(Website website, HttpServletRequest request, HttpServletResponse response) {
		Page<Website> page = websiteService.findPage(new Page<Website>(request, response), website);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:website:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    Website website = websiteService.get(id);
		if(website==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",website).toString();
	}
    /*
     * 对应table=tb_website
     **/
	private final static String dataBaseId ="562e2e65ad43416aa952c5589950bb83";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}

	/**
	 * 根据模板id 给出网站分类
	 *
	 **/
	@RequiresPermissions("ips:website:view")
	@RequestMapping(value = {"getTypeList"})
	@ResponseBody
	public String getTypeList(String id){

		List list=websiteService.getTypelist(id);
		return new Ret().putData(list).toString();

	}
	@RequiresPermissions("ips:website:view")
	@RequestMapping(value = {"getType"})
	@ResponseBody
	public String getType(String id){

		return new Ret().putData(websiteService.getType(id)).toString();

	}

}