package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.AuditInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ZyProjectManager extends DataEntity<ZyProjectManager> {

    private static final long serialVersionUID = 1L;

    private String managerName;//名称

    private String managerCode;//编号

    private String managerType;//类型


    private String treeId;//编号

    private String fileId;//编号

    private String approvalId;//审核人

    private ZyProjectTree  zyProjectTree;

    private User auditUser;//审核人

    private List<AuditInfo> auditInfoList;

    private String status;

    private AuditInfo auditInfo;

    private String flag;

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ZyProjectTree getZyProjectTree() {
        return zyProjectTree;
    }

    public void setZyProjectTree(ZyProjectTree zyProjectTree) {
        this.zyProjectTree = zyProjectTree;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerCode() {
        return managerCode;
    }

    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }

    public String getManagerType() {
        return managerType;
    }

    public void setManagerType(String managerType) {
        this.managerType = managerType;
    }

    public String getTreeId() {
        return treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
}
