package com.thinkgem.jeesite.modules.ips.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.google.gson.reflect.TypeToken;
import com.thinkgem.jeesite.common.bean.Ret;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.Vuetree.VueTreeDTO;
import com.thinkgem.jeesite.common.utils.excel.ExportExcel;
import com.thinkgem.jeesite.modules.gen.entity.GenTable;
import com.thinkgem.jeesite.modules.gen.entity.GenTableColumn;
import com.thinkgem.jeesite.modules.gen.entity.SerachBean;
import com.thinkgem.jeesite.modules.gen.entity.SerachColumn;
import com.thinkgem.jeesite.modules.ips.entity.CollectField;
import com.thinkgem.jeesite.modules.ips.entity.CollectTable;
import com.thinkgem.jeesite.modules.ips.entity.ReptileService;
import com.thinkgem.jeesite.modules.ips.service.CollectTableService;
import com.thinkgem.jeesite.modules.ips.service.DuridService;
import com.thinkgem.jeesite.modules.ips.service.ReptileTaskService;
import com.thinkgem.jeesite.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ips.entity.Database;
import com.thinkgem.jeesite.modules.ips.service.DatabaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库Controller
 * @author zhangsy
 * @version 2019-01-02
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/database")
public class DatabaseController extends BaseController {

	@Autowired
	private DatabaseService databaseService;
	@Autowired
	private DuridService duridService;
	@Autowired
	private CollectTableService collectTableService;
	@Autowired
	private ReptileTaskService reptileTaskService;
	@RequiresPermissions("ips:database:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 Database database=gson.fromJson(json, Database.class);

		beanValidator(database);
	
		databaseService.save(database);

	    return new Ret(0,MsgSuccess).putMap("data",database).toString();
	}
	@RequiresPermissions("ips:database:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		databaseService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(Database database, HttpServletRequest request, HttpServletResponse response) {
		Page<Database> page = databaseService.findPage(new Page<Database>(request, response), database);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    Database database = databaseService.get(id);
		if(database==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",database).toString();
	}
    /*
     * 对应table=tb_database
     **/
	private final static String dataBaseId ="2885b841b60747ff8de72481c8c45794";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
	/*
	*
	*获取数据库中真实的表
	*
	*/
	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"getDBTableById"})
	@ResponseBody
	public  String getDBTableById(@RequestParam(required = true) String id){

		Database database=databaseService.get(id);
		AssertUtil.notNull(database,"数据库中未查询到该数据库");
		List list=new ArrayList();
		for(GenTable g:duridService.getTables(database)){
			VueTreeDTO v=new VueTreeDTO();
			v.setId(g.getName());
			v.setLabel(g.getNameAndComments());
			list.add(v);
		}
		return new Ret().putMap("data",list).toString();

	}
	/**
	 * @Author zhangsy
	 * @Description 获取数据库中存储的数据表
	 * @Date 12:35 2019/1/5
	 * @Param [id]
	 * @return java.lang.String
	 * @Company 重庆尚渝网络科技
	 * @version v1000
	 **/
	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"getTableById"})
	@ResponseBody
	public  String getTableById(@RequestParam(required = true) String id){

		Database database=databaseService.get(id);
		AssertUtil.notNull(database,"数据库中未查询到该数据库");
		CollectTable c=new CollectTable();
		c.setDatabaseId(database);

		List list=new ArrayList();
		for(CollectTable g:collectTableService.findList(c)){
			VueTreeDTO v=new VueTreeDTO();
			v.setId(g.getId());
			v.setLabel(g.getTableName());
			list.add(v);
		}
		return new Ret().putMap("data",list).toString();

	}

	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"listAll"})
	@ResponseBody
	public String listAll(Database database) {
		List list=new ArrayList();
		for(Database g:databaseService.findList(database)){
			VueTreeDTO v=new VueTreeDTO();
			v.setId(g.getId());
			v.setLabel(g.getDatabaseName());
			list.add(v);
		}
		return new Ret().putData(list).toString();
	}

	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"getColumnByTable"})
	@ResponseBody
	public  String getColumnByTable(@RequestParam(required = true) String id,@RequestParam(required = true)String tableName,@RequestParam(required = true)String taskId){

		Database database=databaseService.get(id);
		AssertUtil.notNull(database,"数据库中未查询到该数据库");
		List list=new ArrayList();
		list=reptileTaskService.getColumnByid(duridService.getColumnByTable(database,tableName),taskId,tableName);
		//todo 获取任务ID 将任务的数据的子表核算出来


		return new Ret().putMap("data",list).toString();

	}

	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"getTableData"})
	@ResponseBody
	public  String getTableData(@RequestParam(required = true) String id,@RequestParam(required = true) String tableName, String fkId,String json,HttpServletRequest request, HttpServletResponse response){

		Database database=databaseService.get(id);
		AssertUtil.notNull(database,"数据库中未查询到该数据库");
		List<SerachColumn> sclist=gson.fromJson(json,new TypeToken<List<SerachColumn>>(){}.getType());
		SerachBean serachBean=new SerachBean(tableName,sclist);
		serachBean.setFkId(fkId);
		return new Ret().putMap("data",duridService.getTableData(database,serachBean,new Page<Map>(request, response))).toString();

	}

	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = {"getDefData"})
	@ResponseBody
	public  String getDefData(HttpServletRequest request, HttpServletResponse response){
		Database database= databaseService.findDef();
		if(database==null){
			return null;
		}

		return new Ret().putMap("data",database).toString();

	}
	@RequiresPermissions("ips:database:view")
	@RequestMapping(value = "export")
	public String downTableData(@RequestParam(required = true) String id,@RequestParam(required = true) String tableName, String fkId,String json,HttpServletRequest request, HttpServletResponse response){
		Database database=databaseService.get(id);
		AssertUtil.notNull(database,"数据库中未查询到该数据库");
		List<SerachColumn> sclist=gson.fromJson(json,new TypeToken<List<SerachColumn>>(){}.getType());
		SerachBean serachBean=new SerachBean(tableName,sclist);
		serachBean.setFkId(fkId);
		Page page=new Page<Map>(request, response,-1);
		page.setPageSize(10000);
		duridService.getTableData(database,serachBean,page);

		List<GenTableColumn> list=duridService.getColumnByTable(database,tableName);
		List<String> headerList=new ArrayList<String>();
		List<String> keyList=new ArrayList<String>();
		for (GenTableColumn g:list){
			headerList.add(g.getComments());
			keyList.add(g.getName());
		}
		try {
			String fileName = tableName+DateUtils.getDate("_yyyyMMddHHmmss")+".xlsx";
			new ExportExcel(fileName, headerList).setMapList(page.getList(),keyList).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}