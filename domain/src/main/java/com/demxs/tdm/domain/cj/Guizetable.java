package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集表格关系Entity
 * @author wbq
 * @version 2017-08-19
 */
public class Guizetable extends DataEntity<Guizetable> {
	
	private static final long serialVersionUID = 1L;
	private String guizeid;		// 规则ID
	private String tableid;		// 表格ID
	private String caijifs;		// 采集方式
	private String guizemc;
	private String tablecnname;
	private String tableenname;
	private String biaolx;
	private String shifouzb;//是否主表
	private String zibiaoid;//子表ID
	private String zibiaomc;//子表名称

	public String getBiaolx() {
		return biaolx;
	}

	public void setBiaolx(String biaolx) {
		this.biaolx = biaolx;
	}

	public Guizetable() {
		super();
	}

	public Guizetable(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规则ID长度必须介于 1 和 64 之间")
	public String getGuizeid() {
		return guizeid;
	}

	public void setGuizeid(String guizeid) {
		this.guizeid = guizeid;
	}
	
	@Length(min=1, max=64, message="表格ID长度必须介于 1 和 64 之间")
	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}
	
	@Length(min=1, max=100, message="采集方式长度必须介于 1 和 100 之间")
	public String getCaijifs() {
		return caijifs;
	}

	public void setCaijifs(String caijifs) {
		this.caijifs = caijifs;
	}

	public String getGuizemc() {
		return guizemc;
	}

	public void setGuizemc(String guizemc) {
		this.guizemc = guizemc;
	}

	public String getTablecnname() {
		return tablecnname;
	}

	public void setTablecnname(String tablecnname) {
		this.tablecnname = tablecnname;
	}

	public String getTableenname() {
		return tableenname;
	}

	public void setTableenname(String tableenname) {
		this.tableenname = tableenname;
	}

	public String getShifouzb() {
		return shifouzb;
	}

	public void setShifouzb(String shifouzb) {
		this.shifouzb = shifouzb;
	}

	public String getZibiaoid() {
		return zibiaoid;
	}

	public void setZibiaoid(String zibiaoid) {
		this.zibiaoid = zibiaoid;
	}

	public String getZibiaomc() {
		return zibiaomc;
	}

	public void setZibiaomc(String zibiaomc) {
		this.zibiaomc = zibiaomc;
	}
}