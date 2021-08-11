package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集规则项Entity
 * @author wbq
 * @version 2017-08-11
 */
public class Guizeitem extends DataEntity<Guizeitem> {
	
	private static final long serialVersionUID = 1L;
	private String cjguizeid;		// 规则ID
	private String sheetname;		// sheet名称
	private String address;		// 地址
	private Long rowindex;		// 行
	private Long columnindex;		// 列
	private String cjziduanid;		// 采集字段ID
	private String cjtableid;
	private String fieldcnname;
	private String fieldenname;

	public Guizeitem() {
		super();
	}

	public String getFieldcnname() {
		return fieldcnname;
	}

	public void setFieldcnname(String fieldcnname) {
		this.fieldcnname = fieldcnname;
	}

	public String getFieldenname() {
		return fieldenname;
	}

	public void setFieldenname(String fieldenname) {
		this.fieldenname = fieldenname;
	}

	public Guizeitem(String id){
		super(id);
	}

	@Length(min=1, max=64, message="规则ID长度必须介于 1 和 64 之间")
	public String getCjguizeid() {
		return cjguizeid;
	}

	public void setCjguizeid(String cjguizeid) {
		this.cjguizeid = cjguizeid;
	}

	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getRowindex() {
		return rowindex;
	}

	public void setRowindex(Long rowindex) {
		this.rowindex = rowindex;
	}

	public Long getColumnindex() {
		return columnindex;
	}

	public void setColumnindex(Long columnindex) {
		this.columnindex = columnindex;
	}
	
	@Length(min=1, max=64, message="采集字段ID长度必须介于 1 和 64 之间")
	public String getCjziduanid() {
		return cjziduanid;
	}

	public void setCjziduanid(String cjziduanid) {
		this.cjziduanid = cjziduanid;
	}

	public String getCjtableid() {
		return cjtableid;
	}

	public void setCjtableid(String cjtableid) {
		this.cjtableid = cjtableid;
	}

}