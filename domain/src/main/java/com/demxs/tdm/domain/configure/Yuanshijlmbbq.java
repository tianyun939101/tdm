package com.demxs.tdm.domain.configure;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 原始记录模板标签Entity
 * @author wbq
 * @version 2017-06-09
 */
public class Yuanshijlmbbq extends DataEntity<Yuanshijlmbbq> {
	
	private static final long serialVersionUID = 1L;
	private String enname;		// 英文名称
	private String cnname;		// 中文名称
	private String biaoqianlx;		// 标签类型（表单form，列表list，工具tool）
	private String ziduanlx;		// 字段类型（时间time，人员person）
	private Long shunxu;		// 顺序
	private Long ishuixie;		// 是否回写
	private String biaoqiansx; //标签属性
	public String getBiaoqiansx() {
		return biaoqiansx;
	}

	public void setBiaoqiansx(String biaoqiansx) {
		this.biaoqiansx = biaoqiansx;
	}


	public Yuanshijlmbbq() {
		super();
	}

	public Yuanshijlmbbq(String id){
		super(id);
	}

	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEnname() {
		return enname;
	}

	public void setEnname(String enname) {
		this.enname = enname;
	}
	
	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getCnname() {
		return cnname;
	}

	public void setCnname(String cnname) {
		this.cnname = cnname;
	}
	
	@Length(min=0, max=100, message="标签类型（表单form，列表list，工具tool）长度必须介于 0 和 100 之间")
	public String getBiaoqianlx() {
		return biaoqianlx;
	}

	public void setBiaoqianlx(String biaoqianlx) {
		this.biaoqianlx = biaoqianlx;
	}
	
	@Length(min=0, max=100, message="字段类型（时间time，人员person）长度必须介于 0 和 100 之间")
	public String getZiduanlx() {
		return ziduanlx;
	}

	public void setZiduanlx(String ziduanlx) {
		this.ziduanlx = ziduanlx;
	}
	
	public Long getShunxu() {
		return shunxu;
	}

	public void setShunxu(Long shunxu) {
		this.shunxu = shunxu;
	}
	
	public Long getIshuixie() {
		return ishuixie;
	}

	public void setIshuixie(Long ishuixie) {
		this.ishuixie = ishuixie;
	}
	
}