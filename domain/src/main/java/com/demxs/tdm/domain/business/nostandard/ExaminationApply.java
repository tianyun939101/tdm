package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.List;

/**
 * 试验审批表
 * */
public class ExaminationApply extends DataEntity<ExaminationApply> {
    /**
     * 基础表信息
     */
    private String basicTable;
    /**
     *申请部门/团队
     */
    private String applyDeptId;
    private Office applyDept;
    /**
     *试验大纲编号
     */
    private String testOutlineId;
    /**
     *试验内容
     */
    private String testText;
    /**
     *部门领导/二级IPT经理
     */
    private String deptHeadUserId;
    private User deptHeadUser;
    /**
     *团队/部门质量主管
     */
    private String teamHeadUserId;
    private User teamHeadUser;
    /**
     *批准
     */
    private String applyBasicUserId;
    private User applyBasicUser;
    /**
     * 审批状态
     */
    private String auditType;
    //审核信息
    private List<AuditInfo> auditInfoList;


    public String getBasicTable() {
        return basicTable;
    }

    public void setBasicTable(String basicTable) {
        this.basicTable = basicTable;
    }

    public String getApplyDeptId() {
        return applyDeptId;
    }

    public void setApplyDeptId(String applyDeptId) {
        this.applyDeptId = applyDeptId;
    }

    public Office getApplyDept() {
        return applyDept;
    }

    public void setApplyDept(Office applyDept) {
        this.applyDept = applyDept;
    }


    public String getTestOutlineId() {
        return testOutlineId;
    }

    public void setTestOutlineId(String testOutlineId) {
        this.testOutlineId = testOutlineId;
    }


    public String getTestText() {
        return testText;
    }

    public void setTestText(String testText) {
        this.testText = testText;
    }


    public String getDeptHeadUserId() {
        return deptHeadUserId;
    }

    public void setDeptHeadUserId(String deptHeadUserId) {
        this.deptHeadUserId = deptHeadUserId;
    }

    public User getDeptHeadUser() {
        return deptHeadUser;
    }

    public void setDeptHeadUser(User deptHeadUser) {
        this.deptHeadUser = deptHeadUser;
    }

    public String getTeamHeadUserId() {
        return teamHeadUserId;
    }

    public void setTeamHeadUserId(String teamHeadUserId) {
        this.teamHeadUserId = teamHeadUserId;
    }

    public User getTeamHeadUser() {
        return teamHeadUser;
    }

    public void setTeamHeadUser(User teamHeadUser) {
        this.teamHeadUser = teamHeadUser;
    }

    public String getApplyBasicUserId() {
        return applyBasicUserId;
    }

    public void setApplyBasicUserId(String applyBasicUserId) {
        this.applyBasicUserId = applyBasicUserId;
    }

    public User getApplyBasicUser() {
        return applyBasicUser;
    }

    public void setApplyBasicUser(User applyBasicUser) {
        this.applyBasicUser = applyBasicUser;
    }

    public String getAuditType() {
        return auditType;
    }

    public void setAuditType(String auditType) {
        this.auditType = auditType;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

}
