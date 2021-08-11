package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ZyTestTask extends DataEntity<ZyTestTask> {

    private static final long serialVersionUID = 1L;

    private String testName;

    private String configNo;

    private String testContext;

    private String testEquip;

    private String softNo;

    private String change;

    private String description;

    private String approvalUser;

    private String preUser;

    private String status;

    private User user;

    private String flag;

    //所属实验室ID
    private String labId;
    //所属实验室
    private LabInfo labInfo;

    private String subCenter;

    private String labInfoName;

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

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    @Override
    public String getLabInfoName() {
        return labInfoName;
    }

    @Override
    public void setLabInfoName(String labInfoName) {
        this.labInfoName = labInfoName;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    private List<AuditInfo> auditList;//审批历史

    public List<AuditInfo> getAuditList() {
        return auditList;
    }

    public void setAuditList(List<AuditInfo> auditList) {
        this.auditList = auditList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getConfigNo() {
        return configNo;
    }

    public void setConfigNo(String configNo) {
        this.configNo = configNo;
    }

    public String getTestContext() {
        return testContext;
    }

    public void setTestContext(String testContext) {
        this.testContext = testContext;
    }

    public String getTestEquip() {
        return testEquip;
    }

    public void setTestEquip(String testEquip) {
        this.testEquip = testEquip;
    }

    public String getSoftNo() {
        return softNo;
    }

    public void setSoftNo(String softNo) {
        this.softNo = softNo;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApprovalUser() {
        return approvalUser;
    }

    public void setApprovalUser(String approvalUser) {
        this.approvalUser = approvalUser;
    }

    public String getPreUser() {
        return preUser;
    }

    public void setPreUser(String preUser) {
        this.preUser = preUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
