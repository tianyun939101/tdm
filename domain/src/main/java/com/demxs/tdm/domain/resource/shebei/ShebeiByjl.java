package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 设备保养记录Entity
 * @author zhangdengcai
 * @version 2017-07-15
 */
public class ShebeiByjl extends DataEntity<ShebeiByjl> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String baoyangnr;		// 保养内容
	private String baoyangdw;		// 保养单位
	private User baoyangr;		// 保养人
	private String baoyangrId;
	private String baoyanggcjl;		// 保养过程记录
	private String baoyangzl;		// 保养资料
	private List<AttachmentInfo> baoyangzlList = Lists.newArrayList();//保养资料数组
	private String shijibyrq;		// 实际保养日期
	private String jihuabyrq;		//计划保养日期
	private String baoyangksrq;		// 保养开始时间
	private String baoyangjsrq;		// 保养结束时间
	private String baoyangjg;		// 保养结果
	private String jilur;		// 记录人
	private String jilurid;		// 记录人ID
	private String baoyangjhid;		// 保养计划ID

	/****************2017-08-15 需求变更增加***************/
	private String baoyangyq;		// 保养要求
	private String baoyangcs;		// 保养措施
	private String baoyangtxts;		// 保养提醒（天）


	//以下字段仅供装载、传输数据信息，数据库表中并无此列
	private String shebeimc;	//设备名称
	private String shebeibh;	//设备编号

	public ShebeiByjl() {
		super();
	}

	public ShebeiByjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=2000, message="保养内容长度必须介于 0 和 2000 之间")
	public String getBaoyangnr() {
		return baoyangnr;
	}

	public void setBaoyangnr(String baoyangnr) {
		this.baoyangnr = baoyangnr;
	}
	
	@Length(min=0, max=200, message="保养单位长度必须介于 0 和 200 之间")
	public String getBaoyangdw() {
		return baoyangdw;
	}

	public void setBaoyangdw(String baoyangdw) {
		this.baoyangdw = baoyangdw;
	}

	public User getBaoyangr() {
		return baoyangr;
	}

	public void setBaoyangr(User baoyangr) {
		this.baoyangr = baoyangr;
	}

	@Length(min=0, max=200, message="保养过程记录长度必须介于 0 和 200 之间")
	public String getBaoyanggcjl() {
		return baoyanggcjl;
	}

	public void setBaoyanggcjl(String baoyanggcjl) {
		this.baoyanggcjl = baoyanggcjl;
	}
	
	public String getBaoyangzl() {
		return baoyangzl;
	}

	public void setBaoyangzl(String baoyangzl) {
		this.baoyangzl = baoyangzl;
	}
	
	@Length(min=0, max=200, message="实际保养日期长度必须介于 0 和 200 之间")
	public String getShijibyrq() {
		return shijibyrq;
	}

	public void setShijibyrq(String shijibyrq) {
		this.shijibyrq = shijibyrq;
	}
	
	@Length(min=0, max=200, message="保养开始时间长度必须介于 0 和 200 之间")
	public String getBaoyangksrq() {
		return baoyangksrq;
	}

	public void setBaoyangksrq(String baoyangksrq) {
		this.baoyangksrq = baoyangksrq;
	}
	
	@Length(min=0, max=200, message="保养结束时间长度必须介于 0 和 200 之间")
	public String getBaoyangjsrq() {
		return baoyangjsrq;
	}

	public void setBaoyangjsrq(String baoyangjsrq) {
		this.baoyangjsrq = baoyangjsrq;
	}
	
	@Length(min=0, max=200, message="保养结果长度必须介于 0 和 200 之间")
	public String getBaoyangjg() {
		return baoyangjg;
	}

	public void setBaoyangjg(String baoyangjg) {
		this.baoyangjg = baoyangjg;
	}
	
	@Length(min=0, max=200, message="记录人长度必须介于 0 和 200 之间")
	public String getJilur() {
		return jilur;
	}

	public void setJilur(String jilur) {
		this.jilur = jilur;
	}
	
	@Length(min=0, max=200, message="记录人ID长度必须介于 0 和 200 之间")
	public String getJilurid() {
		return jilurid;
	}

	public void setJilurid(String jilurid) {
		this.jilurid = jilurid;
	}

	public String getBaoyangjhid() {
		return baoyangjhid;
	}

	public void setBaoyangjhid(String baoyangjhid) {
		this.baoyangjhid = baoyangjhid;
	}

	public String getJihuabyrq() {
		return jihuabyrq;
	}

	public void setJihuabyrq(String jihuabyrq) {
		this.jihuabyrq = jihuabyrq;
	}

	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}

	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
	}

	public String getBaoyangyq() {
		return baoyangyq;
	}

	public void setBaoyangyq(String baoyangyq) {
		this.baoyangyq = baoyangyq;
	}

	public String getBaoyangcs() {
		return baoyangcs;
	}

	public void setBaoyangcs(String baoyangcs) {
		this.baoyangcs = baoyangcs;
	}

	public String getBaoyangtxts() {
		return baoyangtxts;
	}

	public void setBaoyangtxts(String baoyangtxts) {
		this.baoyangtxts = baoyangtxts;
	}

	public List<AttachmentInfo> getBaoyangzlList() {
		return baoyangzlList;
	}

	public void setBaoyangzlList(List<AttachmentInfo> baoyangzlList) {
		this.baoyangzlList = baoyangzlList;
	}

	public String getBaoyangrId() {
		return baoyangrId;
	}

	public void setBaoyangrId(String baoyangrId) {
		this.baoyangrId = baoyangrId;
	}
}