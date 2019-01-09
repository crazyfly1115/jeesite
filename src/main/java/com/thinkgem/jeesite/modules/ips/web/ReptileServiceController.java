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
import com.thinkgem.jeesite.modules.ips.entity.ReptileService;
import com.thinkgem.jeesite.modules.ips.service.ReptileServiceService;

/**
 * 服务器管理Controller
 * @author zhangsy
 * @version 2019-01-03
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/reptileService")
public class ReptileServiceController extends BaseController {

	@Autowired
	private ReptileServiceService reptileServiceService;

	@RequiresPermissions("ips:reptileService:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 ReptileService reptileService=gson.fromJson(json, ReptileService.class);

		beanValidator(reptileService);
	
		reptileServiceService.save(reptileService);

	    return new Ret(0,MsgSuccess).putMap("data",reptileService).toString();
	}
	@RequiresPermissions("ips:reptileService:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		reptileServiceService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:reptileService:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(ReptileService reptileService, HttpServletRequest request, HttpServletResponse response) {
		Page<ReptileService> page = reptileServiceService.findPage(new Page<ReptileService>(request, response), reptileService);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:reptileService:view")
	@RequestMapping(value = {"listAll"})
	@ResponseBody
	public String listAll(ReptileService reptileService) {
		return new Ret("data",reptileServiceService.findList(reptileService)).toString();
	}
	@RequiresPermissions("ips:reptileService:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    ReptileService reptileService = reptileServiceService.get(id);
		if(reptileService==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",reptileService).toString();
	}
    /*
     * 对应table=tb_service
     **/
	private final static String dataBaseId ="799aaa2d72f54b5d9ce4944919fa5d47";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}

	@RequiresPermissions("ips:reptileService:view")
	@RequestMapping(value = {"updateServer"})
	@ResponseBody
	public String updateServer(@RequestParam(required = true) String id) {


		reptileServiceService.serviceConfig(reptileServiceService.get(id));

		return new Ret().toString();
	}
	@RequiresPermissions("ips:reptileService:view")
	@RequestMapping(value = {"getServer"})
	@ResponseBody
	public String getServer() {
		return new Ret().putData(reptileServiceService.getServer()).toString();
	}
}