package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;


/**
 * 试验类型管理Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestType extends DataEntity<TestType> {
	
	private static final long serialVersionUID = 1L;
	private TestType parent;		// 父ID
	private String parentIds;		// 父ID集合
	private String name;		// 中文名称
	private String ename;		// 英文名称
	private String valid;		// 有效性
	private Long type;		// 数据类型,1:试验条件，2：试验参数，3：试验项，4：试验项目，5：试验序列
	
	public TestType() {
		super();
	}

	public TestType(String id){
		super(id);
	}

	@JsonBackReference
	public TestType getParent() {
		return parent;
	}

	public void setParent(TestType parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="父ID集合长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
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
	
	@Length(min=0, max=64, message="有效性长度必须介于 0 和 64 之间")
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}
	
}