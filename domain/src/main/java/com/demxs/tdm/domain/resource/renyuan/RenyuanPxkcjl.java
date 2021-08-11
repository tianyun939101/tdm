package com.demxs.tdm.domain.resource.renyuan;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

/**
 * 人员课程培训记录Entity
 * @author 詹小梅
 * @version 2017-06-20
 */
public class RenyuanPxkcjl extends DataEntity<RenyuanPxkcjl> {
	
	private static final long serialVersionUID = 1L;
	private String peixundd;		// 培训地点
	private String peixunfs;		// 培训方式
	private String peixunbh;		// 培训编号
	private String peixuntm;		// 培训题目
	private String peixunmc;		// 培训名称
	private String peixunls;		// 培训老师
	private String peixunlsid;		// 培训老师ID
	private String peixunrq;		// 培训日期
	private String kaohexs;		// 考核形式
	private String peixunnr;		// 培训内容
	private Attachment[] peixunnrfjs;		// 培训内容附件
	private String peixunjg;		// 培训结果
	private String beipeixr;		// 被培训人
	private String beipeixrid;		// 被培训人ID
	private String beginDate;//培训开始日期
	private String endDate;//培训结束日期
	private RenyuanPxjlgx[] pxjlgx; //被培训人员与培训记录关系
	private User[] beipxr;//被培训人
	private String dsf;

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

	public User getDangqianR() {
		return dangqianR;
	}

	public void setDangqianR(User dangqianR) {
		this.dangqianR = dangqianR;
	}

	public String getCaozuoqx() {
		return caozuoqx;
	}

	public void setCaozuoqx(String caozuoqx) {
		this.caozuoqx = caozuoqx;
	}
	
	public RenyuanPxkcjl() {
		super();
	}

	public RenyuanPxkcjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="培训地点长度必须介于 0 和 200 之间")
	public String getPeixundd() {
		return peixundd;
	}

	public void setPeixundd(String peixundd) {
		this.peixundd = peixundd;
	}
	
	@Length(min=0, max=200, message="培训方式长度必须介于 0 和 200 之间")
	public String getPeixunfs() {
		return peixunfs;
	}

	public void setPeixunfs(String peixunfs) {
		this.peixunfs = peixunfs;
	}
	
	@Length(min=0, max=200, message="培训编号长度必须介于 0 和 200 之间")
	public String getPeixunbh() {
		return peixunbh;
	}

	public void setPeixunbh(String peixunbh) {
		this.peixunbh = peixunbh;
	}
	
	@Length(min=0, max=200, message="培训题目长度必须介于 0 和 200 之间")
	public String getPeixuntm() {
		return peixuntm;
	}

	public void setPeixuntm(String peixuntm) {
		this.peixuntm = peixuntm;
	}
	
	@Length(min=0, max=200, message="培训名称长度必须介于 0 和 200 之间")
	public String getPeixunmc() {
		return peixunmc;
	}

	public void setPeixunmc(String peixunmc) {
		this.peixunmc = peixunmc;
	}
	
	@Length(min=0, max=200, message="培训老师长度必须介于 0 和 200 之间")
	public String getPeixunls() {
		return peixunls;
	}

	public void setPeixunls(String peixunls) {
		this.peixunls = peixunls;
	}
	
	@Length(min=0, max=200, message="培训老师ID长度必须介于 0 和 200 之间")
	public String getPeixunlsid() {
		return peixunlsid;
	}

	public void setPeixunlsid(String peixunlsid) {
		this.peixunlsid = peixunlsid;
	}
	
	@Length(min=0, max=200, message="培训日期长度必须介于 0 和 200 之间")
	public String getPeixunrq() {
		return peixunrq;
	}

	public void setPeixunrq(String peixunrq) {
		this.peixunrq = peixunrq;
	}
	
	@Length(min=0, max=200, message="考核形式长度必须介于 0 和 200 之间")
	public String getKaohexs() {
		return kaohexs;
	}

	public void setKaohexs(String kaohexs) {
		this.kaohexs = kaohexs;
	}
	
	@Length(min=0, max=200, message="培训内容长度必须介于 0 和 200 之间")
	public String getPeixunnr() {
		return peixunnr;
	}

	public void setPeixunnr(String peixunnr) {
		this.peixunnr = peixunnr;
	}

	public Attachment[] getPeixunnrfjs() {
		return peixunnrfjs;
	}

	public void setPeixunnrfjs(Attachment[] peixunnrfjs) {
		this.peixunnrfjs = peixunnrfjs;
	}

	@Length(min=0, max=3000, message="培训结果长度必须介于 0 和 3000 之间")
	public String getPeixunjg() {
		return peixunjg;
	}

	public void setPeixunjg(String peixunjg) {
		this.peixunjg = peixunjg;
	}

	public String getBeipeixr() {
		return beipeixr;
	}

	public void setBeipeixr(String beipeixr) {
		this.beipeixr = beipeixr;
	}

	public String getBeipeixrid() {
		return beipeixrid;
	}

	public void setBeipeixrid(String beipeixrid) {
		this.beipeixrid = beipeixrid;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public RenyuanPxjlgx[] getPxjlgx() {
		return pxjlgx;
	}

	public void setPxjlgx(RenyuanPxjlgx[] pxjlgx) {
		this.pxjlgx = pxjlgx;
	}

	public User[] getBeipxr() {
		return beipxr;
	}

	public void setBeipxr(User[] beipxr) {
		this.beipxr = beipxr;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}