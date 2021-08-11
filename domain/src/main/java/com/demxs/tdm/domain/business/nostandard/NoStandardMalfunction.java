package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.ATAChapter;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 试验故障实体类
 */
public class NoStandardMalfunction extends DataEntity<NoStandardMalfunction> {
    /**
     *唯一编号
     */
    private String taskId;
    /**
     * 任务编号
     */
    private String taskNo;
    /**
     *报告人工号
     */
    private String jobNo;
    private String jobId;
    private User user;
    /**
     *报告机构编号
     */
    private String organizationNum;
    private String organizationName;
    /**
     *报告时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date reportDate;
    /**
     *飞机型号
     */
    private String airModel;
    /**
     *飞机版本号
     */
    private String airVersion;
    /**
     *问题标题
     */
    private String matterTittle;
    /**
     *发生时间
     */
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    @JsonFormat(pattern = "yyyy-mm-dd")
    private Date happenDate;
    /**
     *发生地点
     */
    private String happenPlace;
    /**
     *执行任务
     */
    private String executeTask;
    /**
     *问题描述
     */
    private String matterDes;
    /**
     *ATA章节
     */
    private ATAChapter ATANum;
    private List<ATAChapter> ATAList;
    private String ata;
    /**
     *应急措施
     */
    private String emerMeasure;
    /**
     *处理措施依据
     */
    private String handleGist;
    /**
     *历史关联编号
     */
    private String hisRelevance;
    /**
     *问题附件
     */
    private String file;

    /**
     *
     *状态
     */
    private String status;
    /**
     * 当前审批人
     */
    private String auditUserId;
    private User auditUser;
    /**
     * 下一节点审批人
     */
    private String authorized;
    private User authorizedMinUser;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getJobId() {
        return jobId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getOrganizationNum() {
        return organizationNum;
    }

    public void setOrganizationNum(String organizationNum) {
        this.organizationNum = organizationNum;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getAirModel() {
        return airModel;
    }

    public void setAirModel(String airModel) {
        this.airModel = airModel;
    }

    public String getAirVersion() {
        return airVersion;
    }

    public void setAirVersion(String airVersion) {
        this.airVersion = airVersion;
    }

    public String getMatterTittle() {
        return matterTittle;
    }

    public void setMatterTittle(String matterTittle) {
        this.matterTittle = matterTittle;
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public String getHappenPlace() {
        return happenPlace;
    }

    public void setHappenPlace(String happenPlace) {
        this.happenPlace = happenPlace;
    }

    public String getExecuteTask() {
        return executeTask;
    }

    public void setExecuteTask(String executeTask) {
        this.executeTask = executeTask;
    }

    public String getMatterDes() {
        return matterDes;
    }

    public void setMatterDes(String matterDes) {
        this.matterDes = matterDes;
    }

    public ATAChapter getATANum() {
        return ATANum;
    }

    public void setATANum(ATAChapter ATANum) {
        this.ATANum = ATANum;
    }

    public String getAta() {
        return ata;
    }

    public void setAta(String ata) {
        this.ata = ata;
    }

    public String getEmerMeasure() {
        return emerMeasure;
    }

    public void setEmerMeasure(String emerMeasure) {
        this.emerMeasure = emerMeasure;
    }

    public String getHandleGist() {
        return handleGist;
    }

    public void setHandleGist(String handleGist) {
        this.handleGist = handleGist;
    }

    public String getHisRelevance() {
        return hisRelevance;
    }

    public void setHisRelevance(String hisRelevance) {
        this.hisRelevance = hisRelevance;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getStatus() {
        return status;
    }

    public List<ATAChapter> getATAList() {
        return ATAList;
    }

    public void setATAList(List<ATAChapter> ATAList) {
        this.ATAList = ATAList;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getAuditUserId() {
        return auditUserId;
    }

    public void setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public String getAuthorized() {
        return authorized;
    }

    public void setAuthorized(String authorized) {
        this.authorized = authorized;
    }

    public User getAuthorizedMinUser() {
        return authorizedMinUser;
    }

    public void setAuthorizedMinUser(User authorizedMinUser) {
        this.authorizedMinUser = authorizedMinUser;
    }
}
