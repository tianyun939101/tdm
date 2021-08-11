package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.ability.constant.TestCategoryAssessRequestEnum;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/9/23 09:52
 * @Description: 评估申请单实体
 */
public class TestCategoryAssessRequest extends DataEntity<TestCategoryAssessRequest> {

    private static final long serialVersionUID = 1427L;
    /**
     * 申请试验室
     */
    private String labId;
    private LabInfo labInfo;

    private LabInfo labInfoSave;
    /**
     * 版本
     */
    private String vId;
    private TestCategoryVersion version;
    /**
     * 状态（0：编制，1：实验室审核，2：单位审核，3：单位审批，4：公司验证中心审核，5：公司验证中心审批，6：已批准，7：审核驳回）
     */
    private String status;
    private String filterStatus;
    /**
     * 当前审批人
     */
    private String auditUserId;
    private User auditUser;

    //实验室部长审批
    private String labMin;
    private User labMinUser;

    //科技部主管审批
    private String tecDir;
    private User tecDirUser;

    //科技部部长审批
    private String tecMin;
    private User tecMinUser;

    //流程发起人
    private String initiator;
    private User initiatorUser;

    private String subCenter;

    /**
     * 审核记录
     */
    private List<AuditInfo> auditInfoList;
    private AuditInfo auditInfo;

    public LabInfo getLabInfoSave() {
        return labInfoSave;
    }

    public void setLabInfoSave(LabInfo labInfoSave) {
        this.labInfoSave = labInfoSave;
    }

    public TestCategoryAssessRequest() {
    }

    public TestCategoryAssessRequest(String id) {
        super(id);
    }

    public String getLabId() {
        return labId;
    }

    public TestCategoryAssessRequest setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategoryAssessRequest setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryAssessRequest setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public TestCategoryVersion getVersion() {
        return version;
    }

    public TestCategoryAssessRequest setVersion(TestCategoryVersion version) {
        this.version = version;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TestCategoryAssessRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public TestCategoryAssessRequest setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
        return this;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public TestCategoryAssessRequest setAuditUser(User auditUser) {
        this.auditUser = auditUser;
        return this;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public TestCategoryAssessRequest setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
        return this;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public TestCategoryAssessRequest setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public boolean getAuditFlag(){
        return UserUtils.getUser().getId().equals(this.auditUserId);
    }

    public boolean getEditFlag(){
        if(TestCategoryAssessRequestEnum.EDIT.getCode().equals(this.status)){
            return true;
        }else{
            if(null != this.updateBy){
                return UserUtils.getUser().getId().equals(this.updateBy.getId());
            }else{
                return false;
            }
        }
    }

    public String getFilterStatus() {
        return filterStatus;
    }

    public TestCategoryAssessRequest setFilterStatus(String filterStatus) {
        this.filterStatus = filterStatus;
        return this;
    }

    @JsonProperty("isEditing")
    public boolean isEditing(){
        return TestCategoryAssessRequestEnum.EDIT.getCode().equals(this.status);
    }

    @JsonProperty("isLabAudit")
    public boolean isLabAudit(){
        return TestCategoryAssessRequestEnum.LAB_AUDIT.getCode().equals(this.status);
    }

    @JsonProperty("isCompanyAudit")
    public boolean isCompanyAudit(){
        return TestCategoryAssessRequestEnum.COMPANY_AUDIT.getCode().equals(this.status);
    }

    @JsonProperty("isCompanyApproval")
    public boolean isCompanyApproval(){
        return TestCategoryAssessRequestEnum.COMPANY_APPROVAL.getCode().equals(this.status);
    }

    @JsonProperty("isCenterAudit")
    public boolean isCenterAudit(){
        return TestCategoryAssessRequestEnum.CENTER_AUDIT.getCode().equals(this.status);
    }

    @JsonProperty("isCenterApproval")
    public boolean isCenterApproval(){
        return TestCategoryAssessRequestEnum.CENTER_APPROVAL.getCode().equals(this.status);
    }

    @JsonProperty("isApproved")
    public boolean isApproved(){
        return TestCategoryAssessRequestEnum.APPROVED.getCode().equals(this.status);
    }

    @JsonProperty("isReject")
    public boolean isReject(){
        return TestCategoryAssessRequestEnum.REJECT.getCode().equals(this.status);
    }


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

    public String getTecDir() {
        return tecDir;
    }

    public void setTecDir(String tecDir) {
        this.tecDir = tecDir;
    }

    public User getTecDirUser() {
        return tecDirUser;
    }

    public void setTecDirUser(User tecDirUser) {
        this.tecDirUser = tecDirUser;
    }

    public String getTecMin() {
        return tecMin;
    }

    public void setTecMin(String tecMin) {
        this.tecMin = tecMin;
    }

    public User getTecMinUser() {
        return tecMinUser;
    }

    public void setTecMinUser(User tecMinUser) {
        this.tecMinUser = tecMinUser;
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
}
