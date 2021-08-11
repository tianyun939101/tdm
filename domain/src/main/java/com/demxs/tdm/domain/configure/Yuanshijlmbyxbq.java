package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 原始记录已选标签Entity
 * @author wbq
 * @version 2017-07-24
 */
public class Yuanshijlmbyxbq extends DataEntity<Yuanshijlmbyxbq> {
	
	private static final long serialVersionUID = 1L;
	private String mbid;		// 模板ID
	private String bqid;		// 标签ID
	private String caijilx;     //采集类型 tool 工具, form 表单, list 列表
	private String caijizdid;   //采集字段id
	private String rkzdid; // 入库字段id

	public String getRkzdid() {
		return rkzdid;
	}

	public void setRkzdid(String rkzdid) {
		this.rkzdid = rkzdid;
	}
	private String rkenname;//入库字段英文名
	private String rkcnname;//入库字段中文名

	public String getRkenname() {
		return rkenname;
	}

	public void setRkenname(String rkenname) {
		this.rkenname = rkenname;
	}

	public String getRkcnname() {
		return rkcnname;
	}

	public void setRkcnname(String rkcnname) {
		this.rkcnname = rkcnname;
	}

	private String cjenname;//采集字段英文名
	private String cjcnname;//采集字段中文名

	private String sheetname;		// sheet名称
	private Long rowindex;		// 行索引
	private Long columnindex;		// 列索引
	private String enname;
	private String cnname;
	private String address;

	//关联标签查询
	private String biaoqianlx;//标签类型 from or tool or list
	private String ishuixie;//是否回写
	private String biaoqiansx; //标签属性


	private  String cellvalue;//值
	private  String biaom ; // 入库表名

	public String getBiaom() {
		return biaom;
	}

	public void setBiaom(String biaom) {
		this.biaom = biaom;
	}

	public String getCellvalue() {
		return cellvalue;
	}

	public void setCellvalue(String cellvalue) {
		this.cellvalue = cellvalue;
	}

	public String getBiaoqiansx() {
		return biaoqiansx;
	}

	public void setBiaoqiansx(String biaoqiansx) {
		this.biaoqiansx = biaoqiansx;
	}

	public String getCjenname() {
		return cjenname;
	}

	public void setCjenname(String cjenname) {
		this.cjenname = cjenname;
	}

	public String getCjcnname() {
		return cjcnname;
	}

	public void setCjcnname(String cjcnname) {
		this.cjcnname = cjcnname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}

	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}

	public Yuanshijlmbyxbq() {
		super();
	}

	public Yuanshijlmbyxbq(String id){
		super(id);
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public String getBqid() {
		return bqid;
	}

	public void setBqid(String bqid) {
		this.bqid = bqid;
	}

	public String getSheetname() {
		return sheetname;
	}

	public void setSheetname(String sheetname) {
		this.sheetname = sheetname;
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


	public String getBiaoqianlx() {
		return biaoqianlx;
	}

	public void setBiaoqianlx(String biaoqianlx) {
		this.biaoqianlx = biaoqianlx;
	}

	public String getIshuixie() {
		return ishuixie;
	}

	public void setIshuixie(String ishuixie) {
		this.ishuixie = ishuixie;
	}

	public String getCaijilx() {
		return caijilx;
	}

	public void setCaijilx(String caijilx) {
		this.caijilx = caijilx;
	}

	public String getCaijizdid() {
		return caijizdid;
	}

	public void setCaijizdid(String caijizdid) {
		this.caijizdid = caijizdid;
	}
}