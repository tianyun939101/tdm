package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备采购清单明细Entity
 * @author zhangdengcai
 * @version 2017-07-15
 */
public class ShebeiCgqdmx extends DataEntity<ShebeiCgqdmx> {
	
	private static final long serialVersionUID = 1L;
	private String caigouqdzj;		// 采购清单主键
	private String shebeiid;		// 设备主键
	private String shebeimc;		// 设备名称
	private String shebeixh;		// 设备型号
	private String xuqiubm;		// 需求部门
	private String xuqiubmid;		// 需求部门id
	private String shuliang;		// 采购数量
	private String gujia;			//估价
	private String changjia; 		//厂家
	private String gouzhiyy;		//购置原因
	private String diaoyanqk;		//调研情况
	private String caigouzl;		//采购资料（用于接收附件id）
	private List<Attachment> ziliao = new ArrayList<Attachment>();		//附件

	public ShebeiCgqdmx() {
		super();
	}

	public ShebeiCgqdmx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="采购清单主键长度必须介于 0 和 200 之间")
	public String getCaigouqdzj() {
		return caigouqdzj;
	}

	public void setCaigouqdzj(String caigouqdzj) {
		this.caigouqdzj = caigouqdzj;
	}
	
	@Length(min=0, max=200, message="设备主键长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
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
	
	@Length(min=0, max=200, message="需求部门长度必须介于 0 和 200 之间")
	public String getXuqiubm() {
		return xuqiubm;
	}

	public void setXuqiubm(String xuqiubm) {
		this.xuqiubm = xuqiubm;
	}
	
	@Length(min=0, max=200, message="需求部门id长度必须介于 0 和 200 之间")
	public String getXuqiubmid() {
		return xuqiubmid;
	}

	public void setXuqiubmid(String xuqiubmid) {
		this.xuqiubmid = xuqiubmid;
	}
	
	@Length(min=0, max=200, message="采购数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

	public String getGujia() {
		return gujia;
	}

	public void setGujia(String gujia) {
		this.gujia = gujia;
	}

	public String getChangjia() {
		return changjia;
	}

	public void setChangjia(String changjia) {
		this.changjia = changjia;
	}

	public String getGouzhiyy() {
		return gouzhiyy;
	}

	public void setGouzhiyy(String gouzhiyy) {
		this.gouzhiyy = gouzhiyy;
	}

	public String getDiaoyanqk() {
		return diaoyanqk;
	}

	public void setDiaoyanqk(String diaoyanqk) {
		this.diaoyanqk = diaoyanqk;
	}

	public String getCaigouzl() {
		return caigouzl;
	}

	public void setCaigouzl(String caigouzl) {
		this.caigouzl = caigouzl;
	}

	public List<Attachment> getZiliao() {
		return ziliao;
	}

	public void setZiliao(List<Attachment> ziliao) {
		this.ziliao = ziliao;
	}
}