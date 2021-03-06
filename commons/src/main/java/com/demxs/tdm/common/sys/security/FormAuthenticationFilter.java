package com.demxs.tdm.common.sys.security;

import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单验证（包含验证码）过滤类
 * @author ThinkGem
 * @version 2014-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;

	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}

	/**
	 * 获取登录用户名
	 */
	protected String getUsername(ServletRequest request, ServletResponse response) {
		String username = super.getUsername(request);
		if (StringUtils.isBlank(username)){
			username = StringUtils.toString(request.getAttribute(getUsernameParam()), StringUtils.EMPTY);
		}
		return username;
	}

	/**
	 * 获取登录密码
	 */
	@Override
	protected String getPassword(ServletRequest request) {
		String password = super.getPassword(request);
		if (StringUtils.isBlank(password)){
			password = StringUtils.toString(request.getAttribute(getPasswordParam()), StringUtils.EMPTY);
		}
		return password;
	}

	/**
	 * 获取记住我
	 */
	@Override
	protected boolean isRememberMe(ServletRequest request) {
		String isRememberMe = WebUtils.getCleanParam(request, getRememberMeParam());
		if (StringUtils.isBlank(isRememberMe)){
			isRememberMe = StringUtils.toString(request.getAttribute(getRememberMeParam()), StringUtils.EMPTY);
		}
		return StringUtils.toBoolean(isRememberMe);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}

	protected boolean isMobileLogin(ServletRequest request) {
		return WebUtils.isTrue(request, getMobileLoginParam());
	}

	public String getMessageParam() {
		return messageParam;
	}

	/**
	 * 登录成功之后跳转URL
	 */
	@Override
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}

	@Override
	protected void issueSuccessRedirect(ServletRequest request,
										ServletResponse response) throws Exception {
		if (com.demxs.tdm.common.utils.WebUtils.isAjax((HttpServletRequest)request)) {
//					WebUtils.toHttp(response).sendError(SC_UNAUTHORIZED);//401
			Map<String,Object> map = new HashMap();
			map.put("status", true);
			WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));
			return;
		}
//		Principal p = UserUtils.getPrincipal();
//		if (p != null && !p.isMobileLogin()){
		WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
//		}else{
//			super.issueSuccessRedirect(request, response);
//		}
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
									 AuthenticationException e, ServletRequest request, ServletResponse response) {

		String className = e.getClass().getName(), message = "";
		if (e instanceof IncorrectCredentialsException
				|| e instanceof UnknownAccountException){
			message = "用户或密码错误, 请重试.";
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
		if (com.demxs.tdm.common.utils.WebUtils.isAjax((HttpServletRequest)request)) {
//					WebUtils.toHttp(response).sendError(SC_UNAUTHORIZED);//401
			Map<String,Object> map = new HashMap();
			map.put("status", false);
			map.put("message", message);
			try {
				response.reset();
				response.setContentType("application/json; charset=utf-8");
				WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		request.setAttribute(getFailureKeyAttribute(), className);
		request.setAttribute(getMessageParam(), message);
		return true;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Subject subject = getSubject(request, response);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		Map map = new HashMap();
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				return executeLogin(request, response);
			} else {
				return true;
			}
		} else {
			if (subject.getPrincipal() == null) {
				if (com.demxs.tdm.common.utils.WebUtils.isAjax(httpRequest)) {
//					WebUtils.toHttp(response).sendError(SC_UNAUTHORIZED);//401
					map.put("status","401");
					WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//401
				} else {
					saveRequestAndRedirectToLogin(request, response);
				}
			} else {
				if (com.demxs.tdm.common.utils.WebUtils.isAjax(httpRequest)) {
					map.put("status","403");
					WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//403
//					WebUtils.toHttp(response).sendError(SC_FORBIDDEN);
				} else {
					String unauthorizedUrl = getSuccessUrl();
					if (StringUtils.isNotEmpty(unauthorizedUrl)) {
						WebUtils.issueRedirect(request, response, unauthorizedUrl);
					} else {
						map.put("status","401");
						WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//401
					}
					map.put("status","403");
//					WebUtils.toHttp(response).sendError(SC_FORBIDDEN);
					WebUtils.toHttp(response).getWriter().print(JsonMapper.toJsonString(map));//403
				}
			}
			return false;
		}
	}
}