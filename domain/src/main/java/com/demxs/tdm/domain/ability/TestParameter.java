package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 试验参数Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestParameter extends DataEntity<TestParameter> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 中文名称
	private String ename;		// 英文名称
	private String type;		// 条件类型
	private String unit;		// 单位
	private Long sort;		// 排序
	
	public TestParameter() {
		super();
	}

	public TestParameter(String id){
		super(id);
	}

	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=64, message="条件类型长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="单位长度必须介于 0 和 100 之间")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	public Long getSort() {
		return sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}
	
}