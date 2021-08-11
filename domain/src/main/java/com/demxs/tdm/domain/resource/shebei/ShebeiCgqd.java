package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备采购清单管理Entity
 * @author zhangdengcai
 * @version 2017-07-15
 */
public class ShebeiCgqd extends DataEntity<ShebeiCgqd> {
	
	private static final long serialVersionUID = 1L;
	private String shebeimc;		// 设备名称
	private String shebeixh;		// 设备型号
	private String shuliang;		// 数量
	private String gujia;		// 估价
	private String jishuyq;		// 技术要求
	private String shengchancj;		// 生产厂家
	private String xuqiubm;		// 需求部门
	private String xuqiubmid;		// 需求部门ID
	private String shenqingr;		// 申请人
	private String shenqingrid;		// 申请人ID
	private String shenqingrq;		// 申请日期
	private String gouzhiyyjdyqk;		// 购置原因及调研情况
	private String fujian;		// 附件
	private String caigouzt;		// 采购状态：待审核、待批准、审核未通过、未批准、已批准。提交采购审核时，状态为&ldquo;待审核&rdquo;；审核完成后状态自动更改为&ldquo;待批准&rdquo;或&ldquo;审核未通过&ldquo;；批准后状态自动更改为&rdquo;已批准&ldquo;或&rdquo;未批准&ldquo;。
	private String shenherid;		// 审核人id
	private String shenher;		// 审核人
	private String shenhesj;		// 审核日期
	private String shenhejg;		// shenhejg
	private String shenheyj;		//
	private String pizhunrid;		// 批准人ID
	private String pizhunr;		// 批准人
	private String pizhunsj;		// 批准时间
	private String pizhunjg;		// 批准结果
	private String pizhunyj;		// 批准意见
	private String caigoudid;		//采购单id
	private String kaishirq;		//开始日期（申请日期起始）
	private String jieshurq;		//结束日期（申请日期截至）
	private List<ShebeiCgqdmx> shebei = new ArrayList<ShebeiCgqdmx>();//采购的设备

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

	public ShebeiCgqd() {
		super();
	}

	public ShebeiCgqd(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备名称长度必须介于 0 和 200 之间")
	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}
	
	@Length(min=0, max=200, message="设备型号长度必须介于 0 和 200 之间")
	public String getShebeixh() {
		return shebeixh;
	}

	public void setShebeixh(String shebeixh) {
		this.shebeixh = shebeixh;
	}
	
