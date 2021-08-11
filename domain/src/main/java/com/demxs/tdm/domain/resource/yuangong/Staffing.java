package com.demxs.tdm.domain.resource.yuangong;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.constant.yuangong.StaffingConstant;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 试验人力资源调配Entity
 */
public class Staffing extends DataEntity<Staffing> {

	private static final long serialVersionUID = 1L;

	private String workName;//工作名称

	private String applyLab;//申请科室

	private Date workDate;//用工时间

	private String staffing;//人员需求

	private String workHours;//工作

	private String precaution;//注意事项

	private String applicant;//申请人

	private String workContent;//工作内容

	private String status; //当前状态

	private LabInfo labInfo;//申请试验室

	private String opUser;//处理人

	private User apply;//申请人

	private List<AuditInfo> auditInfos;//审核记录

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}

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

	@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getWorkDate() {
		return workDate;
	}

	public void setWorkDate(Date workDate) {
		this.workDate = workDate;
	}

	public String getStaffing() {
		return staffing;
	}

	public void setStaffing(String staffing) {
		this.staffing = staffing;
	}

	public String getWorkHours() {
		return workHours;
	}

	public void setWorkHours(String workHours) {
		this.workHours = workHours;
	}

	public String getPrecaution() {
		return precaution;
	}

	public void setPrecaution(String precaution) {
		this.precaution = precaution;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getWorkContent() {
		return workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public List<AuditInfo> getAuditInfos() {
		return auditInfos;
	}

	public void setAuditInfos(List<AuditInfo> auditInfos) {
		this.auditInfos = auditInfos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getApply() {
		return apply;
	}

	public void setApply(User apply) {
		this.apply = apply;
	}

	public String getOpUser() {
		if (this.getStatus() == null) {
			return "";
		}
		if (StaffingConstant.staffingStatus.AUDIT.equals(this.getStatus())) {
			return this.labInfo==null?"":this.labInfo.getLeader().getName();
		}
		return this.apply==null?"":this.apply.getName();
	}

	public void setOpUser(String opUser) {
		this.opUser = opUser;
	}

	public Boolean getAuditFlag() {
		if (StaffingConstant.staffingStatus.APPLY.equals(this.getStatus())) {
			if(UserUtils.getUser().getId().equals(this.applicant)){
				return true;
			}else{
				return false;
			}
		}
		if (StaffingConstant.staffingStatus.AUDIT.equals(this.getStatus())) {
			if(UserUtils.getUser().getId().equals(this.labInfo.getLeader().getId())){
				return true;
			}else{
				return false;
			}
		}
		if (StaffingConstant.staffingStatus.RECORD.equals(this.getStatus())) {
			if(UserUtils.getUser().getId().equals(this.applicant)){
				return true;
			}else{
				return false;
			}
		}
		return false;
	}
}