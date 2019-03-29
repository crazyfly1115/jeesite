package com.thinkgem.jeesite.modules.ips.web;

import com.thinkgem.jeesite.common.bean.Ret;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.modules.ips.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author zhangsy
 * @Description  PorxyIpsController
 * @Date 10:31 2019/3/29
 * @Param
 * @return
 * @Company 重庆尚渝网络科技
 * @version v1000
 **/
@Controller
@RequestMapping(value = "${frontPath}/ips/proxy")
public class ProxyIpsController extends BaseController {
	@Autowired
	private ProxyService proxyService;
	@RequestMapping(value = "getip")
	@ResponseBody
	public String save(String taskid,String num,String exendip) {




		return new Ret(0,MsgSuccess).putMap("data",proxyService.getIps(num,taskid,exendip)).toString();
	}
}