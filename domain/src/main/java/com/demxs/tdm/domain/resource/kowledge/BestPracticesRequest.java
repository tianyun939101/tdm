package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.ability.TestCategoryVersion;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.List;

public class BestPracticesRequest extends DataEntity<BestPracticesRequest> {
    private static final long serialVersionUID = 1427L;
    /**
     * 申请试验室
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 版本
     */
    private String vId;
    private TestCategoryVersion version;
    /**
     * 状态（0：驳回 1:通过）
     */
    private String status;
    private String filterStatus;

    /**
     * 指定审批人
     */
    private String labMin;
    private User labMinUser;
    /**
     * 当前审批人
     */
    private String auditUserId;
    private User auditUser;

    //流程发起人
    private String initiator;
    private User initiatorUser;

    private String subCenter;


    /**
     * 审核记录
     */
    private List<AuditInfo> auditInfoList;
    private AuditInfo auditInfo;

    public String getLabMin() {
        return labMin;
    }

    public void setLabMin(String labMin) {
        this.labMin = labMin;
    }

    public User getLabMinUser() {
        return labMinUser;
    }

    public void setLabMinUser(User labMinUser) {
        this.labMinUser = labMinUser;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public TestCategoryVersion getVersion() {
        return version;
    }

    public void setVersion(TestCategoryVersion version) {
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public void setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
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

    public String getInitiator() {
        return initiator;
    }

    public void setInitiator(String initiator) {
        this.initiator = initiator;
    }

    public User getInitiatorUser() {
        return initiatorUser;
    }

    public void setInitiatorUser(User initiatorUser) {
        this.initiatorUser = initiatorUser;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public void setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public void setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
    }
}
