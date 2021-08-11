package com.demxs.tdm.common.web;

import com.demxs.tdm.common.beanvalidator.BeanValidators;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.DateUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器支持类
 * @author ThinkGem
 * @version 2013-3-23
 */
public abstract class BaseController {


	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 管理基础路径
	 */
	@Value("${adminPath}")
	protected String adminPath;
	
	/**
	 * 前端基础路径
	 */
	@Value("${frontPath}")
	protected String frontPath;
	
	/**
	 * 前端URL后缀
	 */
	@Value("${urlSuffix}")
	protected String urlSuffix;
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 message 中
	 */
	protected boolean beanValidator(Model model, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(model, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组
	 * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
	 */
	protected boolean beanValidator(RedirectAttributes redirectAttributes, Object object, Class<?>... groups) {
		try{
			BeanValidators.validateWithException(validator, object, groups);
		}catch(ConstraintViolationException ex){
			List<String> list = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
			list.add(0, "数据验证失败：");
			addMessage(redirectAttributes, list.toArray(new String[]{}));
			return false;
		}
		return true;
	}
	
	/**
	 * 服务端参数有效性验证
	 * @param object 验证的实体对象
	 * @param groups 验证组，不传入此参数时，同@Valid注解验证
	 * @return 验证成功：继续执行；验证失败：抛出异常跳转400页面。
	 */
	protected void beanValidator(Object object, Class<?>... groups) {
		BeanValidators.validateWithException(validator, object, groups);
	}
	
	/**
	 * 添加Model消息
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException() {
        return "error/400";
    }
	
	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
    public String authenticationException() {
        return "error/403";
    }

	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({UnauthorizedException.class})
	public String unauthorizedException(HttpServletRequest request, HttpServletResponse response,Object responseObject) {
		//xhr.setRequestHeader('X-CLIENT-TYPE','front-end-browser');
		if("front-end-browser".equals(request.getHeader("X-CLIENT-TYPE"))){
			logger.error("请求失败，没有权限！requestURI:{}",request.getRequestURI());
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			Map map = new HashMap();
			map.put("status","403");
			try {
//				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				response.getWriter().print(JsonMapper.toJsonString(map));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}else{
			return "error/403";
		}
	}

//	/**
//	 * 异常
//	 */
//	@ExceptionHandler({Throwable.class})
//	public String throwable() {
//		return "error/500";
//	}

	/**
	 * 初始化数据绑定
	 * 1. 将所有传递进来的String进行HTML编码，防止XSS攻击
	 * 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
//			@Override
//			public String getAsText() {
//				Object value = getValue();
//				return value != null ? DateUtils.formatDateTime((Date)value) : "";
//			}
		});
	}

	/**
	 * @Author：谭冬梅
	 * @param： * @param yuanObject
	 * @param updateObject
	 * @Description：更新时只更新修改的对象属性
	 * @Date：9:54 2017/5/12
	 * @Return：
	 * @Exception：
	 */
	public Object returnForUpdateObject(Object yuanObject,Object updateObject)  {



			for (Field field : updateObject.getClass().getDeclaredFields()){
                field.setAccessible(true); // 设置些属性是可以访问的
                //  String type = field.getType().toString();// 得到此属性的类型
                String key = field.getName();// key:得到属性名
                Object value = null;// 得到此属性的值
				try {
					value = field.get(updateObject);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}

				if(value!=null){
                    //       System.out.println(key+"--key-------value--"+value);
                    for (Field yfield : yuanObject.getClass().getDeclaredFields()){
						try {
							yfield.setAccessible(true); // 设置些属性是可以访问的
							if (yfield.getName().equals(key) )
                            {
                                yfield.set(yuanObject,value);
                            }
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
                }
            }

		return yuanObject;
	}
	/**
	 * @Author：谭冬梅
	 * @param： * @param e
	 * @Description：获取错误信息返回至前端
	 * @Date：17:36 2017/5/23
	 * @Return：
	 * @Exception：
	 */
	public String getErrorMsg(Exception e){
		String msg = "系统出现问题，请联系管理员！错误内容如下：<br>";
		msg += e.getMessage();
		return msg;
	}
}
