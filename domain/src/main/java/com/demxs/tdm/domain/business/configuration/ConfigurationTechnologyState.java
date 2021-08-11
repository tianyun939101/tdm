package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.List;

public class ConfigurationTechnologyState extends DataEntity<ConfigurationTechnologyState> {

    /**
     * 设备名称
     */
    private String shebeimc;
    /**
     *现软件版本号
     */
    private String nowVersion;
    private SoftwareLibrary nowSoft;
    /**
     *软件技术状态
     */
    private String stateStatus;
    /**
     * 状态（01：编制，02：校对，03：批准 ，04：已批准，05：审核驳回）
     */
    private String status;
    private String filterStatus;
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
    /**
     * 申请人
     */
    private String proofread;
    private User proofreadUser;
    /**
     * 审核记录
     */
    private List<AuditInfo> auditInfoList;
    private AuditInfo auditInfo;

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

    public boolean getAuditFlag(){
        if(this.authorizedMinUser != null){
            return UserUtils.getUser().getId().equals(this.authorizedMinUser.getId());
        }
        return false;
    }
    public String getShebeimc() {
        return shebeimc;
    }

    public void setShebeimc(String shebeimc) {
        this.shebeimc = shebeimc;
    }

    public String getNowVersion() {
        return nowVersion;
    }

    public void setNowVersion(String nowVersion) {
        this.nowVersion = nowVersion;
    }

    public SoftwareLibrary getNowSoft() {
        return nowSoft;
    }

    public void setNowSoft(SoftwareLibrary nowSoft) {
        this.nowSoft = nowSoft;
    }

    public String getStateStatus() {
        return stateStatus;
    }

    public void setStateStatus(String stateStatus) {
        this.stateStatus = stateStatus;
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

    public String getProofread() {
        return proofread;
    }

    public void setProofread(String proofread) {
        this.proofread = proofread;
    }

    public User getProofreadUser() {
        return proofreadUser;
    }

    public void setProofreadUser(User proofreadUser) {
        this.proofreadUser = proofreadUser;
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
