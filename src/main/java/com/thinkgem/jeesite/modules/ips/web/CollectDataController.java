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
import com.thinkgem.jeesite.modules.ips.entity.CollectData;
import com.thinkgem.jeesite.modules.ips.service.CollectDataService;

/**
 * 采集数据Controller
 * @author zhangsy
 * @version 2019-01-04
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/collectData")
public class CollectDataController extends BaseController {

	@Autowired
	private CollectDataService collectDataService;

	@RequiresPermissions("ips:collectData:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 CollectData collectData=gson.fromJson(json, CollectData.class);

		beanValidator(collectData);
	
		collectDataService.save(collectData);

	    return new Ret(0,MsgSuccess).putMap("data",collectData).toString();
	}
	@RequiresPermissions("ips:collectData:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		collectDataService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:collectData:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(CollectData collectData, HttpServletRequest request, HttpServletResponse response) {
		Page<CollectData> page = collectDataService.findPage(new Page<CollectData>(request, response), collectData);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:collectData:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    CollectData collectData = collectDataService.get(id);
		if(collectData==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",collectData).toString();
	}
    /*
     * 对应table=tb_collect_data
     **/
	private final static String dataBaseId ="7f3a134d91734186ae812c7b5dcdd093";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
}