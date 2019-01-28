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
import com.thinkgem.jeesite.modules.ips.entity.StorageService;
import com.thinkgem.jeesite.modules.ips.service.StorageServiceService;

/**
 * 存储容器
 * @author zhangsy
 * @version 2019-01-16
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/storageService")
public class StorageServiceController extends BaseController {

	@Autowired
	private StorageServiceService storageServiceService;

	@RequiresPermissions("ips:storageService:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 StorageService storageService=gson.fromJson(json, StorageService.class);

		beanValidator(storageService);
	
		storageServiceService.save(storageService);

	    return new Ret(0,MsgSuccess).putMap("data",storageService).toString();
	}
	@RequiresPermissions("ips:storageService:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		storageServiceService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:storageService:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(StorageService storageService, HttpServletRequest request, HttpServletResponse response) {

		return new Ret("data",storageServiceService.getListByZookeeper()).toString();
	}
	@RequiresPermissions("ips:storageService:view")
	@RequestMapping(value = {"getListMsg"})
	@ResponseBody
	public String getListMsg(StorageService storageService, HttpServletRequest request, HttpServletResponse response) {
		String name=request.getParameter("zookeeperName");
		return new Ret("data",storageServiceService.getZookeeperMsg(name)).toString();
	}

	@RequiresPermissions("ips:storageService:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    StorageService storageService = storageServiceService.get(id);
		if(storageService==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",storageService).toString();
	}
    /*
     * 对应table=tb_storage_service
     **/
	private final static String dataBaseId ="177f6a0b542d41a085e7c0c768887e4f";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}