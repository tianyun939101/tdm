package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 全部设备的所有维修记录Entity
 * @author zhangdengcai
 * @version 2017-08-01
 */
public class ShebeiWxjlAll extends DataEntity<ShebeiWxjlAll> {
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String shebeimc;		//设备名称
	private String sehbeibh;		//设备编号
	private String guzhangms;		// 故障描述
	private String guzhangyy;		// 故障原因
	private String guzhangfxrq;		// 故障发现日期
	private String weixiudw;		// 维修单位
	private String weixiur;		// 维修人
	private String weixiuksrq;		// 维修开始日期
	private String weixiujsrq;		// 维修结束日期
	private String weixiuff;		// 维修方法
	private String weixiuhzt;		// 维修后状态
	private String weixiuzl;		// 维修资料
	private String dengjirid;		// 登记人ID
	private String dengjir;		// 登记人
	private String dengjirq;		// 登记日期

	public ShebeiWxjlAll() {
		super();
	}

	public ShebeiWxjlAll(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=3000, message="故障描述长度必须介于 0 和 3000 之间")
	public String getGuzhangms() {
		return guzhangms;
	}

	public void setGuzhangms(String guzhangms) {
		this.guzhangms = guzhangms;
	}
	
	@Length(min=0, max=3000, message="故障原因长度必须介于 0 和 3000 之间")
	public String getGuzhangyy() {
		return guzhangyy;
	}

	public void setGuzhangyy(String guzhangyy) {
		this.guzhangyy = guzhangyy;
	}
	
	@Length(min=0, max=200, message="故障发现日期长度必须介于 0 和 200 之间")
	public String getGuzhangfxrq() {
		return guzhangfxrq;
	}

	public void setGuzhangfxrq(String guzhangfxrq) {
		this.guzhangfxrq = guzhangfxrq;
	}
	
	@Length(min=0, max=200, message="维修单位长度必须介于 0 和 200 之间")
	public String getWeixiudw() {
		return weixiudw;
	}

	public void setWeixiudw(String weixiudw) {
		this.weixiudw = weixiudw;
	}
	
	@Length(min=0, max=200, message="维修人长度必须介于 0 和 200 之间")
	public String getWeixiur() {
		return weixiur;
	}

	public void setWeixiur(String weixiur) {
		this.weixiur = weixiur;
	}
	
	@Length(min=0, max=200, message="维修开始日期长度必须介于 0 和 200 之间")
	public String getWeixiuksrq() {
		return weixiuksrq;
	}

	public void setWeixiuksrq(String weixiuksrq) {
		this.weixiuksrq = weixiuksrq;
	}
	
	@Length(min=0, max=200, message="维修结束日期长度必须介于 0 和 200 之间")
	public String getWeixiujsrq() {
		return weixiujsrq;
	}

	public void setWeixiujsrq(String weixiujsrq) {
		this.weixiujsrq = weixiujsrq;
	}
	
	@Length(min=0, max=200, message="维修方法长度必须介于 0 和 200 之间")
	public String getWeixiuff() {
		return weixiuff;
	}

	public void setWeixiuff(String weixiuff) {
		this.weixiuff = weixiuff;
	}
	
	@Length(min=0, max=200, message="维修后状态长度必须介于 0 和 200 之间")
	public String getWeixiuhzt() {
		return weixiuhzt;
	}

	public void setWeixiuhzt(String weixiuhzt) {
		this.weixiuhzt = weixiuhzt;
	}
	
	@Length(min=0, max=200, message="维修资料长度必须介于 0 和 200 之间")
	public String getWeixiuzl() {
		return weixiuzl;
	}

	public void setWeixiuzl(String weixiuzl) {
		this.weixiuzl = weixiuzl;
	}
	
	@Length(min=0, max=200, message="登记人ID长度必须介于 0 和 200 之间")
	public String getDengjirid() {
		return dengjirid;
	}

	public void setDengjirid(String dengjirid) {
		this.dengjirid = dengjirid;
	}
	
	@Length(min=0, max=200, message="登记人长度必须介于 0 和 200 之间")
	public String getDengjir() {
		return dengjir;
	}

	public void setDengjir(String dengjir) {
		this.dengjir = dengjir;
	}
	
	@Length(min=0, max=200, message="登记日期长度必须介于 0 和 200 之间")
	public String getDengjirq() {
		return dengjirq;
	}

	public void setDengjirq(String dengjirq) {
		this.dengjirq = dengjirq;
	}

	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}

	public String getSehbeibh() {
		return sehbeibh;
	}

	public void setSehbeibh(String sehbeibh) {
		this.sehbeibh = sehbeibh;
	}
}