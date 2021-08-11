package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.NoStandardEntrustInfoEnum;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

/**
 * 非标业务申请单entity
 */
public class NoStandardEntrustInfo extends DataEntity<NoStandardEntrustInfo> {

    private static final long serialVersionUID = 1L;
    //申请单号
    private String code;
    //申请人
    private String applicant;
    //申请部门
    private Office org;
    //申请单名称
    private String entrustName;
    //试验性质
    private String testNature;
    //测试目的
    private String objective;
    //是否出报告
    private String reportFlag;
    //状态
    private String status;
    //委托人
    private User client;
    //委托人联系电话
    private String clientTel;
    //委托单位
    private String requester;
    //送检日期
    private Date inspectionDate;
    //协议完成时间
    private Date agreeCompleteDate;
    //报告传送
    private String reportTeleport;
    //任务书编号
    private String taskNo;
    //计划编号
    private String planNo;
    //任务名称-->更名为：试验项目名称
    private String taskName;
    //计划性质
    private String planNature;
    //选择产品-->更名为：飞机型号
    private String product;
    //选择ATA章节
    @JsonProperty("ATAChapterList")
    private List<NoStandardATAChapter> ATAChapterList;
    /**
     * ata章节完全限定名（父级名称：子级名称）
     */
    private String ataChapterFullName;
    //试验目的
    private String testPurpose;
    //试验项目及试样描述
    private String testInformation;
    //附件资料
    private String data;
    //实验室
    private String labId;
    //实验室名称
    private String labName;
    private LabInfo labInfo;
    //实验室负责人
    private User labManager;
    //实验室负责人接收时间
    private Date acceptDate;
    //撤销人
    private User undoUser;
    //撤销原因
    private String undoReason;
    //终止人
    private User stopUser;
    //终止原因
    private String stopReason;
    //申请单审核信息
    private List<AuditInfo> auditInfoList;
    //实验项目
    private List<NoStandardTestItem> testItemList;

    //opUser
    //试验负责人
    private String testManager;
    private String testManagerId;
    //试验执行人
    private String testUsers;
    public String curAuditUser;
    //关联对象
    //资源分配
    private String resourceId;
    private NoStandardEntrustResource resource;
    //任务执行
    private String executionId;
    private NoStandardExecution execution;
    //报告
    private String reportId;
    private NoStandardEntrustReport report;

    public NoStandardEntrustInfo(){
        super();
    }

    public NoStandardEntrustInfo(String id){
        super(id);
    }

