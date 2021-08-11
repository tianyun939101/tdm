package com.demxs.tdm.domain.resource.renyuan;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 被培训人员与培训记录关系Entity
 * @author 詹小梅
 * @version 2017-06-20
 */
public class RenyuanPxjlgx extends DataEntity<RenyuanPxjlgx> {
	
	private static final long serialVersionUID = 1L;
	private String beipeixrid;		// 被培训人ID
	private String peixuncj;		// 培训成绩
	private String peixunjlzj;		// 培训记录主键
	private String userName;		//被培训人员
	private String no;			//被培训人工号
	private String peixuntm;		// 培训题目
	private String peixunls;		// 培训老师
	private String peixunlsid;		// 培训老师ID
	private String peixunrq;		// 培训日期
	private String peixundd;		// 培训地点
	private String peixunfs;		// 培训方式
	private String kaohexs;		    // 考核形式
	private String minPeixuncj;		//成绩
	private String maxPeixuncj;		//成绩
	private String beipeixr;		//被培训人名称
	private String dsf;


	
	public RenyuanPxjlgx() {
		super();
	}

	public RenyuanPxjlgx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="被培训人ID长度必须介于 0 和 200 之间")
	public String getBeipeixrid() {
		return beipeixrid;
	}

	public void setBeipeixrid(String beipeixrid) {
		this.beipeixrid = beipeixrid;
	}
	
	@Length(min=0, max=200, message="培训成绩长度必须介于 0 和 200 之间")
	public String getPeixuncj() {
		return peixuncj;
	}

	public void setPeixuncj(String peixuncj) {
		this.peixuncj = peixuncj;
	}
	
	@Length(min=0, max=200, message="培训记录主键长度必须介于 0 和 200 之间")
	public String getPeixunjlzj() {
		return peixunjlzj;
	}

	public void setPeixunjlzj(String peixunjlzj) {
		this.peixunjlzj = peixunjlzj;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getPeixuntm() {
		return peixuntm;
	}

	public void setPeixuntm(String peixuntm) {
		this.peixuntm = peixuntm;
	}

	public String getPeixunls() {
		return peixunls;
	}

	public void setPeixunls(String peixunls) {
		this.peixunls = peixunls;
	}

	public String getPeixunlsid() {
		return peixunlsid;
	}

	public void setPeixunlsid(String peixunlsid) {
		this.peixunlsid = peixunlsid;
	}

	public String getPeixunrq() {
		return peixunrq;
	}

	public void setPeixunrq(String peixunrq) {
		this.peixunrq = peixunrq;
	}

	public String getPeixundd() {
		return peixundd;
	}

	public void setPeixundd(String peixundd) {
		this.peixundd = peixundd;
	}

	public String getPeixunfs() {
		return peixunfs;
	}

	public void setPeixunfs(String peixunfs) {
		this.peixunfs = peixunfs;
	}

	public String getKaohexs() {
		return kaohexs;
	}

	public void setKaohexs(String kaohexs) {
		this.kaohexs = kaohexs;
	}

	public String getMinPeixuncj() {
		return minPeixuncj;
	}

	public void setMinPeixuncj(String minPeixuncj) {
		this.minPeixuncj = minPeixuncj;
	}

	public String getMaxPeixuncj() {
		return maxPeixuncj;
	}

	public void setMaxPeixuncj(String maxPeixuncj) {
		this.maxPeixuncj = maxPeixuncj;
	}

	public String getBeipeixr() {
		return beipeixr;
	}

	public void setBeipeixr(String beipeixr) {
		this.beipeixr = beipeixr;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}