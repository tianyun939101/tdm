package com.demxs.tdm.domain.resource.yuangong;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.constant.yuangong.StaffingConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 试验人力资源调配报告Entity
 */
public class StaffingReport extends DataEntity<StaffingReport> {

	private static final long serialVersionUID = 1L;

	private String workName;//工作名称

	private String applyLab;//申请科室

	private Date ssDate;//实施时间

	private String ssStaffing;//实施人员

	private String ssHours;//实施工时

	private String completion;//完成情况

	private String legacy; //遗留问题

	private String labConfirm;//科室确认

	public LabInfo labInfo;//申请科室

	private String staffingId;//试验人力资源调配ID

	public String getWorkName() {
		return workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public String getApplyLab() {
		return applyLab;
	}

	public void setApplyLab(String applyLab) {
		this.applyLab = applyLab;
	}

	public Date getSsDate() {
		return ssDate;
	}

	public void setSsDate(Date ssDate) {
		this.ssDate = ssDate;
	}

	public String getSsStaffing() {
		return ssStaffing;
	}

	public void setSsStaffing(String ssStaffing) {
		this.ssStaffing = ssStaffing;
	}

	public String getSsHours() {
		return ssHours;
	}

	public void setSsHours(String ssHours) {
		this.ssHours = ssHours;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public String getLegacy() {
		return legacy;
	}

	public void setLegacy(String legacy) {
		this.legacy = legacy;
	}

	public String getLabConfirm() {
		return labConfirm;
	}

	public void setLabConfirm(String labConfirm) {
		this.labConfirm = labConfirm;
	}

	public String getStaffingId() {
		return staffingId;
	}

	public void setStaffingId(String staffingId) {
		this.staffingId = staffingId;
	}

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}
}