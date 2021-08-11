package com.demxs.tdm.domain.cj;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 采集索引Entity
 * @author 郭金龙
 * @version 2017-10-12
 */
public class CjIndex extends DataEntity<CjIndex> {
	
	private static final long serialVersionUID = 1L;
	private String logid;		// logid
	private String guizeid;		// 规则ID
	private String renwuid;		// 任务ID
	private String yangpinid;		// 样品ID
	private String shifoucg;		// 是否成功
	private String miaoshu;		// 描述
	private String caijisj;		// 采集时间
	
	public CjIndex() {
		super();
	}

	public CjIndex(String id){
		super(id);
	}

	@Length(min=0, max=200, message="logid长度必须介于 0 和 200 之间")
	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}
	
	@Length(min=0, max=200, message="规则ID长度必须介于 0 和 200 之间")
	public String getGuizeid() {
		return guizeid;
	}

	public void setGuizeid(String guizeid) {
		this.guizeid = guizeid;
	}
	
	@Length(min=0, max=200, message="任务ID长度必须介于 0 和 200 之间")
	public String getRenwuid() {
		return renwuid;
	}

	public void setRenwuid(String renwuid) {
		this.renwuid = renwuid;
	}
	
	@Length(min=0, max=200, message="样品ID长度必须介于 0 和 200 之间")
	public String getYangpinid() {
		return yangpinid;
	}

	public void setYangpinid(String yangpinid) {
		this.yangpinid = yangpinid;
	}
	
	@Length(min=0, max=10, message="是否成功长度必须介于 0 和 10 之间")
	public String getShifoucg() {
		return shifoucg;
	}

	public void setShifoucg(String shifoucg) {
		this.shifoucg = shifoucg;
	}
	
	@Length(min=0, max=200, message="描述长度必须介于 0 和 200 之间")
	public String getMiaoshu() {
		return miaoshu;
	}

	public void setMiaoshu(String miaoshu) {
		this.miaoshu = miaoshu;
	}
	
	@Length(min=0, max=200, message="采集时间长度必须介于 0 和 200 之间")
	public String getCaijisj() {
		return caijisj;
	}

	public void setCaijisj(String caijisj) {
		this.caijisj = caijisj;
	}
	
}