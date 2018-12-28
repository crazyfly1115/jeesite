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

/**
 * 网站管理Controller
 * @author zhangsy
 * @version 2018-12-27
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
	public String save(@RequestBody Website website) {
	
		beanValidator(website);

	    return new Ret(0,"保存网站成功").putMap("data",website).toString();
	}
	@RequiresPermissions("ips:website:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam("ids[]") String[] ids) {
		if(ids==null||ids.length==0){
			return new Ret(1, "ids为空,无数据").toString();
		}
		websiteService.deleteByIds(ids);
		return new Ret(0, "删除成功").toString();
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
			return new Ret(1, "未查询到数据").toString();
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
}