package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材库存Entity
 * @author zhangdengcai
 * @version 2017-09-01
 */
public class HaocaiKc extends DataEntity<HaocaiKc> {
	
	private static final long serialVersionUID = 1L;
	private String haocaimc;		// 耗材名称
	private String guigexh;		// 规格型号
	private String kucunsl;		// 库存数量
	private String anquankcsl;		// 安全库存预警数量
	private String haocailx;		 //耗材类型
	private String jiliangdw;		 //计量单位
	private String yongtu;		 //用途
	private String haocaicjmc;		 //厂商名称
	private String guobie;		 //国别
	private String gongyingsmc;		 //供应商名称
	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public HaocaiKc() {
		super();
	}

	public HaocaiKc(String id){
		super(id);
	}

	@Length(min=0, max=200, message="耗材名称长度必须介于 0 和 200 之间")
	public String getHaocaimc() {
		return haocaimc;
	}

	public void setHaocaimc(String haocaimc) {
		this.haocaimc = haocaimc;
	}
	
	@Length(min=0, max=200, message="规格型号长度必须介于 0 和 200 之间")
	public String getGuigexh() {
		return guigexh;
	}

	public void setGuigexh(String guigexh) {
		this.guigexh = guigexh;
	}
	
	@Length(min=0, max=200, message="库存数量长度必须介于 0 和 200 之间")
	public String getKucunsl() {
		return kucunsl;
	}

	public void setKucunsl(String kucunsl) {
		this.kucunsl = kucunsl;
	}
	
	@Length(min=0, max=200, message="安全库存预警数量长度必须介于 0 和 200 之间")
	public String getAnquankcsl() {
		return anquankcsl;
	}

	public void setAnquankcsl(String anquankcsl) {
		this.anquankcsl = anquankcsl;
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

	public String getHaocailx() {
		return haocailx;
	}

	public void setHaocailx(String haocailx) {
		this.haocailx = haocailx;
	}

	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}

	public String getYongtu() {
		return yongtu;
	}

	public void setYongtu(String yongtu) {
		this.yongtu = yongtu;
	}

	public String getHaocaicjmc() {
		return haocaicjmc;
	}

	public void setHaocaicjmc(String haocaicjmc) {
		this.haocaicjmc = haocaicjmc;
	}

	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}

	public String getGongyingsmc() {
		return gongyingsmc;
	}

	public void setGongyingsmc(String gongyingsmc) {
		this.gongyingsmc = gongyingsmc;
	}
}