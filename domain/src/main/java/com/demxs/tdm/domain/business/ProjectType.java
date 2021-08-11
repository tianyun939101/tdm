package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目类型Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class ProjectType extends TreeEntity<ProjectType> {
	
	private static final long serialVersionUID = 1L;
	private ProjectType parent;		// 父ID
	private String parentIds;		// 父ID集合
	private String name;		// 类别名称
	private String ename;		// 英文名称
	private Integer valid;		// 有效性
	
	public ProjectType() {
		super();
	}

	public ProjectType(String id){
		super(id);
	}


	
	@Override
	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	@Override
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
	
	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	@Override
	@JsonBackReference
	public ProjectType getParent() {
		return parent;
	}

	@Override
	public void setParent(ProjectType parent) {
		this.parent = parent;
	}

	@Override
	@Length(min = 0, max = 2000, message = "parent_ids长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	@Override
	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	@Override
	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}
}