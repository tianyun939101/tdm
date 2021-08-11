package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 报告模板方法关系Entity
 * @author wbq
 * @version 2017-06-20
 */
public class Baogaombff extends DataEntity<Baogaombff> {
	
	private static final long serialVersionUID = 1L;
	private String baogaombid;		// 报告模板ID
	private String fangfaid;		// 方法ID


	private String baogaomc;
	private String fangfamc;
	
	public Baogaombff() {
		super();
	}

	public Baogaombff(String id){
		super(id);
	}

	@Length(min=0, max=64, message="报告模板ID长度必须介于 0 和 64 之间")
	public String getBaogaombid() {
		return baogaombid;
	}

	public void setBaogaombid(String baogaombid) {
		this.baogaombid = baogaombid;
	}
	
	@Length(min=0, max=64, message="方法ID长度必须介于 0 和 64 之间")
	public String getFangfaid() {
		return fangfaid;
	}

	public void setFangfaid(String fangfaid) {
		this.fangfaid = fangfaid;
	}

	public String getBaogaomc() {
		return baogaomc;
	}

	public void setBaogaomc(String baogaomc) {
		this.baogaomc = baogaomc;
	}

	public String getFangfamc() {
		return fangfamc;
	}

	public void setFangfamc(String fangfamc) {
		this.fangfamc = fangfamc;
	}


}