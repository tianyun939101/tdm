package com.demxs.tdm.common.sys.security;

/**
 * 用户和密码（包含验证码）令牌类
 * @author ThinkGem
 * @version 2013-5-19
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	public static final Integer LTPA_TOKEN_TYPE = 2;

	private String captcha;
	private boolean mobileLogin;
	private Integer loginType;
	
	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password,
								 boolean rememberMe, String host, String captcha, boolean mobileLogin) {
		super(username, password, rememberMe, host);
		this.captcha = captcha;
		this.mobileLogin = mobileLogin;
	}

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}

	public boolean isMobileLogin() {
		return mobileLogin;
	}

	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}
}