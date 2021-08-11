package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质采购清单明细Entity
 * @author zhangdengcai
 * @version 2017-07-18
 */
public class BiaozhunwzCgqdmx extends DataEntity<BiaozhunwzCgqdmx> {
	
	private static final long serialVersionUID = 1L;
	private String biaozhunwzid;		// 标准物质id
	private String caigoudzj;		//采购单主键
	private String bianma;		// 编码
	private String biaozhunwzbh;		// 标准物质编号
	private String biaozhunwzmc;		// 标准物质名称
	private String danwei;		// 单位
	private String biaozhunwzxh;		// 规格型号
	private String shuliang;		// 数量
	private String xuqiubm;		// 需求部门id
	private String xuqiubmmc;		// 需求部门名称
	private String gujia;			//估价
	private String changjia; 		//厂家
	private String gouzhiyy;		//购置原因
	private String diaoyanqk;		//调研情况
	private String caigouzl;		//采购资料（用于接收附件id）
	private List<Attachment> ziliao = new ArrayList<Attachment>();		//附件

	public BiaozhunwzCgqdmx() {
		super();
	}

	public BiaozhunwzCgqdmx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标准物质id长度必须介于 0 和 200 之间")
	public String getBiaozhunwzid() {
		return biaozhunwzid;
	}

	public void setBiaozhunwzid(String biaozhunwzid) {
		this.biaozhunwzid = biaozhunwzid;
	}
	
	@Length(min=0, max=200, message="编码长度必须介于 0 和 200 之间")
	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}
	
	@Length(min=0, max=200, message="标准物质编号长度必须介于 0 和 200 之间")
	public String getBiaozhunwzbh() {
		return biaozhunwzbh;
	}

	public void setBiaozhunwzbh(String biaozhunwzbh) {
		this.biaozhunwzbh = biaozhunwzbh;
	}
	
	@Length(min=0, max=200, message="标准物质名称长度必须介于 0 和 200 之间")
	public String getBiaozhunwzmc() {
		return biaozhunwzmc;
	}

	public void setBiaozhunwzmc(String biaozhunwzmc) {
		this.biaozhunwzmc = biaozhunwzmc;
	}
	
	@Length(min=0, max=200, message="单位长度必须介于 0 和 200 之间")
	public String getDanwei() {
		return danwei;
	}

	public void setDanwei(String danwei) {
		this.danwei = danwei;
	}
	
	@Length(min=0, max=200, message="规格型号长度必须介于 0 和 200 之间")
	public String getBiaozhunwzxh() {
		return biaozhunwzxh;
	}

	public void setBiaozhunwzxh(String biaozhunwzxh) {
		this.biaozhunwzxh = biaozhunwzxh;
	}
	
	@Length(min=0, max=200, message="数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="需求部门id长度必须介于 0 和 200 之间")
	public String getXuqiubm() {
		return xuqiubm;
	}

	public void setXuqiubm(String xuqiubm) {
		this.xuqiubm = xuqiubm;
	}
	
	@Length(min=0, max=200, message="需求部门名称长度必须介于 0 和 200 之间")
	public String getXuqiubmmc() {
		return xuqiubmmc;
	}

	public void setXuqiubmmc(String xuqiubmmc) {
		this.xuqiubmmc = xuqiubmmc;
	}

	public String getCaigoudzj() {
		return caigoudzj;
	}

	public void setCaigoudzj(String caigoudzj) {
		this.caigoudzj = caigoudzj;
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