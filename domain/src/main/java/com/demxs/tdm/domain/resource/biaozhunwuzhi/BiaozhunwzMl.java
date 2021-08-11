package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 标准物质名录Entity
 * @author zhangdengcai
 * @version 2017-08-31
 */
public class BiaozhunwzMl extends DataEntity<BiaozhunwzMl> {
	
	private static final long serialVersionUID = 1L;
	private String biaozhunwzmc;		// 标准物质名称
	private String bianma;		// 编码
	private String jiliangdw;		// 计量单位
	private String biaozhunwzxh;		// 标准物质型号
	private String changjia;		// 厂家
	private String guobie;		// 国别
	private String gongyings;		// 供应商
	private String shifouxh;		// 是否消耗
	private String anquankcsl;		//安全库存数量
	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	
	public BiaozhunwzMl() {
		super();
	}

	public BiaozhunwzMl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标准物质名称长度必须介于 0 和 200 之间")
	public String getBiaozhunwzmc() {
		return biaozhunwzmc;
	}

	public void setBiaozhunwzmc(String biaozhunwzmc) {
		this.biaozhunwzmc = biaozhunwzmc;
	}
	
	@Length(min=0, max=200, message="编码长度必须介于 0 和 200 之间")
	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
	}
	
	@Length(min=0, max=200, message="计量单位长度必须介于 0 和 200 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}
	
	@Length(min=0, max=200, message="标准物质型号长度必须介于 0 和 200 之间")
	public String getBiaozhunwzxh() {
		return biaozhunwzxh;
	}

	public void setBiaozhunwzxh(String biaozhunwzxh) {
		this.biaozhunwzxh = biaozhunwzxh;
	}
	
	@Length(min=0, max=200, message="厂家长度必须介于 0 和 200 之间")
	public String getChangjia() {
		return changjia;
	}

	public void setChangjia(String changjia) {
		this.changjia = changjia;
	}
	
	@Length(min=0, max=200, message="国别长度必须介于 0 和 200 之间")
	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}
	
	@Length(min=0, max=200, message="供应商长度必须介于 0 和 200 之间")
	public String getGongyings() {
		return gongyings;
	}

	public void setGongyings(String gongyings) {
		this.gongyings = gongyings;
	}
	
	@Length(min=0, max=200, message="是否消耗长度必须介于 0 和 200 之间")
	public String getShifouxh() {
		return shifouxh;
	}

	public void setShifouxh(String shifouxh) {
		this.shifouxh = shifouxh;
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

	public String getAnquankcsl() {
		return anquankcsl;
	}

	public void setAnquankcsl(String anquankcsl) {
		this.anquankcsl = anquankcsl;
	}
}