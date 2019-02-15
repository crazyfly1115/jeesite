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
import com.thinkgem.jeesite.modules.ips.entity.ServiceErr;
import com.thinkgem.jeesite.modules.ips.service.ServiceErrService;

/**
 * 错误日志Controller
 * @author zhangsy
 * @version 2019-02-13
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/serviceErr")
public class ServiceErrController extends BaseController {

	@Autowired
	private ServiceErrService serviceErrService;

	@RequiresPermissions("ips:serviceErr:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 ServiceErr serviceErr=gson.fromJson(json, ServiceErr.class);

		beanValidator(serviceErr);
	
		serviceErrService.save(serviceErr);

	    return new Ret(0,MsgSuccess).putMap("data",serviceErr).toString();
	}
	@RequiresPermissions("ips:serviceErr:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		serviceErrService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:serviceErr:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(ServiceErr serviceErr, HttpServletRequest request, HttpServletResponse response) {
		Page<ServiceErr> page = serviceErrService.findPage(new Page<ServiceErr>(request, response), serviceErr);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:serviceErr:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    ServiceErr serviceErr = serviceErrService.get(id);
		if(serviceErr==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",serviceErr).toString();
	}
    /*
     * 对应table=tb_service_err
     **/
	private final static String dataBaseId ="fc111a904fc64e5fa709f83aa35d8661";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}