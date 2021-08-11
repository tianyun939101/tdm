package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 耗材采购清单Entity
 * @author zhangdengcai
 * @version 2017-07-17
 */
public class HaocaiCgqd extends DataEntity<HaocaiCgqd> {
	
	private static final long serialVersionUID = 1L;
	private String haocaimc;		// 耗材名称
	private String xinghaogg;		// 型号规格
	private String shuliang;		// 数量
	private String danwei;		// 单位
	private String gongyings;		// 供应商
	private String qingdanzt;		// 新建，审核通过
	private String shenqingrid;		// 申请人id
	private String shenqingr;		// 申请人
	private String shenqingsj;		// 申请时间
	private String shenqingly;		// 申请理由
	private String shenherid;		// 审核人id
	private String shenher;		// 审核人
	private String shenhesj;		// 审核时间
	private String shenhejg;		// 审核结果
	private String shenheyj;		// 审核依据
	private String shenqingksrq;	// 申请日期（开始）
	private String shenqingjsrq;	//申请日期（结束）
	private String caigoudid;		//采购单id
	private List<HaocaiCgqdmx> haocai = new ArrayList<HaocaiCgqdmx>();	//采购的耗材

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public HaocaiCgqd() {
		super();
	}

	public HaocaiCgqd(String id){
		super(id);
	}

	@Length(min=0, max=200, message="耗材名称长度必须介于 0 和 200 之间")
	public String getHaocaimc() {
		return haocaimc;
	}

	public void setHaocaimc(String haocaimc) {
		this.haocaimc = haocaimc;
	}
	
	@Length(min=0, max=200, message="型号规格长度必须介于 0 和 200 之间")
	public String getXinghaogg() {
		return xinghaogg;
	}

	public void setXinghaogg(String xinghaogg) {
		this.xinghaogg = xinghaogg;
	}
	
	@Length(min=0, max=200, message="数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="单位长度必须介于 0 和 200 之间")
	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	
	@Length(min=0, max=200, message="供应商长度必须介于 0 和 200 之间")
	public String getGongyings() {
		return gongyings;
	}

	public void setGongyings(String gongyings) {
		this.gongyings = gongyings;
	}
	
	@Length(min=0, max=200, message="新建，审核通过长度必须介于 0 和 200 之间")
	public String getQingdanzt() {
		return qingdanzt;
	}

	public void setQingdanzt(String qingdanzt) {
		this.qingdanzt = qingdanzt;
	}
	
	@Length(min=0, max=200, message="shenqingrid长度必须介于 0 和 200 之间")
	public String getShenqingrid() {
		return shenqingrid;
	}

	public void setShenqingrid(String shenqingrid) {
		this.shenqingrid = shenqingrid;
	}
	
	@Length(min=0, max=200, message="shenqingr长度必须介于 0 和 200 之间")
	public String getShenqingr() {
		return shenqingr;
	}

	public void setShenqingr(String shenqingr) {
		this.shenqingr = shenqingr;
	}
	
	@Length(min=0, max=200, message="shenqingsj长度必须介于 0 和 200 之间")
	public String getShenqingsj() {
		return shenqingsj;
	}

	public void setShenqingsj(String shenqingsj) {
		this.shenqingsj = shenqingsj;
	}
	
	@Length(min=0, max=2000, message="shenqingly长度必须介于 0 和 2000 之间")
	public String getShenqingly() {
		return shenqingly;
	}

	public void setShenqingly(String shenqingly) {
		this.shenqingly = shenqingly;
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

	public String getShenqingksrq() {
		return shenqingksrq;
	}

	public void setShenqingksrq(String shenqingksrq) {
		this.shenqingksrq = shenqingksrq;
	}

	public String getShenqingjsrq() {
		return shenqingjsrq;
	}

	public void setShenqingjsrq(String shenqingjsrq) {
		this.shenqingjsrq = shenqingjsrq;
	}

	public List<HaocaiCgqdmx> getHaocai() {
		return haocai;
	}

	public void setHaocai(List<HaocaiCgqdmx> haocai) {
		this.haocai = haocai;
	}

	public String getCaigoudid() {
		return caigoudid;
	}

	public void setCaigoudid(String caigoudid) {
		this.caigoudid = caigoudid;
	}

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
}