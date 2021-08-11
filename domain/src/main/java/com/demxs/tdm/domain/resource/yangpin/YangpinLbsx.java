package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 样品类别属性关系表Entity
 * @author 詹小梅
 * @version 2017-07-12
 */
public class YangpinLbsx extends DataEntity<YangpinLbsx> {
	
	private static final long serialVersionUID = 1L;
	private String yangpinlbzj;		// 样品类别主键
	private String yangpinsxzj;		// 样品属性主键
	private String yangpinsxmc;		//属性名称
	private int  status ; //	状态（删除1 或 新增2）
	private String yangpinzwmc;		// 样品中文名称
	private String yangpinywmc;		// 样品英文名称
	private String yangpindw;		// 样品单位(字典项)
	private String filed;           //出库单对应属性

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public YangpinLbsx() {
		super();
	}

	public YangpinLbsx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="样品类别主键长度必须介于 0 和 200 之间")
	public String getYangpinlbzj() {
		return yangpinlbzj;
	}

	public void setYangpinlbzj(String yangpinlbzj) {
		this.yangpinlbzj = yangpinlbzj;
	}
	
	@Length(min=0, max=200, message="样品属性主键长度必须介于 0 和 200 之间")
	public String getYangpinsxzj() {
		return yangpinsxzj;
	}

	public void setYangpinsxzj(String yangpinsxzj) {
		this.yangpinsxzj = yangpinsxzj;
	}

	public String getYangpinsxmc() {
		return yangpinsxmc;
	}

	public void setYangpinsxmc(String yangpinsxmc) {
		this.yangpinsxmc = yangpinsxmc;
	}

	public String getYangpinzwmc() {
		return yangpinzwmc;
	}

	public void setYangpinzwmc(String yangpinzwmc) {
		this.yangpinzwmc = yangpinzwmc;
	}

	public String getYangpinywmc() {
		return yangpinywmc;
	}

	public void setYangpinywmc(String yangpinywmc) {
		this.yangpinywmc = yangpinywmc;
	}

	public String getYangpindw() {
		return yangpindw;
	}

	public void setYangpindw(String yangpindw) {
		this.yangpindw = yangpindw;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}
}