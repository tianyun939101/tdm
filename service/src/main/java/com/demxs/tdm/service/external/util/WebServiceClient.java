package com.demxs.tdm.service.external.util;

import com.landray.kmss.sys.notify.webservice.ISysNotifyTodoWebService;
import com.landray.kmss.sys.notify.webservice.NotifyTodoAppResult;
import com.landray.kmss.sys.notify.webservice.NotifyTodoSendContext;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;


/**
 * WebService客户端
 * 
 */
public class WebServiceClient {

	/**
	 * 主方法
	 * 
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//WebServiceConfig cfg = WebServiceConfig.getInstance();

		ISysNotifyTodoWebService service = (ISysNotifyTodoWebService)callService("www.baidu.com", Class.forName("com.landray.kmss.sys.notify.webservice.ISysNotifyTodoWebService"));
		// 请在此处添加业务代码
		NotifyTodoSendContext context = new NotifyTodoSendContext();

		context.setAppName("lims");
		context.setCreateTime("2017-11-28 10:13:30");
		context.setLink("www.baidu.com");
		context.setType(1); //1待办,2待阅
		context.setModelId("12345668");
		context.setModelName("lims");
		context.setSubject("审批");
		context.setTargets("{\"LoginName\":\"ranhl\"}");
		context.setDocCreator("{\"LoginName\":\"ranhl\"}");
		NotifyTodoAppResult result = service.sendTodo(context);
		System.out.println("((((((((((((((((="+result.toString());

	}
	
	/**
	 * 调用服务，生成客户端的服务代理
	 * 
	 * @param address WebService的URL
	 * @param serviceClass 服务接口全名
	 * @return 服务代理对象
	 * @throws Exception
	 */
	public static Object callService(String address, Class serviceClass)
			throws Exception {

		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();

		// 记录入站消息
		factory.getInInterceptors().add(new LoggingInInterceptor());

		// 记录出站消息
		factory.getOutInterceptors().add(new LoggingOutInterceptor());

		// 添加消息头验证信息。如果服务端要求验证用户密码，请加入此段代码
		factory.getOutInterceptors().add(new AddSoapHeader());

		factory.setServiceClass(serviceClass);
		factory.setAddress(address);

		// 创建服务代理并返回
		return factory.create();
	}

}
