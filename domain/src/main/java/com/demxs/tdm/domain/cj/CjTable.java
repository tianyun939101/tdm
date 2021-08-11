package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集信息表Entity
 * @author wbq
 * @version 2017-08-11
 */
public class CjTable extends DataEntity<CjTable> {
	
	private static final long serialVersionUID = 1L;
	private String cnname;		// 中文名称
	private String enname;		// 英文名称
	private String biaolx;		// 表类型
	private String shifouzb;//是否主表
	private String zibiaoid;//子表ID
	private String zibiaomc;//子表名称
	private String shiyangbszd;//试样标识字段

	
	public CjTable() {
		super();
	}

	public CjTable(String id){
		super(id);
	}

	@Length(min=1, max=100, message="中文名称长度必须介于 1 和 100 之间")
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	@Length(min=1, max=100, message="英文名称长度必须介于 1 和 100 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	@Length(min=1, max=100, message="表类型长度必须介于 1 和 100 之间")
	public String getBiaolx() {
		return biaolx;
	}

	public void setBiaolx(String biaolx) {
		this.biaolx = biaolx;
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

	public String getShiyangbszd() {
		return shiyangbszd;
	}

	public void setShiyangbszd(String shiyangbszd) {
		this.shiyangbszd = shiyangbszd;
	}
}