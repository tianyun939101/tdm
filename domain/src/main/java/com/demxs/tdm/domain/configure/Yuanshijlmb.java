package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.Map;

/**
 * 原始记录模板Entity
 * @author wbq
 * @version 2017-06-08
 */
public class Yuanshijlmb extends DataEntity<Yuanshijlmb> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 原始记录名称
	private String dizhi;		// 原始记录地址
	private String caijigzid;     // 采集规则id
	private String caijigzmc;     // 采集规则名称

	private String  shifoupzbg; //是否配置报告 1 是 ，0 否

	public String getShifoupzbg() {
		return shifoupzbg;
	}

	public void setShifoupzbg(String shifoupzbg) {
		this.shifoupzbg = shifoupzbg;
	}

	private String  renwuid;
	private String  mbid;
	private Map dataMap;
	private List<Map> dataList;

	public String getRenwuid() {
		return renwuid;
	}

	public void setRenwuid(String renwuid) {
		this.renwuid = renwuid;
	}

	public String getMbid() {
		return mbid;
	}

	public void setMbid(String mbid) {
		this.mbid = mbid;
	}

	public Map getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map dataMap) {
		this.dataMap = dataMap;
	}

	public List<Map> getDataList() {
		return dataList;
	}

	public void setDataList(List<Map> dataList) {
		this.dataList = dataList;
	}

	public Yuanshijlmb() {
		super();
	}

	public Yuanshijlmb(String id){
		super(id);
	}

	@Length(min=1, max=100, message="原始记录名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="原始记录地址长度必须介于 0 和 255 之间")
	public String getDizhi() {
		return dizhi;
	}

	public void setDizhi(String dizhi) {
		this.dizhi = dizhi;
	}

	public String getCaijigzid() {
		return caijigzid;
	}

	public void setCaijigzid(String caijigzid) {
		this.caijigzid = caijigzid;
	}

	public String getCaijigzmc() {
		return caijigzmc;
	}

	public void setCaijigzmc(String caijigzmc) {
		this.caijigzmc = caijigzmc;
	}


	private String jiluId;//页面传值

	public String getJiluId() {
		return jiluId;
	}

	public void setJiluId(String jiluId) {
		this.jiluId = jiluId;
	}
}