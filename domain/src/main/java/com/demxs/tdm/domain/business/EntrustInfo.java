package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.dataCenter.ZyTestMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 申请信息Entity
 *
 * @author sunjunhui
 * @version 2017-10-30
 */
public class EntrustInfo extends DataEntity<EntrustInfo> {

    private static final long serialVersionUID = 1L;
    private String code;        // 申请单号
    private User user;        // 申请人
    private Office org;        // 部门
    private Date startDate;        // 发起时间
    private String tel;        // 联系电话
    private String email;        // 邮箱
    private Office company;        // 公司
    private String nature;        // 试验性质
    private Integer sampleType;        // 样品类型
    private Integer reportFlag;        // 是否出报告
    private String afterTest;        // 样品试验后处理方式
    private String summary;        // 试验目的
    private String labName;        // 试验室名称
    private Date earliestTime;        // 最早完成时间
    private String labId;        // 试验室
    private User auditUser;    //审核人
    private Date auditDate;    //审核时间
    private User labManager;    //试验室负责人
    private Date acceptDate;    //试验室负责人接收时间
    private User testCharge;    //试验负责人
    private Date confirmDate;    //确认样品时间
    private Date planDate;        //创建计划时间
    private User undoUser;    //撤消人
    private String undoReason;    //撤消原因
    private User stopUser;    //终止用户
    private String stopReason;    //终止原因
    private Integer stage;    //阶段:申请,调度,试验,报告
    private Integer status;        //申请状态:实际为各阶段的状态
    private EntrustOtherInfo otherInfo;    //申请单其他信息
    private List<EntrustTestGroup> testGroupList;    //试验组
    private EntrustSampleGroup sampleGroup;
    private EntrustReport report;    //试验报告
    private Integer emergency; //紧急程度
    private Date startCreateDate;
    private Date endCreateDate;
    private Date finishDate;

    private String commissionName;//委托人
    private String commissionTel;//委托人联系电话
    private String requester;//委托单位
    private Date inspectionDate;//送检日期
    private Date agreeCompleteDate;//协议完成时间
    private String planNo;//计划编号
    private String reportTeleport;//报告传送
    private String taskNo;//任务书编号
    private String discription;//试验目的和描述
    private String fileId;
    private String reason;
    /**
     * 飞机型号
     */
    private String model;
    /**
     * 关联ata章节
     */
    private List<EntrustAtaChapter> ataChapterList;
    /**
     * ata章节完全限定名（父级名称：子级名称）
     */
    private String ataChapterFullName;
    /**********样品关联start************/
    private Integer putInStatus;//申请单入库状态(当此申请单样品组都入库了此状态才是入库状态) 0否 1是
    private List<EntrustSampleGroup> sampleGroups = Lists.newArrayList();//样品组

    /**********样品关联end************/

    private Boolean dataAuth;

    private String isPersonFlag;   //是否按申请人查询标志。“Y” 是。

    private String assignUser;

    public String getIsPersonFlag() {
        return isPersonFlag;
    }

    public void setIsPersonFlag(String isPersonFlag) {
        this.isPersonFlag = isPersonFlag;
    }

    /**
     * 申请单审核信息
     */
    private List<AuditInfo> auditInfos = new ArrayList<>();


    private Date sDate;
    private Date eDate;
    private String dateRange;//
    private List<EntrustInfo> planEntrusts = Lists.newArrayList();

    private String taskVersion;


    private String  taskView;

    private String  subCenter;


    private String  otherRemark;


    public String getOtherRemark() {
        return otherRemark;
    }

