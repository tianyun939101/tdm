package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 耗材出库返库Entity
 * @author zhangdengcai
 * @version 2017-07-17
 */
public class HaocaiCkfkd extends DataEntity<HaocaiCkfkd> {
	
	private static final long serialVersionUID = 1L;
	private String haocaizj;		// 耗材主键
	private String haocailx;		// 耗材流向：出库；返库
	private String lingqughsl;		// 领取/归还数量
	private String lingqughrid;		// 领取人/归还人ID
	private String lingqughr;		// 领取人/归还人
	private String lingqughssbmid;		// 领取/归还所属部门ID
	private String lingqughssbm;		// 领取/归还所属部门
	private String chukurq;		// 出库日期/返库日期
	private String dengjirid;		// 登记人ID
	private String dengjir;		// 登记人
	private String shujuzt;		//数据状态

	public String getCunfangwzId() {
		return cunfangwzId;
	}

	public void setCunfangwzId(String cunfangwzId) {
		this.cunfangwzId = cunfangwzId;
	}

	public String getCunfangwzmc() {
		return cunfangwzmc;
	}

	public void setCunfangwzmc(String cunfangwzmc) {
		this.cunfangwzmc = cunfangwzmc;
	}

	private String cunfangwzId;		// 存放位置ID
	private String cunfangwzmc;		// 存放位置名称

	private List<HaocaiCkfkdmx> haocai = new ArrayList<HaocaiCkfkdmx>();

	private Boolean submit;		//是否提交
	
	public HaocaiCkfkd() {
		super();
	}

	public HaocaiCkfkd(String id){
		super(id);
	}

	@Length(min=0, max=200, message="耗材主键长度必须介于 0 和 200 之间")
	public String getHaocaizj() {
		return haocaizj;
	}

	public void setHaocaizj(String haocaizj) {
		this.haocaizj = haocaizj;
	}
	
	@Length(min=0, max=200, message="耗材流向：出库；返库长度必须介于 0 和 200 之间")
	public String getHaocailx() {
		return haocailx;
	}

	public void setHaocailx(String haocailx) {
		this.haocailx = haocailx;
	}
	
	@Length(min=0, max=200, message="领取/归还数量长度必须介于 0 和 200 之间")
	public String getLingqughsl() {
		return lingqughsl;
	}

	public void setLingqughsl(String lingqughsl) {
		this.lingqughsl = lingqughsl;
	}
	
	@Length(min=0, max=200, message="领取人/归还人ID长度必须介于 0 和 200 之间")
	public String getLingqughrid() {
		return lingqughrid;
	}

	public void setLingqughrid(String lingqughrid) {
		this.lingqughrid = lingqughrid;
	}
	
	@Length(min=0, max=200, message="领取人/归还人长度必须介于 0 和 200 之间")
	public String getLingqughr() {
		return lingqughr;
	}

	public void setLingqughr(String lingqughr) {
		this.lingqughr = lingqughr;
	}
	
	@Length(min=0, max=200, message="领取/归还所属部门ID长度必须介于 0 和 200 之间")
	public String getLingqughssbmid() {
		return lingqughssbmid;
	}

	public void setLingqughssbmid(String lingqughssbmid) {
		this.lingqughssbmid = lingqughssbmid;
	}
	
	@Length(min=0, max=200, message="领取/归还所属部门长度必须介于 0 和 200 之间")
	public String getLingqughssbm() {
		return lingqughssbm;
	}

	public void setLingqughssbm(String lingqughssbm) {
		this.lingqughssbm = lingqughssbm;
	}
	
	@Length(min=0, max=200, message="出库日期/返库日期长度必须介于 0 和 200 之间")
	public String getChukurq() {
		return chukurq;
	}

	public void setChukurq(String chukurq) {
		this.chukurq = chukurq;
	}
	
	@Length(min=0, max=200, message="登记人ID长度必须介于 0 和 200 之间")
	public String getDengjirid() {
		return dengjirid;
	}

	public void setDengjirid(String dengjirid) {
		this.dengjirid = dengjirid;
	}
	
	@Length(min=0, max=200, message="登记人长度必须介于 0 和 200 之间")
	public String getDengjir() {
		return dengjir;
	}

	public void setDengjir(String dengjir) {
		this.dengjir = dengjir;
	}

	public List<HaocaiCkfkdmx> getHaocai() {
		return haocai;
	}

	public void setHaocai(List<HaocaiCkfkdmx> haocai) {
		this.haocai = haocai;
	}

	public String getShujuzt() {
		return shujuzt;
	}

	public void setShujuzt(String shujuzt) {
		this.shujuzt = shujuzt;
	}

	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}
}