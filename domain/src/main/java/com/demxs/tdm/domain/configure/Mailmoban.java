package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 邮件模板Entity
 * @author wbq
 * @version 2017-08-09
 */
public class Mailmoban extends DataEntity<Mailmoban> {
	
	private static final long serialVersionUID = 1L;
	private String mobanmc;		// 模板名称
	private String mobanbm;		// 模板编码
	private String mobandz;		// 模板地址
	private String ismoren;		// 是否是默认
	private String mobannr;       //模板内容

	public String getMobannr() {
		return mobannr;
	}

	public void setMobannr(String mobannr) {
		this.mobannr = mobannr;
	}

	public Mailmoban() {
		super();
	}

	public Mailmoban(String id){
		super(id);
	}

	@Length(min=1, max=100, message="模板名称长度必须介于 1 和 100 之间")
	public String getMobanmc() {
		return mobanmc;
	}

	public void setMobanmc(String mobanmc) {
		this.mobanmc = mobanmc;
	}
	
	@Length(min=0, max=100, message="模板编码长度必须介于 0 和 100 之间")
	public String getMobanbm() {
		return mobanbm;
	}

	public void setMobanbm(String mobanbm) {
		this.mobanbm = mobanbm;
	}
	
	@Length(min=0, max=100, message="模板地址长度必须介于 0 和 100 之间")
	public String getMobandz() {
		return mobandz;
	}

	public void setMobandz(String mobandz) {
		this.mobandz = mobandz;
	}
	
	@Length(min=1, max=1, message="是否是默认长度必须介于 1 和 1 之间")
	public String getIsmoren() {
		return ismoren;
	}

	public void setIsmoren(String ismoren) {
		this.ismoren = ismoren;
	}
	
}