    public void setOtherRemark(String otherRemark) {
        this.otherRemark = otherRemark;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public String getTaskVersion() {
        return taskVersion;
    }

    public void setTaskVersion(String taskVersion) {
        this.taskVersion = taskVersion;
    }

    public String getTaskView() {
        return taskView;
    }

    public void setTaskView(String taskView) {
        this.taskView = taskView;
    }

    public EntrustInfo() {
        super();
    }

    public EntrustInfo(String id) {
        super(id);
    }

    @Length(min = 0, max = 100, message = "申请单号长度必须介于 0 和 100 之间")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Office getOrg() {
        return org;
    }

    public void setOrg(Office org) {
        this.org = org;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Length(min = 0, max = 30, message = "联系电话长度必须介于 0 和 30 之间")
    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Length(min = 0, max = 200, message = "邮箱长度必须介于 0 和 200 之间")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Office getCompany() {
        return company;
    }

    public void setCompany(Office company) {
        this.company = company;
    }

    @Length(min = 0, max = 64, message = "试验性质长度必须介于 0 和 64 之间")
    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    @Length(min = 0, max = 64, message = "样品类型长度必须介于 0 和 64 之间")
    public Integer getSampleType() {
        return sampleType;
    }

    public void setSampleType(Integer sampleType) {
        this.sampleType = sampleType;
    }

    @Length(min = 0, max = 1, message = "是否出报告长度必须介于 0 和 1 之间")
    public Integer getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(Integer reportFlag) {
        this.reportFlag = reportFlag;
    }

    @Length(min = 0, max = 100, message = "样品试验后处理方式长度必须介于 0 和 100 之间")
    public String getAfterTest() {
        return afterTest;
    }

    public void setAfterTest(String afterTest) {
        this.afterTest = afterTest;
    }

    @Length(min = 0, max = 200, message = "试验目的长度必须介于 0 和 200 之间")
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Length(min = 0, max = 200, message = "试验室名称长度必须介于 0 和 200 之间")
    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public Date getEarliestTime() {
        return earliestTime;
    }

    public void setEarliestTime(Date earliestTime) {
        this.earliestTime = earliestTime;
    }

    @Length(min = 0, max = 200, message = "试验室长度必须介于 0 和 200 之间")
    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public User getLabManager() {
        return labManager;
    }

    public void setLabManager(User labManager) {
        this.labManager = labManager;
    }

    public User getTestCharge() {
        return testCharge;
    }

    public void setTestCharge(User testCharge) {
        this.testCharge = testCharge;
    }

    public User getUndoUser() {
        return undoUser;
    }

    public void setUndoUser(User undoUser) {
        this.undoUser = undoUser;
    }

    public String getUndoReason() {
        return undoReason;
    }

    public void setUndoReason(String undoReason) {
        this.undoReason = undoReason;
    }

    public User getStopUser() {
        return stopUser;
    }

    public void setStopUser(User stopUser) {
        this.stopUser = stopUser;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public EntrustOtherInfo getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(EntrustOtherInfo otherInfo) {
        this.otherInfo = otherInfo;
    }

    public List<EntrustTestGroup> getTestGroupList() {
        return testGroupList;
    }

    public void setTestGroupList(List<EntrustTestGroup> testGroupList) {
        this.testGroupList = testGroupList;
    }

    public List<AuditInfo> getAuditInfos() {
        return auditInfos;
    }

    public void setAuditInfos(List<AuditInfo> auditInfos) {
        this.auditInfos = auditInfos;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public EntrustReport getReport() {
        return report;
    }

    public void setReport(EntrustReport report) {
        this.report = report;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(Date confirmDate) {
        this.confirmDate = confirmDate;
    }

    public String getOpUser() {
        if (this.getStatus() == null) {
            return "";
        }
        if (EntrustConstants.EntrustStage.AUDIT.equals(this.getStatus())) {
            return this.getAuditUser() == null ? "" : this.getAuditUser().getName();
        } else if (EntrustConstants.EntrustStage.ACCEPT.equals(this.getStatus())) {
            return this.getLabManager() == null ? "" : this.getLabManager().getName();
        } else if (EntrustConstants.DispatchStage.CONFIRM_SAMPLE.equals(this.getStatus()) || EntrustConstants.DispatchStage.CREATE_PLAN.equals(this.getStatus())
                || EntrustConstants.DispatchStage.ASSIGN_TASK.equals(this.getStatus())) {
            return this.getTestCharge() == null ? "" : this.getTestCharge().getName();
        } else if (EntrustConstants.ReportStage.DRAW <= this.getStatus()) {
            return this.getReport() == null ? "" : this.getReport().getOwnerName();
        }
        return this.getUser() == null ? "" : this.getUser().getName();
    }

    public Boolean getStatusFlag() {
        if (EntrustConstants.EntrustStage.DRAFT.equals(this.getStatus())) {
            return true;
        } else if (EntrustConstants.EntrustStage.AUDIT_RETURN.equals(this.getStatus()) && UserUtils.getUser().getId().equals(this.getUser().getId())) {
            return true;
        } else if (EntrustConstants.EntrustStage.ACCEPT_RETURN.equals(this.getStatus()) && UserUtils.getUser().getId().equals(this.getUser().getId())) {
            return true;
        }
        if (this.getLabManager() == null && this.getAuditUser() == null) {
            return false;
        }
        if (EntrustConstants.EntrustStage.AUDIT.equals(this.getStatus()) && UserUtils.getUser().getId().equals(this.getAuditUser().getId())) {
            return true;
        } else if (EntrustConstants.EntrustStage.ACCEPT.equals(this.getStatus()) && UserUtils.getUser().getId().equals(this.getLabManager().getId())) {
            return true;
        }

        return false;
    }

    public Integer getPutInStatus() {
        return putInStatus = putInStatus;
    }

    public void setPutInStatus(Integer putInStatus) {
        this.putInStatus = putInStatus;
    }

    public List<EntrustSampleGroup> getSampleGroups() {
        return sampleGroups;
    }

    public void setSampleGroups(List<EntrustSampleGroup> sampleGroups) {
        this.sampleGroups = sampleGroups;
    }

    public Integer getEmergency() {
        return emergency;
    }

    public void setEmergency(Integer emergency) {
        this.emergency = emergency;
    }

    public Boolean getDataAuth() {
        return dataAuth;
    }

    public void setDataAuth(Boolean dataAuth) {
        this.dataAuth = dataAuth;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public Date getStartCreateDate() {
        return startCreateDate;
    }

    public void setStartCreateDate(Date startCreateDate) {
        this.startCreateDate = startCreateDate;
    }

    public Date getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(Date endCreateDate) {
        this.endCreateDate = endCreateDate;
    }


    public String getCommissionName() {
        return commissionName;
    }

    public void setCommissionName(String commissionName) {
        this.commissionName = commissionName;
    }

    public String getCommissionTel() {
        return commissionTel;
    }

    public void setCommissionTel(String commissionTel) {
        this.commissionTel = commissionTel;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getInspectionDate() {
        return inspectionDate;
    }

    public void setInspectionDate(Date inspectionDate) {
        this.inspectionDate = inspectionDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getAgreeCompleteDate() {
        return agreeCompleteDate;
    }

    public void setAgreeCompleteDate(Date agreeCompleteDate) {
        this.agreeCompleteDate = agreeCompleteDate;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getReportTeleport() {
        return reportTeleport;
    }

    public void setReportTeleport(String reportTeleport) {
        this.reportTeleport = reportTeleport;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    @JsonIgnore
    public Date getsDate() {
        if (StringUtils.isNotBlank(dateRange)) {
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[0]);
        } else {
            return null;
        }
    }

    public void setsDate(Date sDate) {
        this.sDate = sDate;
    }

    @JsonIgnore
    public Date geteDate() {
        if (StringUtils.isNotBlank(dateRange)) {
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[1]);
        } else {
            return null;
        }
    }

    public void seteDate(Date eDate) {
        this.eDate = eDate;
    }

    @JsonIgnore
    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    public List<EntrustInfo> getPlanEntrusts() {
        return planEntrusts;
    }

    public void setPlanEntrusts(List<EntrustInfo> planEntrusts) {
        this.planEntrusts = planEntrusts;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public static final Integer PUTIN = 1;

    public static final Integer NOTPUTIN = 0;

    public String getModel() {
        return model;
    }

    public EntrustInfo setModel(String model) {
        this.model = model;
        return this;
    }

    public List<EntrustAtaChapter> getAtaChapterList() {
        return ataChapterList;
    }

    public EntrustInfo setAtaChapterList(List<EntrustAtaChapter> ataChapterList) {
        this.ataChapterList = ataChapterList;
        return this;
    }

    /**
    * @author Jason
    * @date 2020/6/29 18:29
    * @params []
    * 用作视图传输
    * @return java.util.List<com.demxs.tdm.domain.business.EntrustAtaChapter>
    */
    public List<EntrustAtaChapter> getCopyAtaChapterList() {
        if(null != ataChapterList){
            for (EntrustAtaChapter ataChapter : ataChapterList) {
                ataChapter.setId(ataChapter.getAtaId());
            }
        }
        return ataChapterList;
    }

    public String getAtaChapterFullName() {
        return ataChapterFullName;
    }

    public EntrustInfo setAtaChapterFullName(String ataChapterFullName) {
        this.ataChapterFullName = ataChapterFullName;
        return this;
    }

    public EntrustSampleGroup getSampleGroup() {
        return sampleGroup;
    }

    public void setSampleGroup(EntrustSampleGroup sampleGroup) {
        this.sampleGroup = sampleGroup;
    }
}