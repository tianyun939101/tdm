package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**实验前检查大纲*/
public class CheckOutline extends DataEntity<CheckOutline> {
    /**
     * 基础表信息
     */
    private String basicTable;
    /**
     *检查目的
     */
    private String examineGoal;
    /**
     *检查依据
     */
    private String  examineGist;
    /**
     *检查组长
     */
    private String examineLeaderId;
    private User examineLeader;
    /**
     *检查组成员
     */
    private String othersId;
    private List<User> examineOtherUsers;
    private String othersName;
    /**
     *参加部门
     */
    private String joinDeptId;
    private Office joinDept;
    /**
     *时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;
    /**
     *地点
     */
    private String space;
    /**
     *检查内容和要求
     */
    private String examineTextRequre;
    /**
     *检查程序
     */
    private String examineProc;
    /**
     *编制人
     */
    private String authorizedUserId;
    private User authorizedUser;
    /**
     *批准人
     */
    private String applyUserId;
    private User applyUser;
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

    public String getExamineGoal() {
        return examineGoal;
    }

    public void setExamineGoal(String examineGoal) {
        this.examineGoal = examineGoal;
    }

    public String getExamineGist() {
        return examineGist;
    }

    public void setExamineGist(String examineGist) {
        this.examineGist = examineGist;
    }

    public String getExamineLeaderId() {
        return examineLeaderId;
    }

    public void setExamineLeaderId(String examineLeaderId) {
        this.examineLeaderId = examineLeaderId;
    }

    public User getExamineLeader() {
        return examineLeader;
    }

    public void setExamineLeader(User examineLeader) {
        this.examineLeader = examineLeader;
    }

    public List<User> getExamineOtherUsers() {
        return examineOtherUsers;
    }

    public void setExamineOtherUsers(List<User> examineOtherUsers) {
        this.examineOtherUsers = examineOtherUsers;
    }

    public String getOthersName() {
        return othersName;
    }

    public void setOthersName(String othersName) {
        this.othersName = othersName;
    }

    public String getJoinDeptId() {
        return joinDeptId;
    }

    public void setJoinDeptId(String joinDeptId) {
        this.joinDeptId = joinDeptId;
    }

    public Office getJoinDept() {
        return joinDept;
    }

    public void setJoinDept(Office joinDept) {
        this.joinDept = joinDept;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public String getExamineTextRequre() {
        return examineTextRequre;
    }

    public void setExamineTextRequre(String examineTextRequre) {
        this.examineTextRequre = examineTextRequre;
    }

    public String getExamineProc() {
        return examineProc;
    }

    public void setExamineProc(String examineProc) {
        this.examineProc = examineProc;
    }

    public String getAuthorizedUserId() {
        return authorizedUserId;
    }

    public void setAuthorizedUserId(String authorizedUserId) {
        this.authorizedUserId = authorizedUserId;
    }

    public User getAuthorizedUser() {
        return authorizedUser;
    }

    public void setAuthorizedUser(User authorizedUser) {
        this.authorizedUser = authorizedUser;
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

    public String getOthersId() {
        return othersId;
    }

    public void setOthersId(String othersId) {
        this.othersId = othersId;
    }
}
