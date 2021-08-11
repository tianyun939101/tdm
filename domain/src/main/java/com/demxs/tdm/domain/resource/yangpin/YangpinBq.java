package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 样品标签Entity
 * @author 詹小梅
 * @version 2017-07-27
 */
public class YangpinBq extends DataEntity<YangpinBq> {
	
	private static final long serialVersionUID = 1L;
	private String biaoqianmc;		// 标签名称
	private String biaoqiansm;		// 标签说明
	private String chukudzj;		// 出库单id
	
	public YangpinBq() {
		super();
	}

	public YangpinBq(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标签名称长度必须介于 0 和 200 之间")
	public String getBiaoqianmc() {
		return biaoqianmc;
	}

	public void setBiaoqianmc(String biaoqianmc) {
		this.biaoqianmc = biaoqianmc;
	}
	
	@Length(min=0, max=2000, message="标签说明长度必须介于 0 和 2000 之间")
	public String getBiaoqiansm() {
		return biaoqiansm;
	}

	public void setBiaoqiansm(String biaoqiansm) {
		this.biaoqiansm = biaoqiansm;
	}
	
	@Length(min=0, max=200, message="出库单id长度必须介于 0 和 200 之间")
	public String getChukudzj() {
		return chukudzj;
	}

	public void setChukudzj(String chukudzj) {
		this.chukudzj = chukudzj;
	}
	
}