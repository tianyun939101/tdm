package com.demxs.tdm.domain.resource.yuangong;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.constant.yuangong.StaffingConstant;

import java.util.Date;
import java.util.List;

/**
 * 科室间用工协调Entity
 */
public class LaborStaffing extends DataEntity<LaborStaffing> {

	private static final long serialVersionUID = 1L;

	private String applyZz;//申请资质

	private String applyLab;//申请科室

	private Date startDate;//工作开始时间

	private Date endDate;//工作结束时间

	private String quantity;//人员需求数量

	private String applicant;//申请人

	private String status; //当前状态

	private LabInfo labInfo;//申请试验室

	private String opUser;//处理人

	private User apply;//申请人

	private Aptitude aptitude;//人员资质

	private List<AuditInfo> auditInfos;//审核记录

	private String zzLabLeader;//资质科室负责人

	private String zzLabLeaderName;//资质科室负责人

	public String getApplyZz() {
		return applyZz;
	}

	public void setApplyZz(String applyZz) {
		this.applyZz = applyZz;
	}

	public String getApplyLab() {
		return applyLab;
	}

	public void setApplyLab(String applyLab) {
		this.applyLab = applyLab;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}

	public User getApply() {
		return apply;
	}

	public void setApply(User apply) {
		this.apply = apply;
	}

	public List<AuditInfo> getAuditInfos() {
		return auditInfos;
	}

	public void setAuditInfos(List<AuditInfo> auditInfos) {
		this.auditInfos = auditInfos;
	}

	public Aptitude getAptitude() {
		return aptitude;
	}

	public void setAptitude(Aptitude aptitude) {
		this.aptitude = aptitude;
	}

	public String getOpUser() {
		if (this.getStatus() == null) {
			return "";
		}
		if (StaffingConstant.laborStaffingStatus.AUDIT.equals(this.status)) {
			return this.labInfo==null?"":this.labInfo.getLeader().getName();
		}
		if (StaffingConstant.laborStaffingStatus.APPROVAL.equals(this.status)) {
			return this.zzLabLeaderName==null?"":this.zzLabLeaderName;
		}
		return this.apply==null?"":this.apply.getName();
	}

	public Boolean getAuditFlag() {
		if (StaffingConstant.laborStaffingStatus.APPLY.equals(this.status)) {
			if(UserUtils.getUser().getId().equals(this.applicant)){
				return true;
			}else{
				return false;
			}
		}
		if (StaffingConstant.laborStaffingStatus.AUDIT.equals(this.status)) {
			if(UserUtils.getUser().getId().equals(this.labInfo.getLeader().getId())){
				return true;
			}else{
				return false;
			}
		}
		if (StaffingConstant.laborStaffingStatus.APPROVAL.equals(this.status)) {
			if(StringUtils.isNotBlank(this.zzLabLeader)){
				String[] arr = this.zzLabLeader.split(",");
				for(String s : arr){
					if(UserUtils.getUser().getId().equals(s)){
						return true;
					}
				}
 			}
		}
		return false;
	}

	public String getZzLabLeader() {
		return zzLabLeader;
	}

	public void setZzLabLeader(String zzLabLeader) {
		this.zzLabLeader = zzLabLeader;
	}

	public String getZzLabLeaderName() {
		return zzLabLeaderName;
	}

	public void setZzLabLeaderName(String zzLabLeaderName) {
		this.zzLabLeaderName = zzLabLeaderName;
	}
}