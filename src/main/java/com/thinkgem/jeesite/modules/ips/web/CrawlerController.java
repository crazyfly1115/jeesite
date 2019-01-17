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
import com.thinkgem.jeesite.modules.ips.entity.Crawler;
import com.thinkgem.jeesite.modules.ips.service.CrawlerService;

/**
 * 爬虫管理Controller
 * @author zhagnsy
 * @version 2019-01-15
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/crawler")
public class CrawlerController extends BaseController {

	@Autowired
	private CrawlerService crawlerService;

	@RequiresPermissions("ips:crawler:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 Crawler crawler=gson.fromJson(json, Crawler.class);

		beanValidator(crawler);
	
		crawlerService.save(crawler);

	    return new Ret(0,MsgSuccess).putMap("data",crawler).toString();
	}
	@RequiresPermissions("ips:crawler:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		crawlerService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:crawler:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(Crawler crawler, HttpServletRequest request, HttpServletResponse response) {
		Page<Crawler> page = crawlerService.findPage(new Page<Crawler>(request, response), crawler);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:crawler:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    Crawler crawler = crawlerService.get(id);
		if(crawler==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",crawler).toString();
	}
    /*
     * 对应table=tb_crawler
     **/
	private final static String dataBaseId ="3464b50796ea49b1ba80c6c18d314341";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}