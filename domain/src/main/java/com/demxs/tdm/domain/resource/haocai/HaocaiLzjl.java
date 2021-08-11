package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.comac.common.constant.HaocaiConstans;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材流转记录Entity
 * @author zhangdengcai
 * @version 2017-07-17
 */
public class HaocaiLzjl extends DataEntity<HaocaiLzjl> {
	
	private static final long serialVersionUID = 1L;
	private String haocaizj;		// 耗材主键
	private String haocailx;		// 耗材流向：入库；出库；返库
	private String shuliang;		// 入库/领取/归还数量
	private String haocaimc;		// 耗材名称
	private String haocaibh;		//耗材编号
	private String eipbianhao;		// EIP编号
	private String haocaileix;		// 耗材类型ID
	private String haocaileixmc;		// 耗材类型名称
	private String jiliangdw;		// 计量单位
	private String guigexh;		//耗材规格
	private String caozuor;		// 入库人/领取人/归还人ID
	private String caozuormc;		// 入库人/领取人/归还人名称
	private String lingqughssbm;		// 领取/归还所属部门ID
	private String lingqughssbmmc;		// 领取/归还所属部门名称
	private String riqi;		// 入库/出库日期/返库日期
	private String dengjir;		// 登记人ID
	private String dengjirmc;		// 登记人名称

	private String ruku = HaocaiConstans.haoCaiLzlx.RUKU;
	private String chuku = HaocaiConstans.haoCaiLzlx.CHUKU;
	private String fanku = HaocaiConstans.haoCaiLzlx.FANKU;


	public String getRuku() {
		return ruku;
	}

	public void setRuku(String ruku) {
		this.ruku = ruku;
	}

	public String getChuku() {
		return chuku;
	}

	public void setChuku(String chuku) {
		this.chuku = chuku;
	}

	public String getFanku() {
		return fanku;
	}

	public void setFanku(String fanku) {
		this.fanku = fanku;
	}

	public HaocaiLzjl() {
		super();
	}

	public HaocaiLzjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="耗材主键长度必须介于 0 和 200 之间")
	public String getHaocaizj() {
		return haocaizj;
	}

	public void setHaocaizj(String haocaizj) {
		this.haocaizj = haocaizj;
	}
	
	@Length(min=0, max=200, message="耗材流向：入库；出库；返库长度必须介于 0 和 200 之间")
	public String getHaocailx() {
		return haocailx;
	}

	public void setHaocailx(String haocailx) {
		this.haocailx = haocailx;
	}
	
	@Length(min=0, max=200, message="入库/领取/归还数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="耗材名称长度必须介于 0 和 200 之间")
	public String getHaocaimc() {
		return haocaimc;
	}

	public void setHaocaimc(String haocaimc) {
		this.haocaimc = haocaimc;
	}
	
	@Length(min=0, max=200, message="EIP编号长度必须介于 0 和 200 之间")
	public String getEipbianhao() {
		return eipbianhao;
	}

	public void setEipbianhao(String eipbianhao) {
		this.eipbianhao = eipbianhao;
	}
	
	@Length(min=0, max=200, message="耗材类型ID长度必须介于 0 和 200 之间")
	public String getHaocaileix() {
		return haocaileix;
	}

	public void setHaocaileix(String haocaileix) {
		this.haocaileix = haocaileix;
	}
	
	@Length(min=0, max=200, message="耗材类型名称长度必须介于 0 和 200 之间")
	public String getHaocaileixmc() {
		return haocaileixmc;
	}

	public void setHaocaileixmc(String haocaileixmc) {
		this.haocaileixmc = haocaileixmc;
	}
	
	@Length(min=0, max=200, message="计量单位长度必须介于 0 和 200 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}
	
	@Length(min=0, max=200, message="入库人/领取人/归还人ID长度必须介于 0 和 200 之间")
	public String getCaozuor() {
		return caozuor;
	}

	public void setCaozuor(String caozuor) {
		this.caozuor = caozuor;
	}
	
	@Length(min=0, max=200, message="入库人/领取人/归还人名称长度必须介于 0 和 200 之间")
	public String getCaozuormc() {
		return caozuormc;
	}

	public void setCaozuormc(String caozuormc) {
		this.caozuormc = caozuormc;
	}
	
	@Length(min=0, max=200, message="领取/归还所属部门ID长度必须介于 0 和 200 之间")
	public String getLingqughssbm() {
		return lingqughssbm;
	}

	public void setLingqughssbm(String lingqughssbm) {
		this.lingqughssbm = lingqughssbm;
	}
	
	@Length(min=0, max=200, message="领取/归还所属部门名称长度必须介于 0 和 200 之间")
	public String getLingqughssbmmc() {
		return lingqughssbmmc;
	}

	public void setLingqughssbmmc(String lingqughssbmmc) {
		this.lingqughssbmmc = lingqughssbmmc;
	}
	
	@Length(min=0, max=200, message="入库/出库日期/返库日期长度必须介于 0 和 200 之间")
	public String getRiqi() {
		return riqi;
	}

	public void setRiqi(String riqi) {
		this.riqi = riqi;
	}
	
	@Length(min=0, max=200, message="登记人ID长度必须介于 0 和 200 之间")
	public String getDengjir() {
		return dengjir;
	}

	public void setDengjir(String dengjir) {
		this.dengjir = dengjir;
	}
	
	@Length(min=0, max=200, message="登记人名称长度必须介于 0 和 200 之间")
	public String getDengjirmc() {
		return dengjirmc;
	}

	public void setDengjirmc(String dengjirmc) {
		this.dengjirmc = dengjirmc;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getGuigexh() {
		return guigexh;
	}

	public void setGuigexh(String guigexh) {
		this.guigexh = guigexh;
	}

	public String getHaocaibh() {
		return haocaibh;
	}

	public void setHaocaibh(String haocaibh) {
		this.haocaibh = haocaibh;
	}
}