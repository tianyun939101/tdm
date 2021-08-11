package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;

/**
 * 耗材采购清单明细Entity
 * @author zhangdengcai
 * @version 2017-07-17
 */
public class HaocaiCgqdmx extends DataEntity<HaocaiCgqdmx> {
	
	private static final long serialVersionUID = 1L;
	private String caigoudzj;		// 采购单主键
	private String haocaiid;		// 耗材耗材主键
	private String haocaibh;		// 耗材耗材编号
	private String guigexh;		// 耗材规格
	private String haocaimc;		// 耗材名称
	private String jiliangdw;		// 计量单位
	private String shuliang;		// 数量
	private String gujia;			//估价
	private String changjia; 		//厂家
	private String gouzhiyy;		//购置原因
	private String diaoyanqk;		//调研情况
	private String caigouzl;		//采购资料（用于接收附件id）
	private List<Attachment> ziliao = new ArrayList<Attachment>();		//资料

	public HaocaiCgqdmx() {
		super();
	}

	public HaocaiCgqdmx(String id){
		super(id);
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	@Length(min=0, max=200, message="耗材耗材主键长度必须介于 0 和 200 之间")
	public String getHaocaiid() {
		return haocaiid;
	}

	public void setHaocaiid(String haocaiid) {
		this.haocaiid = haocaiid;
	}
	
	@Length(min=0, max=200, message="耗材耗材编号长度必须介于 0 和 200 之间")
	public String getHaocaibh() {
		return haocaibh;
	}

	public void setHaocaibh(String haocaibh) {
		this.haocaibh = haocaibh;
	}
	
	@Length(min=0, max=200, message="耗材规格长度必须介于 0 和 200 之间")
	public String getGuigexh() {
		return guigexh;
	}

	public void setGuigexh(String guigexh) {
		this.guigexh = guigexh;
	}
	
	@Length(min=0, max=200, message="耗材名称长度必须介于 0 和 200 之间")
	public String getHaocaimc() {
		return haocaimc;
	}

	public void setHaocaimc(String haocaimc) {
		this.haocaimc = haocaimc;
	}
	
	@Length(min=0, max=200, message="计量单位长度必须介于 0 和 200 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}
	
	@Length(min=0, max=200, message="数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
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