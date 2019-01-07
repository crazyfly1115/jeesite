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
import com.thinkgem.jeesite.modules.ips.entity.CollectField;
import com.thinkgem.jeesite.modules.ips.service.CollectFieldService;

/**
 * 字段管理Controller
 * @author zhangsy
 * @version 2019-01-03
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/collectField")
public class CollectFieldController extends BaseController {

	@Autowired
	private CollectFieldService collectFieldService;

	@RequiresPermissions("ips:collectField:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 CollectField collectField=gson.fromJson(json, CollectField.class);

		beanValidator(collectField);
	
		collectFieldService.save(collectField);

	    return new Ret(0,MsgSuccess).putMap("data",collectField).toString();
	}
	@RequiresPermissions("ips:collectField:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		collectFieldService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:collectField:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(CollectField collectField, HttpServletRequest request, HttpServletResponse response) {
		Page<CollectField> page = collectFieldService.findPage(new Page<CollectField>(request, response), collectField);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:collectField:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    CollectField collectField = collectFieldService.get(id);
		if(collectField==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",collectField).toString();
	}
    /*
     * 对应table=tb_collect_field
     **/
	private final static String dataBaseId ="c7f64422e88c45679c7b890a668375a7";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}