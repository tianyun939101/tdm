package com.demxs.tdm.common.sys.listener;

import com.demxs.tdm.common.utils.SpringContextHolder;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

public class WebContextListener extends org.springframework.web.context.ContextLoaderListener {
	
	@Override
	public WebApplicationContext initWebApplicationContext(ServletContext servletContext) {
		/*if (!SystemService.printKeyLoadMessage()){
			return null;
		}*/
		SpringContextHolder.init(servletContext);
		return super.initWebApplicationContext(servletContext);
	}
}
