package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.ATAChapter;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.configuration.BaseConfiguration;
import com.demxs.tdm.domain.business.configuration.ConfigurationVO;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wuhui
 * @date 2020/12/9 14:55
 **/
public class NoStandardTestCheck extends DataEntity<NoStandardTestCheck> {
    private static final long serialVersionUID = 1L;

    private String code;//申请单号
    private String applicant;//申请人
    private User applicantUser;//
    private String org;//申请人单位
    private Office office;
    private String status;//审核状态：01:检查,02:负责人审批,03:数据提报,04:审批,05:批准
    private String testManagerId;//试验负责人
    private Yuangong testManager;
    private String testUserId;//试验执行人
    private String curAudItUser;//当前审批人
    private String curAudItUserName;//当前审批人
    private User auditer;
    private String labId;//所属试验室
    private LabInfo labInfo;
    private String data;//检查单
    private String testOutline;//试验大纲
    private String testOutlineCode;//试验大纲编号
    private String testOutlineVersion;//试验大纲版本
    private String configurationId;//构型编号
    private String configurationVersion;//构型编号
    private String testName;//试验项目名称
    private String testNature;//试验性质
    private String productModel;//产品型号
    private String taskNo;//任务书编号
    private String planNo;//计划编号
    private String ataChapter;//ATA章节
    private String modelId;//模型说明书
    private String testReport;//仿真试验调试报告
    private String testCheckReport;//仿真试验前检查表

    private String laboratory;//所属实验室
    @JsonProperty("ATAChapterList")
    private List<ATAChapter> ATAChapterList;
    private String reportId;//提报编号
    //是否提交
    private Boolean submit;

    private ConfigurationVO configuration;//构型
    //其他人员
    private List<NoStandardOtherUser> otherUsers;

    private List<AuditInfo> auditList;//审批历史

    //预览地址
    private String onlineViewPath;
    private String documentCodeCMOS;
    private String documentVersionCMOS;

    private String  taskVersion;

    private String  taskView;

    private String testView;

    private String documentCode;

    private String documentVersion;

    private String  state;

    //标准规范体系
    private String standard;
    private List<Standard> standardList;


    private String subCenter;

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getDocumentVersion() {
        return documentVersion;
    }

    public void setDocumentVersion(String documentVersion) {
        this.documentVersion = documentVersion;
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

    public String getTestView() {
        return testView;
    }

    public void setTestView(String testView) {
        this.testView = testView;
    }

    public String getOnlineViewPath() {
        return onlineViewPath;
    }

    public void setOnlineViewPath(String onlineViewPath) {
        this.onlineViewPath = onlineViewPath;
    }

    public String getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(String laboratory) {
        this.laboratory = laboratory;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getTestReport() {
        return testReport;
    }

    public void setTestReport(String testReport) {
        this.testReport = testReport;
    }

    public String getTestCheckReport() {
        return testCheckReport;
    }

    public void setTestCheckReport(String testCheckReport) {
        this.testCheckReport = testCheckReport;
    }

    public String getCurAudItUserName() {
        return curAudItUserName;
    }

    public void setCurAudItUserName(String curAudItUserName) {
        this.curAudItUserName = curAudItUserName;
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

    public User getApplicantUser() {
        return applicantUser;
    }

    public void setApplicantUser(User applicantUser) {
        this.applicantUser = applicantUser;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestManagerId() {
        return testManagerId;
    }

    public void setTestManagerId(String testManagerId) {
        this.testManagerId = testManagerId;
    }

    public void setTestManager(Yuangong testManager) {
        this.testManager = testManager;
    }


    public String getTestUserId() {
        return testUserId;
    }

    public void setTestUserId(String testUserId) {
        this.testUserId = testUserId;
    }

    public String getCurAudItUser() {
        return curAudItUser;
    }

    public void setCurAudItUser(String curAudItUser) {
        this.curAudItUser = curAudItUser;
    }

    public User getAuditer() {
        return auditer;
    }

    public void setAuditer(User auditer) {
        this.auditer = auditer;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTestOutline() {
        return testOutline;
    }

    public void setTestOutline(String testOutline) {
        this.testOutline = testOutline;
    }

    public String getTestOutlineCode() {
        return testOutlineCode;
    }

    public void setTestOutlineCode(String testOutlineCode) {
        this.testOutlineCode = testOutlineCode;
    }

    public String getTestOutlineVersion() {
        return testOutlineVersion;
    }

    public void setTestOutlineVersion(String testOutlineVersion) {
        this.testOutlineVersion = testOutlineVersion;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getConfigurationVersion() {
        return configurationVersion;
    }

    public void setConfigurationVersion(String configurationVersion) {
        this.configurationVersion = configurationVersion;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestNature() {
        return testNature;
    }

    public void setTestNature(String testNature) {
        this.testNature = testNature;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
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

    public String getAtaChapter() {
        return ataChapter;
    }

    public void setAtaChapter(String ataChapter) {
        this.ataChapter = ataChapter;
    }

    public List<ATAChapter> getATAChapterList() {
        if(ATAChapterList == null){
            this.ATAChapterList = new ArrayList<ATAChapter>();
        }
        return ATAChapterList;
    }

    public String getDocumentCodeCMOS() {
        return documentCodeCMOS;
    }

    public void setDocumentCodeCMOS(String documentCodeCMOS) {
        this.documentCodeCMOS = documentCodeCMOS;
    }

    public String getDocumentVersionCMOS() {
        return documentVersionCMOS;
    }

    public void setDocumentVersionCMOS(String documentVersionCMOS) {
        this.documentVersionCMOS = documentVersionCMOS;
    }

    public void setATAChapterList(List<ATAChapter> ATAChapterList) {
        this.ATAChapterList = ATAChapterList;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public ConfigurationVO getConfiguration() {
        return configuration;
    }

    public void setConfiguration(ConfigurationVO configuration) {
        this.configuration = configuration;
    }

    public Yuangong getTestManager() {
        return testManager;
    }

    public List<NoStandardOtherUser> getOtherUsers() {
        return otherUsers;
    }

    public void setOtherUsers(List<NoStandardOtherUser> otherUsers) {
        this.otherUsers = otherUsers;
    }

    public Boolean getSubmit() {
        if(submit == null){
            submit = false;
        }
        return submit;
    }

    public void setSubmit(Boolean submit) {
        this.submit = submit;
    }

    public List<AuditInfo> getAuditList() {
        return auditList;
    }

    public void setAuditList(List<AuditInfo> auditList) {
        this.auditList = auditList;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public List<Standard> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Standard> standardList) {
        this.standardList = standardList;
    }
}
