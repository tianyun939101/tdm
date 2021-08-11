package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质采购清单Entity
 * @author zhangdengcai
 * @version 2017-07-18
 */
public class BiaozhunwzCgqd extends DataEntity<BiaozhunwzCgqd> {
	
	private static final long serialVersionUID = 1L;
	private String biaozhunzj;		// 标准主键
	private String biaozhunmc;		// 标准名称
	private String biaozhunxh;		// 标准型号
	private String shuliang;		// 数量
	private String xuqiubmid;		// 需求部门ID
	private String xuqiubm;		// 需求部门
	private String xuqiurid;		// 需求人ID
	private String xuqiur;		// 需求人
	private String xuqiusm;		// 需求说明
	private String duiyingsb;		// 对应设备
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
	private String shenheyj;		// 审核意见
	private String caigoudid;		//采购单id
	private String beginShenqingsj;		//开始时间（申请日期查询）
	private String endShenqingsj;		//结束时间（申请日期查询）
	private List<BiaozhunwzCgqdmx> biaozhunwz = new ArrayList<BiaozhunwzCgqdmx>();

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public BiaozhunwzCgqd() {
		super();
	}

	public BiaozhunwzCgqd(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标准主键长度必须介于 0 和 200 之间")
	public String getBiaozhunzj() {
		return biaozhunzj;
	}

	public void setBiaozhunzj(String biaozhunzj) {
		this.biaozhunzj = biaozhunzj;
	}
	
	@Length(min=0, max=200, message="标准名称长度必须介于 0 和 200 之间")
	public String getBiaozhunmc() {
		return biaozhunmc;
	}

	public void setBiaozhunmc(String biaozhunmc) {
		this.biaozhunmc = biaozhunmc;
	}
	
	@Length(min=0, max=200, message="标准型号长度必须介于 0 和 200 之间")
	public String getBiaozhunxh() {
		return biaozhunxh;
	}

	public void setBiaozhunxh(String biaozhunxh) {
		this.biaozhunxh = biaozhunxh;
	}
	
	@Length(min=0, max=200, message="数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="需求部门ID长度必须介于 0 和 200 之间")
	public String getXuqiubmid() {
		return xuqiubmid;
	}

	public void setXuqiubmid(String xuqiubmid) {
		this.xuqiubmid = xuqiubmid;
	}
	
	@Length(min=0, max=200, message="需求部门长度必须介于 0 和 200 之间")
	public String getXuqiubm() {
		return xuqiubm;
	}

	public void setXuqiubm(String xuqiubm) {
		this.xuqiubm = xuqiubm;
	}
	
	@Length(min=0, max=200, message="需求人ID长度必须介于 0 和 200 之间")
	public String getXuqiurid() {
		return xuqiurid;
	}

	public void setXuqiurid(String xuqiurid) {
		this.xuqiurid = xuqiurid;
	}
	
	@Length(min=0, max=200, message="需求人长度必须介于 0 和 200 之间")
	public String getXuqiur() {
		return xuqiur;
	}

	public void setXuqiur(String xuqiur) {
		this.xuqiur = xuqiur;
	}
	
	@Length(min=0, max=2000, message="需求说明长度必须介于 0 和 2000 之间")
	public String getXuqiusm() {
		return xuqiusm;
	}

	public void setXuqiusm(String xuqiusm) {
		this.xuqiusm = xuqiusm;
	}
	
	@Length(min=0, max=200, message="对应设备长度必须介于 0 和 200 之间")
	public String getDuiyingsb() {
		return duiyingsb;
	}

	public void setDuiyingsb(String duiyingsb) {
		this.duiyingsb = duiyingsb;
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
	
	@Length(min=0, max=200, message="申请人id长度必须介于 0 和 200 之间")
	public String getShenqingrid() {
		return shenqingrid;
	}

	public void setShenqingrid(String shenqingrid) {
		this.shenqingrid = shenqingrid;
	}
	
	@Length(min=0, max=200, message="申请人长度必须介于 0 和 200 之间")
	public String getShenqingr() {
		return shenqingr;
	}

	public void setShenqingr(String shenqingr) {
		this.shenqingr = shenqingr;
	}
	
	@Length(min=0, max=200, message="申请时间长度必须介于 0 和 200 之间")
	public String getShenqingsj() {
		return shenqingsj;
	}

	public void setShenqingsj(String shenqingsj) {
		this.shenqingsj = shenqingsj;
	}
	
	@Length(min=0, max=2000, message="申请理由长度必须介于 0 和 2000 之间")
	public String getShenqingly() {
		return shenqingly;
	}

	public void setShenqingly(String shenqingly) {
		this.shenqingly = shenqingly;
	}
	
	@Length(min=0, max=200, message="审核人id长度必须介于 0 和 200 之间")
	public String getShenherid() {
		return shenherid;
	}

	public void setShenherid(String shenherid) {
		this.shenherid = shenherid;
	}
	
	@Length(min=0, max=200, message="审核人长度必须介于 0 和 200 之间")
	public String getShenher() {
		return shenher;
	}

	public void setShenher(String shenher) {
		this.shenher = shenher;
	}
	
	@Length(min=0, max=200, message="审核时间长度必须介于 0 和 200 之间")
	public String getShenhesj() {
		return shenhesj;
	}

	public void setShenhesj(String shenhesj) {
		this.shenhesj = shenhesj;
	}
	
	@Length(min=0, max=200, message="审核结果长度必须介于 0 和 200 之间")
	public String getShenhejg() {
		return shenhejg;
	}

	public void setShenhejg(String shenhejg) {
		this.shenhejg = shenhejg;
	}
	
	@Length(min=0, max=2000, message="审核意见长度必须介于 0 和 2000 之间")
	public String getShenheyj() {
		return shenheyj;
	}

	public void setShenheyj(String shenheyj) {
		this.shenheyj = shenheyj;
	}

	public List<BiaozhunwzCgqdmx> getBiaozhunwz() {
		return biaozhunwz;
	}

	public String getCaigoudid() {
		return caigoudid;
	}

	public void setCaigoudid(String caigoudid) {
		this.caigoudid = caigoudid;
	}

	public void setBiaozhunwz(List<BiaozhunwzCgqdmx> biaozhunwz) {
		this.biaozhunwz = biaozhunwz;
	}

	public String getBeginShenqingsj() {
		return beginShenqingsj;
	}

	public void setBeginShenqingsj(String beginShenqingsj) {
		this.beginShenqingsj = beginShenqingsj;
	}

	public String getEndShenqingsj() {
		return endShenqingsj;
	}

	public void setEndShenqingsj(String endShenqingsj) {
		this.endShenqingsj = endShenqingsj;
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