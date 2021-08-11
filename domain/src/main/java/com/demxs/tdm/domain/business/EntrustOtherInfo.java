package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 申请其他信息Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustOtherInfo extends DataEntity<EntrustOtherInfo> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String projectId;		// 项目ID
	private String projectName;		// 项目名称
	private String projectCode;		// 项目编号
	private String phase;		// 测试阶段
	private String material;		// 被测材料
	private String tech;		// 改善工艺
	private String assist;		// 辅助信息
	
	public EntrustOtherInfo() {
		super();
	}

	public EntrustOtherInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=64, message="项目ID长度必须介于 0 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	@Length(min=0, max=100, message="项目名称长度必须介于 0 和 100 之间")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	@Length(min=0, max=100, message="项目编号长度必须介于 0 和 100 之间")
	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	
	@Length(min=0, max=64, message="测试阶段长度必须介于 0 和 64 之间")
	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}
	
	@Length(min=0, max=200, message="被测材料长度必须介于 0 和 200 之间")
	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}
	
	@Length(min=0, max=200, message="改善工艺长度必须介于 0 和 200 之间")
	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}
	
	@Length(min=0, max=200, message="辅助信息长度必须介于 0 和 200 之间")
	public String getAssist() {
		return assist;
	}

	public void setAssist(String assist) {
		this.assist = assist;
	}
	
}