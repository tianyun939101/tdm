package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 邮件配置Entity
 * @author wbq
 * @version 2017-08-09
 */
public class Mailmaster extends DataEntity<Mailmaster> {
	
	private static final long serialVersionUID = 1L;
	private String peizhimc;		// 配置名称
	private String mailfrom;		// 发送地址
	private String mailhost;		// 服务地址
	private String username;		// 用户名
	private String password;		// 密码
	private String smtpauth;		// 是否需要验证
	private String smtptimeout;		// 超时时间
	private String isqiyong;		// 是否启用
	private String mailperson;   //发信人
	private String mailport;
	
	public Mailmaster() {
		super();
	}

	public Mailmaster(String id){
		super(id);
	}

	@Length(min=0, max=100, message="配置名称长度必须介于 0 和 100 之间")
	public String getPeizhimc() {
		return peizhimc;
	}

	public void setPeizhimc(String peizhimc) {
		this.peizhimc = peizhimc;
	}
	
	@Length(min=1, max=100, message="发送地址长度必须介于 1 和 100 之间")
	public String getMailfrom() {
		return mailfrom;
	}

	public void setMailfrom(String mailfrom) {
		this.mailfrom = mailfrom;
	}
	
	@Length(min=1, max=100, message="服务地址长度必须介于 1 和 100 之间")
	public String getMailhost() {
		return mailhost;
	}

	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}
	
	@Length(min=1, max=100, message="用户名长度必须介于 1 和 100 之间")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=1, max=1, message="是否需要验证长度必须介于 1 和 1 之间")
	public String getSmtpauth() {
		return smtpauth;
	}

	public void setSmtpauth(String smtpauth) {
		this.smtpauth = smtpauth;
	}
	
	@Length(min=1, max=100, message="超时时间长度必须介于 1 和 100 之间")
	public String getSmtptimeout() {
		return smtptimeout;
	}

	public void setSmtptimeout(String smtptimeout) {
		this.smtptimeout = smtptimeout;
	}
	
	@Length(min=1, max=1, message="是否启用长度必须介于 1 和 1 之间")
	public String getIsqiyong() {
		return isqiyong;
	}

	public void setIsqiyong(String isqiyong) {
		this.isqiyong = isqiyong;
	}

	public String getMailperson() {
		return mailperson;
	}

	public void setMailperson(String mailperson) {
		this.mailperson = mailperson;
	}

	public String getMailport() {
		return mailport;
	}

	public void setMailport(String mailport) {
		this.mailport = mailport;
	}


}