    public String getOpUser(){
        if(NoStandardEntrustInfoEnum.RESOURCE.getCode().equals(status)){
            if(StringUtils.isBlank(curAuditUser)){
                return "";
            }else{
                User user = UserUtils.get(curAuditUser);
                return user == null ? "" : user.getName();
            }
        }else if(NoStandardEntrustInfoEnum.INSPECT.getCode().equals(status)){
            if(StringUtils.isBlank(curAuditUser)){
                return testManager;
            }else{
                User user = UserUtils.get(curAuditUser);
                return user == null ? "" : user.getName();
            }
        }else if(NoStandardEntrustInfoEnum.EXECUTION.getCode().equals(status)){
            return testManager == null ? "" :
                    (testManager+(testUsers == null ? "" : ",")) +
                            (testUsers == null ? "" : testUsers);
        }else if(NoStandardEntrustInfoEnum.COMPILE.getCode().equals(status)){
            if(StringUtils.isBlank(curAuditUser)){
                return testManager == null ? "" :
                        (testManager+(testUsers == null ? "" : ",")) +
                                (testUsers == null ? "" : testUsers);
            }else{
                StringBuilder sb = new StringBuilder();
                String[] split = curAuditUser.split(",");
                for(int i = 0; i < split.length; i++){
                    User user = UserUtils.get(split[i]);
                    if(null != user){
                        sb.append(user.getName()).append(",");
                    }
                }
                if(sb.length() > 0 ){
                    sb.deleteCharAt(sb.length() - 1);
                }

                return sb.toString();
            }
        }else if(NoStandardEntrustInfoEnum.FILE.getCode().equals(status)){
            return null;
        }
        return  testManager;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public Office getOrg() {
        return org;
    }

    public void setOrg(Office org) {
        this.org = org;
    }

    public String getTestNature() {
        return testNature;
    }

    public void setTestNature(String testNature) {
        this.testNature = testNature;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getReportFlag() {
        return reportFlag;
    }

    public void setReportFlag(String reportFlag) {
        this.reportFlag = reportFlag;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public String getClientTel() {
        return clientTel;
    }

    public void setClientTel(String clientTel) {
        this.clientTel = clientTel;
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

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getPlanNature() {
        return planNature;
    }

    public void setPlanNature(String planNature) {
        this.planNature = planNature;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public List<NoStandardATAChapter> getATAChapterList() {
        return ATAChapterList;
    }

    //视图传输
    public List<NoStandardATAChapter> getCopyATAChapterList() {
        if(null != ATAChapterList){
            for(NoStandardATAChapter ataChapter : ATAChapterList){
                ataChapter.setId(ataChapter.getAtaId());
            }
        }
        return ATAChapterList;
    }

    public void setATAChapterList(List<NoStandardATAChapter> ATAChapterList) {
        this.ATAChapterList = ATAChapterList;
    }

    public String getTestPurpose() {
        return testPurpose;
    }

    public void setTestPurpose(String testPurpose) {
        this.testPurpose = testPurpose;
    }

    public String getTestInformation() {
        return testInformation;
    }

    public void setTestInformation(String testInformation) {
        this.testInformation = testInformation;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public User getLabManager() {
        return labManager;
    }

    public void setLabManager(User labManager) {
        this.labManager = labManager;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getAcceptDate() {
        return acceptDate;
    }

    public void setAcceptDate(Date acceptDate) {
        this.acceptDate = acceptDate;
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

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

    public List<NoStandardTestItem> getTestItemList() {
        if(null != testItemList){
            testItemList.sort((e1,e2)->Integer.parseInt(e1.getSort())-Integer.parseInt(e2.getSort()));
        }
        return testItemList;
    }

    public void setTestItemList(List<NoStandardTestItem> testItemList) {
        this.testItemList = testItemList;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getTestManager() {
        return testManager;
    }

    public NoStandardEntrustInfo setTestManager(String testManager) {
        this.testManager = testManager;
        return this;
    }

    public String getTestUsers() {
        return testUsers;
    }

    public NoStandardEntrustInfo setTestUsers(String testUsers) {
        this.testUsers = testUsers;
        return this;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public NoStandardEntrustInfo setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
        return this;
    }

    public String getEntrustName() {
        return entrustName;
    }

    public NoStandardEntrustInfo setEntrustName(String entrustName) {
        this.entrustName = entrustName;
        return this;
    }

    public boolean getAuditFlag(){
        if(StringUtils.isBlank(curAuditUser)){
            return false;
        }
        User user = UserUtils.getUser();
        if(user.getId().equals(curAuditUser)){
            return true;
        }else {
            return false;
        }
    }

    public String getCurAuditUser() {
        return curAuditUser;
    }

    public NoStandardEntrustInfo setCurAuditUser(String curAuditUser) {
        this.curAuditUser = curAuditUser;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public NoStandardEntrustInfo setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public NoStandardEntrustResource getResource() {
        return resource;
    }

    public NoStandardEntrustInfo setResource(NoStandardEntrustResource resource) {
        this.resource = resource;
        return this;
    }

    public String getExecutionId() {
        return executionId;
    }

    public NoStandardEntrustInfo setExecutionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public NoStandardExecution getExecution() {
        return execution;
    }

    public NoStandardEntrustInfo setExecution(NoStandardExecution execution) {
        this.execution = execution;
        return this;
    }

    public String getReportId() {
        return reportId;
    }

    public NoStandardEntrustInfo setReportId(String reportId) {
        this.reportId = reportId;
        return this;
    }

    public NoStandardEntrustReport getReport() {
        return report;
    }

    public NoStandardEntrustInfo setReport(NoStandardEntrustReport report) {
        this.report = report;
        return this;
    }

    public String getAtaChapterFullName() {
        return ataChapterFullName;
    }

    public NoStandardEntrustInfo setAtaChapterFullName(String ataChapterFullName) {
        this.ataChapterFullName = ataChapterFullName;
        return this;
    }
}
