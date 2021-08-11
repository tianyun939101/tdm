package com.demxs.tdm.service.external.util;

import com.demxs.tdm.common.config.Global;

/**
 * 解析配置文件
 * 
 */
public class WebServiceConfig {
	private static WebServiceConfig cfg = new WebServiceConfig();

	/**
	 * Web服务的URL
	 */
	private String address;

	/**
	 * Web服务接口
	 */
	private Class serviceClass;

	/**
	 * Web服务标识
	 */
	private String serviceBean;

	/**
	 * 用户
	 */
	private String user;

	/**
	 * 密码
	 */
	private String password;

	private WebServiceConfig() {
		loadCfg();
	}

	public static WebServiceConfig getInstance() {
		return cfg;
	}

	/**
	 * 解析配置文件
	 */
		private void loadCfg() {
		this.address = Global.getConfig("address");
		String serviceClassName =  Global.getConfig("service_class");
		try {
			this.serviceClass = Class.forName(serviceClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		this.serviceBean =  Global.getConfig("service_bean");
		this.user =  Global.getConfig("user");
		this.password =  Global.getConfig("password");

	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Class getServiceClass() {
		return serviceClass;
	}

	public void setServiceClass(Class serviceClass) {
		this.serviceClass = serviceClass;
	}

	public String getServiceBean() {
		return serviceBean;
	}

	public void setServiceBean(String serviceBean) {
		this.serviceBean = serviceBean;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
