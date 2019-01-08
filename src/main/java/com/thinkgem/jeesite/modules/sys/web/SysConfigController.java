package com.thinkgem.jeesite.modules.sys.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.bean.Ret;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.sys.entity.SysConfig;
import com.thinkgem.jeesite.modules.sys.service.SysConfigService;

/**
 * 配置Controller
 * @author zhangsy
 * @version 2018-12-28
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "/config")
public class SysConfigController extends BaseController {

	@Autowired
	private SysConfigService sysConfigService;

	@RequiresPermissions("sys:sysConfig:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestBody SysConfig sysConfig) {
	
		beanValidator(sysConfig);

	    return new Ret(0,MsgSuccess).putMap("data",sysConfig).toString();
	}
	@RequiresPermissions("sys:sysConfig:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam("ids[]") String[] ids) {
		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		sysConfigService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("sys:sysConfig:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(SysConfig sysConfig, HttpServletRequest request, HttpServletResponse response) {
		Page<SysConfig> page = sysConfigService.findPage(new Page<SysConfig>(request, response), sysConfig);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("sys:sysConfig:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    SysConfig sysConfig = sysConfigService.get(id);
		if(sysConfig==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",sysConfig).toString();
	}
    /*
     * 对应table=sys_config
     **/
	private final static String dataBaseId ="eddd000246e347fb9690c4dbc5127378";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
	
	
	/**
	 * @Author zhangsy
	 * @Description 系统数据
	 * @Date 11:58 2018/12/28
	 * @Param []
	 * @return java.lang.String
	 * @Company 重庆尚渝网络科技
	 * @version v1000
	 **/
	@RequestMapping(value = {"getConfig"})
	@ResponseBody
	public String getConfig(){
		return new Ret().putMap("configList",sysConfigService.findList(new SysConfig())).toString();
	}
}