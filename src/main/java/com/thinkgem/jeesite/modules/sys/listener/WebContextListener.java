package com.thinkgem.jeesite.modules.sys.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import com.thinkgem.jeesite.modules.ips.service.MQService;
import com.thinkgem.jeesite.modules.zookeeper.ZookeeperSession;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.web.context.WebApplicationContext;

import com.thinkgem.jeesite.modules.sys.service.SystemService;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		if (!SystemService.printKeyLoadMessage()){
			return null;
		}
		new ZookeeperSession().createZ();
		try {
			new MQService().getErrMessage();
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		return super.initWebApplicationContext(servletContext);
	}

	@Override
	public void contextInitialized(ServletContextEvent event) {

		super.contextInitialized(event);
	}
}
