package com.thinkgem.jeesite.modules.ips.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.bean.Ret;
import com.thinkgem.jeesite.common.utils.Vuetree.VueTreeDTO;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ips.entity.CollectTable;
import com.thinkgem.jeesite.modules.ips.service.CollectTableService;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库表Controller
 * @author zhangsy
 * @version 2019-01-05
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/collectTable")
public class CollectTableController extends BaseController {

	@Autowired
	private CollectTableService collectTableService;

	@RequiresPermissions("ips:collectTable:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 CollectTable collectTable=gson.fromJson(json, CollectTable.class);

		beanValidator(collectTable);
	
		collectTableService.save(collectTable);

	    return new Ret(0,MsgSuccess).putMap("data",collectTable).toString();
	}
	@RequiresPermissions("ips:collectTable:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		collectTableService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:collectTable:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(CollectTable collectTable, HttpServletRequest request, HttpServletResponse response) {
		Page<CollectTable> page = collectTableService.findPage(new Page<CollectTable>(request, response), collectTable);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:collectTable:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    CollectTable collectTable = collectTableService.get(id);
		if(collectTable==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		
		return new Ret("data",collectTable).toString();
	}
    /*
     * 对应table=tb_collect_table
     **/
	private final static String dataBaseId ="2dad8ed16bf642538750fab027294c7e";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
	/**
	 * @Author zhangsy
	 * @Description  根据databaseid 获取
	 * @Date 15:37 2019/1/7
	 * @Param [database]
	 * @return java.lang.String
	 * @Company 重庆尚渝网络科技
	 * @version v1000
	 **/
	@RequiresPermissions("ips:collectTable:view")
	@RequestMapping(value = {"listTableAll"})
	@ResponseBody
	public String listTableAll(Database database) {
		CollectTable collectTable =new CollectTable();
		collectTable.setDatabaseId(database);
		List list=new ArrayList();
		for(CollectTable g:collectTableService.findList(collectTable)){
			VueTreeDTO v=new VueTreeDTO();
			v.setId(g.getId());
			v.setLabel(g.getTableName());
			list.add(v);
		}
		return new Ret().putData(list).toString();
	}
}