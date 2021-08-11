package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 样品拆样工程师Entity
 * @author 詹小梅
 * @version 2017-07-19
 */
public class YangpinCygcs extends DataEntity<YangpinCygcs> {
	
	private static final long serialVersionUID = 1L;
	private String chaiyangzj;		// 样品主键
	private String gongchengsid;	// 工程师主键
	private String gongchengsmc;	//工程师名称
	public YangpinCygcs() {
		super();
	}

	public YangpinCygcs(String id){
		super(id);
	}

	public String getChaiyangzj() {
		return chaiyangzj;
	}

	public void setChaiyangzj(String chaiyangzj) {
		this.chaiyangzj = chaiyangzj;
	}

	public String getGongchengsid() {
		return gongchengsid;
	}

	public void setGongchengsid(String gongchengsid) {
		this.gongchengsid = gongchengsid;
	}

	public String getGongchengsmc() {
		return gongchengsmc;
	}

	public void setGongchengsmc(String gongchengsmc) {
		this.gongchengsmc = gongchengsmc;
	}
}