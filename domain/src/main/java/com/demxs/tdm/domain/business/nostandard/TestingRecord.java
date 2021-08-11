package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.List;

public class TestingRecord extends DataEntity<TestingRecord> {
    /**
     * 基础表信息
     */
    private String basicTable;
    /**
     * 记录人
     */
    private String notekeeperId;
    private User notekeeper;

    /**
     *批准人
     */
    private String applyUserId;
    private User applyUser;
    /**
     *调试内容
     */
    private String debuggContent;
    /**
     *存在问题
     */
    private String problom;
    /**
     *调试结论
     */
    private String conclusion;
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

    public String getNotekeeperId() {
        return notekeeperId;
    }

    public void setNotekeeperId(String notekeeperId) {
        this.notekeeperId = notekeeperId;
    }

    public User getNotekeeper() {
        return notekeeper;
    }

    public void setNotekeeper(User notekeeper) {
        this.notekeeper = notekeeper;
    }

    public String getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(String applyUserId) {
        this.applyUserId = applyUserId;
    }

    public User getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(User applyUser) {
        this.applyUser = applyUser;
    }

    public String getDebuggContent() {
        return debuggContent;
    }

    public void setDebuggContent(String debuggContent) {
        this.debuggContent = debuggContent;
    }

    public String getProblom() {
        return problom;
    }

    public void setProblom(String problom) {
        this.problom = problom;
    }

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
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
