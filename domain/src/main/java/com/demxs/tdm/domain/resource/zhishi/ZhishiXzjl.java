package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 知识下载记录Entity
 * @author 詹小梅
 * @version 2017-07-11
 */
public class ZhishiXzjl extends DataEntity<ZhishiXzjl> {
	
	private static final long serialVersionUID = 1L;
	private String zhishiid;		// 知识ID
	private String xiazair;		// 下载人
	private String xiazairid;		// 下载人ID
	private String xiazaisj;		// 下载时间
	private String xiazaiip;		// 下载IP
	
	public ZhishiXzjl() {
		super();
	}

	public ZhishiXzjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="知识ID长度必须介于 0 和 200 之间")
	public String getZhishiid() {
		return zhishiid;
	}

	public void setZhishiid(String zhishiid) {
		this.zhishiid = zhishiid;
	}
	
	@Length(min=0, max=200, message="下载人长度必须介于 0 和 200 之间")
	public String getXiazair() {
		return xiazair;
	}

	public void setXiazair(String xiazair) {
		this.xiazair = xiazair;
	}
	
	@Length(min=0, max=200, message="下载人ID长度必须介于 0 和 200 之间")
	public String getXiazairid() {
		return xiazairid;
	}

	public void setXiazairid(String xiazairid) {
		this.xiazairid = xiazairid;
	}
	
	@Length(min=0, max=200, message="下载时间长度必须介于 0 和 200 之间")
	public String getXiazaisj() {
		return xiazaisj;
	}

	public void setXiazaisj(String xiazaisj) {
		this.xiazaisj = xiazaisj;
	}
	
	@Length(min=0, max=200, message="下载IP长度必须介于 0 和 200 之间")
	public String getXiazaiip() {
		return xiazaiip;
	}

	public void setXiazaiip(String xiazaiip) {
		this.xiazaiip = xiazaiip;
	}
	
}