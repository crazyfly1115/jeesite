package com.thinkgem.jeesite.modules.ips.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thinkgem.jeesite.common.bean.Ret;
import com.thinkgem.jeesite.common.utils.AssertUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ips.entity.ReptileTask;
import com.thinkgem.jeesite.modules.ips.service.ReptileTaskService;

/**
 * 爬虫任务Controller
 * @author zhangsy
 * @version 2019-01-04
 * 尚渝网络
 */
@Controller
@RequestMapping(value = "${adminPath}/ips/reptileTask")
public class ReptileTaskController extends BaseController {

	@Autowired
	private ReptileTaskService reptileTaskService;

	@RequiresPermissions("ips:reptileTask:edit")
	@RequestMapping(value = "save")
	@ResponseBody
	public String save(@RequestParam(required = true) String json) {

		 ReptileTask reptileTask=gson.fromJson(json, ReptileTask.class);

		beanValidator(reptileTask);
	
		reptileTaskService.save(reptileTask);

	    return new Ret(0,MsgSuccess).putMap("data",reptileTask).toString();
	}
	@RequiresPermissions("ips:reptileTask:delete")
	@RequestMapping(value = "delete")
	@ResponseBody
	public String delete(@RequestParam(required = true) String json) {

		String[] ids=gson.fromJson(json,String[].class);

		if(ids==null||ids.length==0){
			return new Ret(1, MsgReqNotNull).toString();
		}
		reptileTaskService.deleteByIds(ids);
		return new Ret(0, MsgDeleteSuccess).toString();
	}


	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"list"})
	@ResponseBody
	public String list(ReptileTask reptileTask, HttpServletRequest request, HttpServletResponse response) {
		Page<ReptileTask> page = reptileTaskService.findPage(new Page<ReptileTask>(request, response), reptileTask);
		return new Ret("data",page).toString();
	}
	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"getById"})
	@ResponseBody
	public String getById(@RequestParam(required = true) String id) {

	    ReptileTask reptileTask = reptileTaskService.get(id);
		if(reptileTask==null){
			return new Ret(1,MsgDataNotFinad).toString();
		}
		return new Ret("data",reptileTask).toString();
	}
    /*
     * 对应table=tb_task
     **/
	private final static String dataBaseId ="57d6c766f6514a249ca5f341937e7ff7";
	@RequiresPermissions("user")
	@RequestMapping(value = {"config"})
	@ResponseBody
	public String config(){
		return new Ret("data",super.getDataBaseConfig(dataBaseId)).toString();
	}
	
	/**
	 * @Author zhangsy
	 * @Description  更新数据至zookeeper
	 * @Date 10:20 2019/1/7
	 * @Param []
	 * @return java.lang.String
	 * @Company 重庆尚渝网络科技
	 * @version v1000
	 **/
	public String updateTask(@RequestParam(required = true)String id){
		ReptileTask reptileTask=reptileTaskService.get(id);
		AssertUtil.isNull(reptileTask,"未查询到相关数据");
		//TO-DO 操作zookeeper的相关数据

		return new Ret().toString();
	}
}