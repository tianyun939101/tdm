package com.demxs.tdm.domain.resource.huanjing;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 环境数据Entity
 * @author zhangdengcai
 * @version 2017-07-19
 */
public class HuanjingSj extends DataEntity<HuanjingSj> {
	
	private static final long serialVersionUID = 1L;
	private String shebieid;		// 设备id
	private String huanjingsbid;		// 环境设备ID
	private String fangjianid;		// 房间id（设备存放位置的id）
	private String fangjian;		// 房间名称（设备存放位置的名称）
	private String shebeibh;		// 设备编号
	private String shebeimc;		// 设备名称
	private String shijian;		// 时间
	private String wendu;		// 温度
	private String shidu;		// 湿度
	private String zhendong;		// 震动
	private String zaosheng;		// 噪声
	private String fushez;		// 辐射值
	private String shujuly;		//数据来源（1为内部采集数据；2为录入的环境设备数据）
	private String shujulx;		//数据类型（1：温湿度数据 2：震动、噪声、辐射数据）

	private String wendusx;//温度上限
	private String wenduxx;
	private String shidusx;//湿度上限
	private String shiduxx;
	private String zaoshengsx;//噪声上限
	private String zaoshengxx;
	private String zhendongsx;//震动上限
	private String zhendongxx;

	public HuanjingSj() {
		super();
	}

	public HuanjingSj(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备id长度必须介于 0 和 200 之间")
	public String getShebieid() {
		return shebieid;
	}

	public void setShebieid(String shebieid) {
		this.shebieid = shebieid;
	}
	
	@Length(min=0, max=200, message="环境设备ID长度必须介于 0 和 200 之间")
	public String getHuanjingsbid() {
		return huanjingsbid;
	}

	public void setHuanjingsbid(String huanjingsbid) {
		this.huanjingsbid = huanjingsbid;
	}
	
	@Length(min=0, max=200, message="房间id（设备存放位置的id）长度必须介于 0 和 200 之间")
	public String getFangjianid() {
		return fangjianid;
	}

	public void setFangjianid(String fangjianid) {
		this.fangjianid = fangjianid;
	}
	
	@Length(min=0, max=200, message="房间名称（设备存放位置的名称）长度必须介于 0 和 200 之间")
	public String getFangjian() {
		return fangjian;
	}

	public void setFangjian(String fangjian) {
		this.fangjian = fangjian;
	}
	
	@Length(min=0, max=200, message="设备编号长度必须介于 0 和 200 之间")
	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
	}
	
	@Length(min=0, max=200, message="设备名称长度必须介于 0 和 200 之间")
	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}
	
	@Length(min=0, max=200, message="时间长度必须介于 0 和 200 之间")
	public String getShijian() {
		return shijian;
	}

	public void setShijian(String shijian) {
		this.shijian = shijian;
	}
	
	@Length(min=0, max=200, message="温度长度必须介于 0 和 200 之间")
	public String getWendu() {
		return wendu;
	}

	public void setWendu(String wendu) {
		this.wendu = wendu;
	}
	
	@Length(min=0, max=2000, message="湿度长度必须介于 0 和 2000 之间")
	public String getShidu() {
		return shidu;
	}

	public void setShidu(String shidu) {
		this.shidu = shidu;
	}
	
	@Length(min=0, max=200, message="震动长度必须介于 0 和 200 之间")
	public String getZhendong() {
		return zhendong;
	}

	public void setZhendong(String zhendong) {
		this.zhendong = zhendong;
	}
	
	@Length(min=0, max=200, message="噪声长度必须介于 0 和 200 之间")
	public String getZaosheng() {
		return zaosheng;
	}

	public void setZaosheng(String zaosheng) {
		this.zaosheng = zaosheng;
	}
	
	@Length(min=0, max=200, message="辐射值长度必须介于 0 和 200 之间")
	public String getFushez() {
		return fushez;
	}

	public void setFushez(String fushez) {
		this.fushez = fushez;
	}

	public String getShujuly() {
		return shujuly;
	}

	public void setShujuly(String shujuly) {
		this.shujuly = shujuly;
	}

	public String getShujulx() {
		return shujulx;
	}

	public void setShujulx(String shujulx) {
		this.shujulx = shujulx;
	}

	public String getWendusx() {
		return wendusx;
	}

	public void setWendusx(String wendusx) {
		this.wendusx = wendusx;
	}

	public String getWenduxx() {
		return wenduxx;
	}

	public void setWenduxx(String wenduxx) {
		this.wenduxx = wenduxx;
	}

	public String getShidusx() {
		return shidusx;
	}

	public void setShidusx(String shidusx) {
		this.shidusx = shidusx;
	}

	public String getShiduxx() {
		return shiduxx;
	}

	public void setShiduxx(String shiduxx) {
		this.shiduxx = shiduxx;
	}

	public String getZaoshengsx() {
		return zaoshengsx;
	}

	public void setZaoshengsx(String zaoshengsx) {
		this.zaoshengsx = zaoshengsx;
	}

	public String getZaoshengxx() {
		return zaoshengxx;
	}

	public void setZaoshengxx(String zaoshengxx) {
		this.zaoshengxx = zaoshengxx;
	}

	public String getZhendongsx() {
		return zhendongsx;
	}

	public void setZhendongsx(String zhendongsx) {
		this.zhendongsx = zhendongsx;
	}

	public String getZhendongxx() {
		return zhendongxx;
	}

	public void setZhendongxx(String zhendongxx) {
		this.zhendongxx = zhendongxx;
	}
}