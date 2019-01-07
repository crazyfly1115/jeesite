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
import com.thinkgem.jeesite.modules.ips.entity.ServiceTask;
import com.thinkgem.jeesite.modules.ips.service.ServiceTaskService;

/**
 * 爬虫服务任务管关联Controller
 * @author zhangsy
 * @version 2019-01-07
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/serviceTask")
public class ServiceTaskController extends BaseController {

	@Autowired
	private ServiceTaskService serviceTaskService;

	@RequiresPermissions("ips:serviceTask:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 ServiceTask serviceTask=gson.fromJson(json, ServiceTask.class);

		beanValidator(serviceTask);
	
		serviceTaskService.save(serviceTask);

	    return new Ret(0,MsgSuccess).putMap("data",serviceTask).toString();
	}
	@RequiresPermissions("ips:serviceTask:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		serviceTaskService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:serviceTask:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(ServiceTask serviceTask, HttpServletRequest request, HttpServletResponse response) {
		Page<ServiceTask> page = serviceTaskService.findPage(new Page<ServiceTask>(request, response), serviceTask);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:serviceTask:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    ServiceTask serviceTask = serviceTaskService.get(id);
		if(serviceTask==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",serviceTask).toString();
	}
    /*
     * 对应table=tb_service_task
     **/
	private final static String dataBaseId ="e89967ed2f074b52b5005fc1b4c8681e";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}