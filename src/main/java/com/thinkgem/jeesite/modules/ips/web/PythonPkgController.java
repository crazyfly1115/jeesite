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
import com.thinkgem.jeesite.modules.ips.entity.PythonPkg;
import com.thinkgem.jeesite.modules.ips.service.PythonPkgService;

/**
 * 执行包上传管理Controller
 * @author zhgnsy
 * @version 2019-01-15
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/pythonPkg")
public class PythonPkgController extends BaseController {

	@Autowired
	private PythonPkgService pythonPkgService;

	@RequiresPermissions("ips:pythonPkg:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 PythonPkg pythonPkg=gson.fromJson(json, PythonPkg.class);

		beanValidator(pythonPkg);
	
		pythonPkgService.save(pythonPkg);

	    return new Ret(0,MsgSuccess).putMap("data",pythonPkg).toString();
	}
	@RequiresPermissions("ips:pythonPkg:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		pythonPkgService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:pythonPkg:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(PythonPkg pythonPkg, HttpServletRequest request, HttpServletResponse response) {
		Page<PythonPkg> page = pythonPkgService.findPage(new Page<PythonPkg>(request, response), pythonPkg);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:pythonPkg:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    PythonPkg pythonPkg = pythonPkgService.get(id);
		if(pythonPkg==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",pythonPkg).toString();
	}
    /*
     * 对应table=tb_python_pkg
     **/
	private final static String dataBaseId ="a8b512dcbaee431387cb0de7f2f309aa";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}