package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材名录Entity
 * @author zhangdengcai
 * @version 2017-08-31
 */
public class HaocaiMl extends DataEntity<HaocaiMl> {
	
	private static final long serialVersionUID = 1L;
	private String haocaimc;		// 耗材名称
	private String haocailx;		// 耗材类型
	private String haocailxmc;		// 耗材类型名称
	private String jiliangdw;		// 计量单位
	private String guigexh;		// 规格型号
	private String haocaicj;		// 耗材厂家
	private String guobie;		// 国别
	private String gongyings;		// 供应商
	private String yongtu;		// 用途
	private String anquankcsl;		// 安全库存数量
	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public HaocaiMl() {
		super();
	}

	public HaocaiMl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="耗材名称长度必须介于 0 和 200 之间")
	public String getHaocaimc() {
		return haocaimc;
	}

	public void setHaocaimc(String haocaimc) {
		this.haocaimc = haocaimc;
	}
	
	@Length(min=0, max=200, message="耗材类型长度必须介于 0 和 200 之间")
	public String getHaocailx() {
		return haocailx;
	}

	public void setHaocailx(String haocailx) {
		this.haocailx = haocailx;
	}
	
	@Length(min=0, max=200, message="耗材类型名称长度必须介于 0 和 200 之间")
	public String getHaocailxmc() {
		return haocailxmc;
	}

	public void setHaocailxmc(String haocailxmc) {
		this.haocailxmc = haocailxmc;
	}
	
	@Length(min=0, max=200, message="计量单位长度必须介于 0 和 200 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}
	
	@Length(min=0, max=200, message="规格型号长度必须介于 0 和 200 之间")
	public String getGuigexh() {
		return guigexh;
	}

	public void setGuigexh(String guigexh) {
		this.guigexh = guigexh;
	}
	
	@Length(min=0, max=200, message="耗材厂家长度必须介于 0 和 200 之间")
	public String getHaocaicj() {
		return haocaicj;
	}

	public void setHaocaicj(String haocaicj) {
		this.haocaicj = haocaicj;
	}
	
	@Length(min=0, max=200, message="供应商长度必须介于 0 和 200 之间")
	public String getGongyings() {
		return gongyings;
	}

	public void setGongyings(String gongyings) {
		this.gongyings = gongyings;
	}
	
	@Length(min=0, max=200, message="用途长度必须介于 0 和 200 之间")
	public String getYongtu() {
		return yongtu;
	}

	public void setYongtu(String yongtu) {
		this.yongtu = yongtu;
	}
	
	@Length(min=0, max=200, message="安全库存数量长度必须介于 0 和 200 之间")
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

	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}
}