	@Length(min=0, max=200, message="数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="估价长度必须介于 0 和 200 之间")
	public String getGujia() {
		return gujia;
	}

	public void setGujia(String gujia) {
		this.gujia = gujia;
	}
	
	@Length(min=0, max=2000, message="技术要求长度必须介于 0 和 2000 之间")
	public String getJishuyq() {
		return jishuyq;
	}

	public void setJishuyq(String jishuyq) {
		this.jishuyq = jishuyq;
	}
	
	@Length(min=0, max=200, message="生产厂家长度必须介于 0 和 200 之间")
	public String getShengchancj() {
		return shengchancj;
	}

	public void setShengchancj(String shengchancj) {
		this.shengchancj = shengchancj;
	}
	
	@Length(min=0, max=200, message="需求部门长度必须介于 0 和 200 之间")
	public String getXuqiubm() {
		return xuqiubm;
	}

	public void setXuqiubm(String xuqiubm) {
		this.xuqiubm = xuqiubm;
	}
	
	@Length(min=0, max=200, message="需求部门ID长度必须介于 0 和 200 之间")
	public String getXuqiubmid() {
		return xuqiubmid;
	}

	public void setXuqiubmid(String xuqiubmid) {
		this.xuqiubmid = xuqiubmid;
	}
	
	@Length(min=0, max=200, message="申请人长度必须介于 0 和 200 之间")
	public String getShenqingr() {
		return shenqingr;
	}

	public void setShenqingr(String shenqingr) {
		this.shenqingr = shenqingr;
	}
	
	@Length(min=0, max=200, message="申请人ID长度必须介于 0 和 200 之间")
	public String getShenqingrid() {
		return shenqingrid;
	}

	public void setShenqingrid(String shenqingrid) {
		this.shenqingrid = shenqingrid;
	}
	
	@Length(min=0, max=200, message="申请日期长度必须介于 0 和 200 之间")
	public String getShenqingrq() {
		return shenqingrq;
	}

	public void setShenqingrq(String shenqingrq) {
		this.shenqingrq = shenqingrq;
	}
	
	@Length(min=0, max=2000, message="购置原因及调研情况长度必须介于 0 和 2000 之间")
	public String getGouzhiyyjdyqk() {
		return gouzhiyyjdyqk;
	}

	public void setGouzhiyyjdyqk(String gouzhiyyjdyqk) {
		this.gouzhiyyjdyqk = gouzhiyyjdyqk;
	}
	
	@Length(min=0, max=200, message="附件长度必须介于 0 和 200 之间")
	public String getFujian() {
		return fujian;
	}

	public void setFujian(String fujian) {
		this.fujian = fujian;
	}
	
	@Length(min=0, max=200, message="采购状态：待审核、待批准、审核未通过、未批准、已批准。提交采购审核时，状态为&ldquo;待审核&rdquo;；审核完成后状态自动更改为&ldquo;待批准&rdquo;或&ldquo;审核未通过&ldquo;；批准后状态自动更改为&rdquo;已批准&ldquo;或&rdquo;未批准&ldquo;。长度必须介于 0 和 200 之间")
	public String getCaigouzt() {
		return caigouzt;
	}

	public void setCaigouzt(String caigouzt) {
		this.caigouzt = caigouzt;
	}
	
	@Length(min=0, max=200, message="shenherid长度必须介于 0 和 200 之间")
	public String getShenherid() {
		return shenherid;
	}

	public void setShenherid(String shenherid) {
		this.shenherid = shenherid;
	}
	
	@Length(min=0, max=200, message="shenher长度必须介于 0 和 200 之间")
	public String getShenher() {
		return shenher;
	}

	public void setShenher(String shenher) {
		this.shenher = shenher;
	}
	
	@Length(min=0, max=200, message="shenhesj长度必须介于 0 和 200 之间")
	public String getShenhesj() {
		return shenhesj;
	}

	public void setShenhesj(String shenhesj) {
		this.shenhesj = shenhesj;
	}
	
	@Length(min=0, max=200, message="shenhejg长度必须介于 0 和 200 之间")
	public String getShenhejg() {
		return shenhejg;
	}

	public void setShenhejg(String shenhejg) {
		this.shenhejg = shenhejg;
	}
	
	@Length(min=0, max=2000, message="shenheyj长度必须介于 0 和 2000 之间")
	public String getShenheyj() {
		return shenheyj;
	}

	public void setShenheyj(String shenheyj) {
		this.shenheyj = shenheyj;
	}
	
	@Length(min=0, max=200, message="批准人ID长度必须介于 0 和 200 之间")
	public String getPizhunrid() {
		return pizhunrid;
	}

	public void setPizhunrid(String pizhunrid) {
		this.pizhunrid = pizhunrid;
	}
	
	@Length(min=0, max=200, message="批准人长度必须介于 0 和 200 之间")
	public String getPizhunr() {
		return pizhunr;
	}

	public void setPizhunr(String pizhunr) {
		this.pizhunr = pizhunr;
	}
	
	@Length(min=0, max=200, message="批准时间长度必须介于 0 和 200 之间")
	public String getPizhunsj() {
		return pizhunsj;
	}

	public void setPizhunsj(String pizhunsj) {
		this.pizhunsj = pizhunsj;
	}
	
	@Length(min=0, max=200, message="批准结果长度必须介于 0 和 200 之间")
	public String getPizhunjg() {
		return pizhunjg;
	}

	public void setPizhunjg(String pizhunjg) {
		this.pizhunjg = pizhunjg;
	}
	
	@Length(min=0, max=2000, message="批准意见长度必须介于 0 和 2000 之间")
	public String getPizhunyj() {
		return pizhunyj;
	}

	public void setPizhunyj(String pizhunyj) {
		this.pizhunyj = pizhunyj;
	}

	public String getKaishirq() {
		return kaishirq;
	}

	public void setKaishirq(String kaishirq) {
		this.kaishirq = kaishirq;
	}

	public String getJieshurq() {
		return jieshurq;
	}

	public void setJieshurq(String jieshurq) {
		this.jieshurq = jieshurq;
	}

	public String getCaigoudid() {
		return caigoudid;
	}

	public void setCaigoudid(String caigoudid) {
		this.caigoudid = caigoudid;
	}

	public List<ShebeiCgqdmx> getShebei() {
		return shebei;
	}

	public void setShebei(List<ShebeiCgqdmx> shebei) {
		this.shebei = shebei;
	}
}