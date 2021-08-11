package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目模块Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class ProjectModel extends DataEntity<ProjectModel> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目ID
	private ProjectModel parent;		// 上级模块
	private String parentIds;		// 所有上级模块ID
	private String name;		// 模块名称
	private String charge;		// 负责人
	private String valid;		// 有效性
	private String summary;		// 模块简介
	
	public ProjectModel() {
		super();
	}

	public ProjectModel(String id){
		super(id);
	}

	@Length(min=0, max=64, message="项目ID长度必须介于 0 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@JsonBackReference
	public ProjectModel getParent() {
		return parent;
	}

	public void setParent(ProjectModel parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=2000, message="所有上级模块ID长度必须介于 0 和 2000 之间")
	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}
	
	@Length(min=0, max=100, message="模块名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="负责人长度必须介于 0 和 64 之间")
	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}
	
	@Length(min=0, max=64, message="有效性长度必须介于 0 和 64 之间")
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	@Length(min=0, max=500, message="模块简介长度必须介于 0 和 500 之间")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
}