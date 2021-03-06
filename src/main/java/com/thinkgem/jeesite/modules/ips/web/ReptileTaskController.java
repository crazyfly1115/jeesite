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
	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"startTask"})
	@ResponseBody
	public String startTask(@RequestParam(required = true)String id){
		ReptileTask reptileTask=reptileTaskService.get(id);
		AssertUtil.notNull(reptileTask,"未查询到相关数据");
		reptileTaskService.startTask(reptileTask);
		return new Ret(0,"部署成功").toString();
	}

	/**
	下线任务 将爬虫中的任务和和存储中的任务删除
	 * @version v1000
	 **/
	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"stopTask"})
	@ResponseBody
	public String stopTask(@RequestParam(required = true)String id){

		reptileTaskService.deleteTask(id,2);
		return new Ret(0,"删除成功").toString();
	}

	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"task_schedule_start"})
	@ResponseBody
	public String task_schedule_start(@RequestParam(required = true)String id){

		reptileTaskService.task_schedule(id,0);
		return new Ret(0,"启动成功").toString();
	}
	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"task_schedule_stop"})
	@ResponseBody
	public String task_schedule_stop(@RequestParam(required = true)String id){

		reptileTaskService.task_schedule(id,1);
		return new Ret(0,"停止成功").toString();
	}
	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"getTJSJ"})
	@ResponseBody
	public String getTJSJ(){

		return new Ret().putData(reptileTaskService.getTJSJ()).toString();
	}
//	@RequiresPermissions("ips:reptileTask:view")
//	@RequestMapping(value = {"changeState"})
//	@ResponseBody
//	public String changeState(@RequestParam(required = true)String id,@RequestParam(required = true)String state){
//		ReptileTask reptileTask=reptileTaskService.get(id);
//		AssertUtil.notNull(reptileTask,"未查询到相关数据");
//
//		return new Ret(0,"更新成功").toString();
//	}
	/**
	 * @version v1000
	 * 获取爬虫状态
	 **/
	@RequiresPermissions("ips:reptileTask:view")
	@RequestMapping(value = {"getTaskState"})
	@ResponseBody
	public String getTaskState(@RequestParam(required = true)String id){
		return new Ret(0,"获取成功").putData(reptileTaskService.getTaskState(id)).toString();
	}
}