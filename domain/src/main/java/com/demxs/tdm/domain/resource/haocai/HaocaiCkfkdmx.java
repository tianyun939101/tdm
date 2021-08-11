package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材出库/返库单明细Entity
 * @author zhangdengcai
 * @version 2017-07-17
 */
public class HaocaiCkfkdmx extends DataEntity<HaocaiCkfkdmx> {
	
	private static final long serialVersionUID = 1L;
	private String chukufkdid;		// 出库返库单主键
	private String haocaiid;		// 出库或返库的耗材的主键
	private String haocaimc;		// 耗材名称
	private String eipbianhao;		// EIP编号
	private String guigexh;		// 耗材规格
	private String chukufksl;		// 出库/返库数量
	private String jiliangdw;		// 计量单位
	private String haocailx;		//耗材类型
	private String haocailxmc;		//耗材类型名称
	
	public HaocaiCkfkdmx() {
		super();
	}

	public HaocaiCkfkdmx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="出库返库单主键长度必须介于 0 和 200 之间")
	public String getChukufkdid() {
		return chukufkdid;
	}

	public void setChukufkdid(String chukufkdid) {
		this.chukufkdid = chukufkdid;
	}
	
	@Length(min=0, max=200, message="出库或返库的耗材的主键长度必须介于 0 和 200 之间")
	public String getHaocaiid() {
		return haocaiid;
	}

	public void setHaocaiid(String haocaiid) {
		this.haocaiid = haocaiid;
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
	
	@Length(min=0, max=200, message="耗材规格长度必须介于 0 和 200 之间")
	public String getGuigexh() {
		return guigexh;
	}

	public void setGuigexh(String guigexh) {
		this.guigexh = guigexh;
	}
	
	@Length(min=0, max=200, message="出库/返库数量长度必须介于 0 和 200 之间")
	public String getChukufksl() {
		return chukufksl;
	}

	public void setChukufksl(String chukufksl) {
		this.chukufksl = chukufksl;
	}
	
	@Length(min=0, max=10, message="计量单位长度必须介于 0 和 10 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}

	public String getHaocailx() {
		return haocailx;
	}

	public void setHaocailx(String haocailx) {
		this.haocailx = haocailx;
	}

	public String getHaocailxmc() {
		return haocailxmc;
	}

	public void setHaocailxmc(String haocailxmc) {
		this.haocailxmc = haocailxmc;
	}
}