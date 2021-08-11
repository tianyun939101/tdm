package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 知识评论Entity
 * @author 詹小梅
 * @version 2017-07-11
 */
public class ZhishiPljl extends DataEntity<ZhishiPljl> {
	
	private static final long serialVersionUID = 1L;
	private String zhishiid;		// 知识ID
	private String pinglunrid;		// 评论人ID
	private String pinglunr;		// 评论人
	private String pinglunsj;		// 评论时间
	private String pinglunnr;		// 评论内容
	private Long pingfen;		// 评分
	
	public ZhishiPljl() {
		super();
	}

	public ZhishiPljl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="知识ID长度必须介于 0 和 200 之间")
	public String getZhishiid() {
		return zhishiid;
	}

	public void setZhishiid(String zhishiid) {
		this.zhishiid = zhishiid;
	}
	
	@Length(min=0, max=200, message="评论人ID长度必须介于 0 和 200 之间")
	public String getPinglunrid() {
		return pinglunrid;
	}

	public void setPinglunrid(String pinglunrid) {
		this.pinglunrid = pinglunrid;
	}
	
	@Length(min=0, max=200, message="评论人长度必须介于 0 和 200 之间")
	public String getPinglunr() {
		return pinglunr;
	}

	public void setPinglunr(String pinglunr) {
		this.pinglunr = pinglunr;
	}
	
	@Length(min=0, max=200, message="评论时间长度必须介于 0 和 200 之间")
	public String getPinglunsj() {
		return pinglunsj;
	}

	public void setPinglunsj(String pinglunsj) {
		this.pinglunsj = pinglunsj;
	}
	
	@Length(min=0, max=4000, message="评论内容长度必须介于 0 和 4000 之间")
	public String getPinglunnr() {
		return pinglunnr;
	}

	public void setPinglunnr(String pinglunnr) {
		this.pinglunnr = pinglunnr;
	}
	
	@Length(min=0, max=200, message="评分长度必须介于 0 和 200 之间")
	public Long getPingfen() {
		return pingfen;
	}

	public void setPingfen(Long pingfen) {
		this.pingfen = pingfen;
	}
	
}