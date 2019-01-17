package com.thinkgem.jeesite.modules.sys.listener;

import javax.servlet.ServletContext;

import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import org.springframework.web.context.WebApplicationContext;

import com.thinkgem.jeesite.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		new ZookeeperSession().createZ();

		return super.initWebApplicationContext(servletContext);
	}
}
