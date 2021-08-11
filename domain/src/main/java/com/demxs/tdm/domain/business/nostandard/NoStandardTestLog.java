package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.excel.anno.ExcelField;
import com.demxs.tdm.domain.business.AuditInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;


/**
 * 非标试验日志
 */
public class NoStandardTestLog extends DataEntity<NoStandardTestLog> {

    private static final long serialVersionUID = 1L;

    private String executionId;
    //日志编号
    @ExcelField(title = "日志编号",sort = 1)
    private String code;
    //时间
    @ExcelField(title = "填写时间",sort = 2)
    private Date date;
    //地点
    @ExcelField(title = "地点",sort = 3)
    private String position;
    //大纲编号
    @ExcelField(title = "大纲编号",sort = 4)
    private String outlineCode;
    //试验名称
    @ExcelField(title = "试验名称",sort = 5)
    private String testName;
    //试验负责人
    //@ExcelField(title = "试验负责人",sort = 6)
    private String testManager;
    //试验记录
    @ExcelField(title = "试验记录",sort = 8)
    private String record;
    //状态
    private String status;
    //上传附件
    private String file;
    //填报人
    @ExcelField(title = "填写人",sort = 0)
    private String commitUser;
    //资源分配id
    private String resourceId;
    //其他人员
    @ExcelField(title = "其他人员",sort = 7)
    private String otherUser;
    //审核信息
    private List<AuditInfo> auditInfoList;


    //存在问题
    private String exProblem;
    //问题处置说明
    private String proStat;


    //视图传递对象
    private AuditInfo auditInfo;

    public NoStandardTestLog(){
        super();
    }

    public NoStandardTestLog(String id){
        super(id);
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getOutlineCode() {
        return outlineCode;
    }

    public void setOutlineCode(String outlineCode) {
        this.outlineCode = outlineCode;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestManager() {
        return testManager;
    }

    public void setTestManager(String testManager) {
        this.testManager = testManager;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFile() {
        return file;
    }

    public NoStandardTestLog setFile(String file) {
        this.file = file;
        return this;
    }

    public String getCommitUser() {
        return commitUser;
    }

    public NoStandardTestLog setCommitUser(String commitUser) {
        this.commitUser = commitUser;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public NoStandardTestLog setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public NoStandardTestLog setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
        return this;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public NoStandardTestLog setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public NoStandardTestLog setOtherUser(String otherUser) {
        this.otherUser = otherUser;
        return this;
    }

    public String getExProblem() {
        return exProblem;
    }

    public void setExProblem(String exProblem) {
        this.exProblem = exProblem;
    }

    public String getProStat() {
        return proStat;
    }

    public void setProStat(String proStat) {
        this.proStat = proStat;
    }
}
