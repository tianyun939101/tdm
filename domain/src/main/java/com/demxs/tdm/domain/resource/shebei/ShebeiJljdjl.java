package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备计量检定记录Entity
 * @author zhangdengcai
 * @version 2017-07-13
 */
public class ShebeiJljdjl extends DataEntity<ShebeiJljdjl> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String jiliangjdjhid;		// 计量计划ID
	private String jiliangjdkssj;		// 计量检定开始时间
	private String jiliangjdjssj;		// 计量检定结束时间
	private String jiliangjdlxr;		// 计量检定联系人
	private String jiliangjdlxfs;		// 计量检定联系方式
	private String jiliangjdnr;		// 计量检定内容
	private String jiliangjdzl;		// 计量检定资料
	private String jiliangjdjg;		// 计量检定结果
	private String jiliangjdzsh;		// 计量检定证书号
	private String jihuajljdrq;		// 计划计量检定日期
	private String jilur;		// 记录人
	private String jilurid;		// 记录人ID
	private List<AttachmentInfo> ziliao = new ArrayList<AttachmentInfo>();	//附件资料
	private String jiliangjddw;		// 计量检定单位
	private String renwuwcnr;//任务完成内容
	
	public ShebeiJljdjl() {
		super();
	}

	public ShebeiJljdjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=200, message="计量计划ID长度必须介于 0 和 200 之间")
	public String getJiliangjdjhid() {
		return jiliangjdjhid;
	}

	public void setJiliangjdjhid(String jiliangjdjhid) {
		this.jiliangjdjhid = jiliangjdjhid;
	}
	
	@Length(min=0, max=200, message="计量检定开始时间长度必须介于 0 和 200 之间")
	public String getJiliangjdkssj() {
		return jiliangjdkssj;
	}

	public void setJiliangjdkssj(String jiliangjdkssj) {
		this.jiliangjdkssj = jiliangjdkssj;
	}
	
	@Length(min=0, max=200, message="计量检定结束时间长度必须介于 0 和 200 之间")
	public String getJiliangjdjssj() {
		return jiliangjdjssj;
	}

	public void setJiliangjdjssj(String jiliangjdjssj) {
		this.jiliangjdjssj = jiliangjdjssj;
	}
	
	@Length(min=0, max=200, message="计量检定联系人长度必须介于 0 和 200 之间")
	public String getJiliangjdlxr() {
		return jiliangjdlxr;
	}

	public void setJiliangjdlxr(String jiliangjdlxr) {
		this.jiliangjdlxr = jiliangjdlxr;
	}
	
	@Length(min=0, max=200, message="计量检定联系方式长度必须介于 0 和 200 之间")
	public String getJiliangjdlxfs() {
		return jiliangjdlxfs;
	}

	public void setJiliangjdlxfs(String jiliangjdlxfs) {
		this.jiliangjdlxfs = jiliangjdlxfs;
	}
	
	@Length(min=0, max=3000, message="计量检定内容长度必须介于 0 和 3000 之间")
	public String getJiliangjdnr() {
		return jiliangjdnr;
	}

	public void setJiliangjdnr(String jiliangjdnr) {
		this.jiliangjdnr = jiliangjdnr;
	}

	public String getJiliangjdzl() {
		return jiliangjdzl;
	}

	public void setJiliangjdzl(String jiliangjdzl) {
		this.jiliangjdzl = jiliangjdzl;
	}
	
	@Length(min=0, max=200, message="计量检定结果长度必须介于 0 和 200 之间")
	public String getJiliangjdjg() {
		return jiliangjdjg;
	}

	public void setJiliangjdjg(String jiliangjdjg) {
		this.jiliangjdjg = jiliangjdjg;
	}
	
	@Length(min=0, max=200, message="计量检定证书号长度必须介于 0 和 200 之间")
	public String getJiliangjdzsh() {
		return jiliangjdzsh;
	}

	public void setJiliangjdzsh(String jiliangjdzsh) {
		this.jiliangjdzsh = jiliangjdzsh;
	}
	
	@Length(min=0, max=200, message="记录人长度必须介于 0 和 200 之间")
	public String getJilur() {
		return jilur;
	}

	public String getJihuajljdrq() {
		return jihuajljdrq;
	}

	public void setJihuajljdrq(String jihuajljdrq) {
		this.jihuajljdrq = jihuajljdrq;
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

	public List<AttachmentInfo> getZiliao() {
		return ziliao;
	}

	public void setZiliao(List<AttachmentInfo> ziliao) {
		this.ziliao = ziliao;
	}

	public String getJiliangjddw() {
		return jiliangjddw;
	}

	public void setJiliangjddw(String jiliangjddw) {
		this.jiliangjddw = jiliangjddw;
	}

	public String getRenwuwcnr() {
		return renwuwcnr;
	}

	public void setRenwuwcnr(String renwuwcnr) {
		this.renwuwcnr = renwuwcnr;
	}
}