package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集字段信息Entity
 * @author wbq
 * @version 2017-08-11
 */
public class CjZiduan extends DataEntity<CjZiduan> {
	
	private static final long serialVersionUID = 1L;
	private String cjtableid;		// 采集表ID
	private String cnname;		// 中文名称
	private String enname;		// 英文名称
	private String ziduanlx;		// 字段类型
	private Long ziduancd;		// 字段长度
	private String tablecnname;
	private Integer sort;
	private String iskey;
	private String shifousybs;//c

	public CjZiduan() {
		super();
	}

	public CjZiduan(String id){
		super(id);
	}

	@Length(min=1, max=64, message="采集表ID长度必须介于 1 和 64 之间")
	public String getCjtableid() {
		return cjtableid;
	}

	public void setCjtableid(String cjtableid) {
		this.cjtableid = cjtableid;
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
	
	@Length(min=1, max=100, message="字段类型长度必须介于 1 和 100 之间")
	public String getZiduanlx() {
		return ziduanlx;
	}

	public void setZiduanlx(String ziduanlx) {
		this.ziduanlx = ziduanlx;
	}
	
	public Long getZiduancd() {
		return ziduancd;
	}

	public void setZiduancd(Long ziduancd) {
		this.ziduancd = ziduancd;
	}


	public String getTablecnname() {
		return tablecnname;
	}

	public void setTablecnname(String tablecnname) {
		this.tablecnname = tablecnname;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public String getIskey() {
		return iskey;
	}

	public void setIskey(String iskey) {
		this.iskey = iskey;
	}

	public String getShifousybs() {
		return shifousybs;
	}

	public void setShifousybs(String shifousybs) {
		this.shifousybs = shifousybs;
	}
}