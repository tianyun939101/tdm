package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 样品属性Entity
 * @author 詹小梅
 * @version 2017-07-04
 */
public class YangpinSx extends DataEntity<YangpinSx> {
	
	private static final long serialVersionUID = 1L;
	private String yangpinzwmc;		// 样品中文名称
	private String yangpinywmc;		// 样品英文名称
	private String yangpindw;		// 样品单位(字典项)
	private String yangpinzt;		// 样品状态（可用，不可用）
	private String yangpinlbzj;		// 样品类别主键
	private String filed;           // 出库单对应属性
	private String shuxingmc;		// 属性名称
	private String ziduancd;        // 属性字段长度（出库单动态加列长度依据于该字段）
	private String yangpindwmc;     // 单位名称
	
	public YangpinSx() {
		super();
	}

	public YangpinSx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="样品中文名称长度必须介于 0 和 200 之间")
	public String getYangpinzwmc() {
		return yangpinzwmc;
	}

	public void setYangpinzwmc(String yangpinzwmc) {
		this.yangpinzwmc = yangpinzwmc;
	}
	
	@Length(min=0, max=200, message="样品英文名称长度必须介于 0 和 200 之间")
	public String getYangpinywmc() {
		return yangpinywmc;
	}

	public void setYangpinywmc(String yangpinywmc) {
		this.yangpinywmc = yangpinywmc;
	}
	
	@Length(min=0, max=200, message="样品单位(字典项)长度必须介于 0 和 200 之间")
	public String getYangpindw() {
		return yangpindw;
	}

	public void setYangpindw(String yangpindw) {
		this.yangpindw = yangpindw;
	}
	
	@Length(min=0, max=100, message="样品状态（可用，不可用）长度必须介于 0 和 100 之间")
	public String getYangpinzt() {
		return yangpinzt;
	}

	public void setYangpinzt(String yangpinzt) {
		this.yangpinzt = yangpinzt;
	}
	
	@Length(min=0, max=200, message="样品类别主键长度必须介于 0 和 200 之间")
	public String getYangpinlbzj() {
		return yangpinlbzj;
	}

	public void setYangpinlbzj(String yangpinlbzj) {
		this.yangpinlbzj = yangpinlbzj;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getZiduancd() {
		return ziduancd;
	}

	public void setZiduancd(String ziduancd) {
		this.ziduancd = ziduancd;
	}

	public String getShuxingmc() {
		return shuxingmc;
	}

	public void setShuxingmc(String shuxingmc) {
		this.shuxingmc = shuxingmc;
	}

	public String getYangpindwmc() {
		return yangpindwmc;
	}

	public void setYangpindwmc(String yangpindwmc) {
		this.yangpindwmc = yangpindwmc;
	}
}