package com.demxs.tdm.domain.resource.huanjing;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 环境数据，设置展示的房间Entity
 * @author zhangdengcai
 * @version 2017-10-09
 */
public class HuanjingZsfj extends DataEntity<HuanjingZsfj> {
	
	private static final long serialVersionUID = 1L;
	private String fangjianid;		// 房间ID
	private String fangjianmc;		// 房间名称
	private String shifouzs;		//是否展示
	
	public HuanjingZsfj() {
		super();
	}

	public HuanjingZsfj(String id){
		super(id);
	}

	@Length(min=0, max=100, message="房间ID长度必须介于 0 和 100 之间")
	public String getFangjianid() {
		return fangjianid;
	}

	public void setFangjianid(String fangjianid) {
		this.fangjianid = fangjianid;
	}
	
	@Length(min=0, max=200, message="房间名称长度必须介于 0 和 200 之间")
	public String getFangjianmc() {
		return fangjianmc;
	}

	public void setFangjianmc(String fangjianmc) {
		this.fangjianmc = fangjianmc;
	}

	public String getShifouzs() {
		return shifouzs;
	}

	public void setShifouzs(String shifouzs) {
		this.shifouzs = shifouzs;
	}
}