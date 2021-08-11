package com.demxs.tdm.domain.resource.zhishi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 知识查看Entity
 * @author 詹小梅
 * @version 2017-07-11
 */
public class ZhishiCk extends DataEntity<ZhishiCk> {
	
	private static final long serialVersionUID = 1L;
	private String zhishiid;		// 知识主键
	private String shenqingr;		// 申请人
	private String shenqingrid;		// 申请人id
	private String shenqingly;		// 申请理由
	private String shenqingckqx;		// 申请查看期限(1天、1周、1个月、3个月、6个月、1年、永久)
	private String shenqingsj;		// 申请时间
	private String shenpir;		// 审批人
	private String shenpirid;		// 审批人ID
	private String shenpiyj;		// 审批意见
	private String shenpijg;		// 审批结果
	private String shenpisj;		// 审批时间
	
	public ZhishiCk() {
		super();
	}

	public ZhishiCk(String id){
		super(id);
	}

	@Length(min=0, max=200, message="知识主键长度必须介于 0 和 200 之间")
	public String getZhishiid() {
		return zhishiid;
	}

	public void setZhishiid(String zhishiid) {
		this.zhishiid = zhishiid;
	}
	
	@Length(min=0, max=200, message="申请人长度必须介于 0 和 200 之间")
	public String getShenqingr() {
		return shenqingr;
	}

	public void setShenqingr(String shenqingr) {
		this.shenqingr = shenqingr;
	}
	
	@Length(min=0, max=200, message="申请人id长度必须介于 0 和 200 之间")
	public String getShenqingrid() {
		return shenqingrid;
	}

	public void setShenqingrid(String shenqingrid) {
		this.shenqingrid = shenqingrid;
	}
	
	@Length(min=0, max=2000, message="申请理由长度必须介于 0 和 2000 之间")
	public String getShenqingly() {
		return shenqingly;
	}

	public void setShenqingly(String shenqingly) {
		this.shenqingly = shenqingly;
	}
	
	@Length(min=0, max=200, message="申请查看期限(1天、1周、1个月、3个月、6个月、1年、永久)长度必须介于 0 和 200 之间")
	public String getShenqingckqx() {
		return shenqingckqx;
	}

	public void setShenqingckqx(String shenqingckqx) {
		this.shenqingckqx = shenqingckqx;
	}
	
	@Length(min=0, max=200, message="申请时间长度必须介于 0 和 200 之间")
	public String getShenqingsj() {
		return shenqingsj;
	}

	public void setShenqingsj(String shenqingsj) {
		this.shenqingsj = shenqingsj;
	}
	
	@Length(min=0, max=200, message="审批人长度必须介于 0 和 200 之间")
	public String getShenpir() {
		return shenpir;
	}

	public void setShenpir(String shenpir) {
		this.shenpir = shenpir;
	}
	
	@Length(min=0, max=200, message="审批人ID长度必须介于 0 和 200 之间")
	public String getShenpirid() {
		return shenpirid;
	}

	public void setShenpirid(String shenpirid) {
		this.shenpirid = shenpirid;
	}
	
	@Length(min=0, max=200, message="审批意见长度必须介于 0 和 200 之间")
	public String getShenpiyj() {
		return shenpiyj;
	}

	public void setShenpiyj(String shenpiyj) {
		this.shenpiyj = shenpiyj;
	}
	
	@Length(min=0, max=200, message="审批结果长度必须介于 0 和 200 之间")
	public String getShenpijg() {
		return shenpijg;
	}

	public void setShenpijg(String shenpijg) {
		this.shenpijg = shenpijg;
	}
	
	@Length(min=0, max=200, message="审批时间长度必须介于 0 和 200 之间")
	public String getShenpisj() {
		return shenpisj;
	}

	public void setShenpisj(String shenpisj) {
		this.shenpisj = shenpisj;
	}
	
}