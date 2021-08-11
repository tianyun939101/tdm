package com.demxs.tdm.domain.configure;


import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 原始记录模板入库信息配置Entity
 * @author 谭冬梅
 * @version 2017-11-30
 */
public class YuanshijlmbRk extends DataEntity<YuanshijlmbRk> {
	
	private static final long serialVersionUID = 1L;
	private String mbid;		// 原始记录模板id
	private String cnname;		// 字段中文名
	private String ziduanlx;		// 字段类型 表单或列表
	private String enname;		// 字段英文字段名
	private String biaom;		// 字段所属表名

	private boolean sfsz;//是否设置 true 是

	private boolean sfhb ;//是否合并显示

	public boolean isSfhb() {
		return sfhb;
	}

	public void setSfhb(boolean sfhb) {
		this.sfhb = sfhb;
	}

	public boolean isSfsz() {
		return sfsz;
	}

	public void setSfsz(boolean sfsz) {
		this.sfsz = sfsz;
	}
	
	public YuanshijlmbRk() {
		super();
	}

	public YuanshijlmbRk(String id){
		super(id);
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}
	
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	public String getZiduanlx() {
		return ziduanlx;
	}

	public void setZiduanlx(String ziduanlx) {
		this.ziduanlx = ziduanlx;
	}
	
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	public String getBiaom() {
		return biaom;
	}

	public void setBiaom(String biaom) {
		this.biaom = biaom;
	}
	
}