package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.constant.TestCategoryModifyRequestStatus;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.SubCenter;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/3 18:05
 * @Description: 变更申请
 */
public class TestCategoryModifyRequest extends DataEntity<TestCategoryModifyRequest> {

    private static final long serialVersionUID = 1L;
    /**
     * 版本id
     */
    private String vId;
    private TestCategoryVersion version;
    /**
     * 试验室id
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 所属单位
     */
    private String  affiliatedOfficeId;
    private SubCenter affiliatedOffice;
    /**
     * 修改类别（0：增加试验项，1：删除试验项，2：试验名称，3：试验层级，4：试验分类）
     */
    private String modifyType;
    /**
     * 变更原因
     */
    private String reason;
    /**
     * 变更记录
     */
    private List<TestCategoryModifyRecord> recordList;
    /**
     * 当前审核人
     */
    private String auditUserId;
    private User auditUser;
    /**
     * 会签人员
     */
    private String coSignerIds;
    private List<User> coSignerUserList;
    /**
     * 已会签人员
     */
    private String signed;
    /**
     * 状态（0：编制，1：待审核，2：已批准，3：审核驳回，4：待批准，5：会签，6：待审批）
     */
    private String status;
    /**
     * 有效性（0：无效，1：有效）
     */
    private String effectiveness;
    public static final String INVALID = "0";
    public static final String EFFECTIVE = "1";
    /**
     * 应用生效的版本
     */
    private String effectVersionId;
    private TestCategoryVersion effectVersion;
    /**
     * 审核记录
     */
    private List<AuditInfo> auditInfoList;
    private AuditInfo auditInfo;

    public TestCategoryModifyRequest() {
    }

    public TestCategoryModifyRequest(String id) {
        super(id);
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryModifyRequest setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public TestCategoryVersion getVersion() {
        return version;
    }

    public TestCategoryModifyRequest setVersion(TestCategoryVersion version) {
        this.version = version;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestCategoryModifyRequest setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategoryModifyRequest setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public SubCenter getAffiliatedOffice() {
        return affiliatedOffice;
    }

    public TestCategoryModifyRequest setAffiliatedOffice(SubCenter affiliatedOffice) {
        this.affiliatedOffice = affiliatedOffice;
        return this;
    }

    public String getModifyType() {
        return modifyType;
    }

    public TestCategoryModifyRequest setModifyType(String modifyType) {
        this.modifyType = modifyType;
        return this;
    }

    public String getReason() {
        return reason;
    }

    public TestCategoryModifyRequest setReason(String reason) {
        this.reason = reason;
        return this;
    }

    public List<TestCategoryModifyRecord> getRecordList() {
        return recordList;
    }

    public TestCategoryModifyRequest setRecordList(List<TestCategoryModifyRecord> recordList) {
        this.recordList = recordList;
        return this;
    }

    public String getAuditUserId() {
        return auditUserId;
    }

    public TestCategoryModifyRequest setAuditUserId(String auditUserId) {
        this.auditUserId = auditUserId;
        return this;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public TestCategoryModifyRequest setAuditUser(User auditUser) {
        this.auditUser = auditUser;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TestCategoryModifyRequest setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getAffiliatedOfficeId() {
        return affiliatedOfficeId;
    }

    public TestCategoryModifyRequest setAffiliatedOfficeId(String affiliatedOfficeId) {
        this.affiliatedOfficeId = affiliatedOfficeId;
        return this;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public TestCategoryModifyRequest setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
        return this;
    }

    public boolean isNotSubmitted(){
        return null == this.status || TestCategoryModifyRequestStatus.NOT_SUBMITTED.getCode().equals(this.status);
    }

    public boolean isUnderReview(){
        return TestCategoryModifyRequestStatus.UNDER_REVIEW.getCode().equals(this.status);
    }

    public boolean isUnderApproved(){
        return TestCategoryModifyRequestStatus.UNDER_APPROVED.getCode().equals(this.status);
    }

    public boolean isApproved(){
        return TestCategoryModifyRequestStatus.APPROVED.getCode().equals(this.status);
    }

    public boolean isRejected(){
        return TestCategoryModifyRequestStatus.REJECTED.getCode().equals(this.status);
    }

    public boolean isJointlySign(){
        return TestCategoryModifyRequestStatus.JOINTLY_SIGN.getCode().equals(this.status);
    }

    public boolean isExamination(){
        return TestCategoryModifyRequestStatus.EXAMINATION.getCode().equals(this.status);
    }

    public boolean isCenterApproved(){
        return TestCategoryModifyRequestStatus.CENTER_APPROVED.getCode().equals(this.status);
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public TestCategoryModifyRequest setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public boolean getAuditFlag(){
        if(TestCategoryModifyRequestStatus.JOINTLY_SIGN.getCode().equals(this.getStatus())){
            if(this.getCoSignerIds() != null && this.getCoSignerIds().contains(UserUtils.getUser().getId())){
                //从已会签人员中过滤掉
                return this.signed == null || !this.signed.contains(UserUtils.getUser().getId());
            }
        }
        return UserUtils.getUser().getId().equals(this.auditUserId);
    }

    public boolean getEditFlag(){
        if(UserUtils.getUser().isAdmin()){
            return true;
        }
        if(null != this.createBy){
            return UserUtils.getUser().getId().equals(this.createBy.getId());
        }
        return false;
    }

    @Override
    public void preInsert() {
        //设置有效性
        this.setEffectiveness(EFFECTIVE);
        super.preInsert();
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public TestCategoryModifyRequest setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }

    public String getEffectVersionId() {
        return effectVersionId;
    }

    public TestCategoryModifyRequest setEffectVersionId(String effectVersionId) {
        this.effectVersionId = effectVersionId;
        return this;
    }

    public TestCategoryVersion getEffectVersion() {
        return effectVersion;
    }

    public TestCategoryModifyRequest setEffectVersion(TestCategoryVersion effectVersion) {
        this.effectVersion = effectVersion;
        return this;
    }

    public String getCoSignerIds() {
        return coSignerIds;
    }

    public TestCategoryModifyRequest setCoSignerIds(String coSignerIds) {
        this.coSignerIds = coSignerIds;
        return this;
    }

    public List<User> getCoSignerUserList() {
        return coSignerUserList;
    }

    public String getCoSignerUsername() {
        if(null != this.coSignerUserList && !this.coSignerUserList.isEmpty()){
            StringBuilder s = new StringBuilder();
            for (User user : coSignerUserList) {
                if(null != user && null != user.getName()){
                    s.append(user.getName()).append(",");
                }
            }
            if(s.length() > 0){
                s.deleteCharAt(s.length() - 1);
            }
            return s.toString();
        }
        return null;
    }

    public TestCategoryModifyRequest setCoSignerUserList(List<User> coSignerUserList) {
        this.coSignerUserList = coSignerUserList;
        return this;
    }

    public String getSigned() {
        return signed;
    }

    public TestCategoryModifyRequest setSigned(String signed) {
        this.signed = signed;
        return this;
    }

    /**
    * @author Jason
    * @date 2020/9/8 13:36
    * @params []
    * 获得当前处理人姓名
    * @return java.lang.String
    */
    public String getCurOpUserName(){
        if(null != this.status){
            if(TestCategoryModifyRequestStatus.JOINTLY_SIGN.getCode().equals(this.status)){
                StringBuilder s = new StringBuilder();
                if(StringUtils.isNoneBlank(this.coSignerIds,this.signed)){
                    String[] split = coSignerIds.split(",");
                    for (String id : split) {
                        if(!this.signed.contains(id)){
                            User u = UserUtils.get(id);
                            if(null == u){
                                continue;
                            }
                            s.append(u.getName()).append(",");
                        }
                    }
                    return s.length() > 0 ? s.deleteCharAt(s.length() - 1 ).toString() : null;
                }else if(StringUtils.isBlank(this.signed)){
                    String[] split = coSignerIds.split(",");
                    for (String id : split) {
                        User u = UserUtils.get(id);
                        if(null == u){
                            continue;
                        }
                        s.append(u.getName()).append(",");
                    }
                    return s.length() > 0 ? s.deleteCharAt(s.length() - 1 ).toString() : null;
                }else{
                    return auditUser == null ? null : auditUser.getName();
                }
            }else if(!TestCategoryModifyRequestStatus.APPROVED.getCode().equals(this.status)
                    && !TestCategoryModifyRequestStatus.NOT_SUBMITTED.getCode().equals(this.status)
                    && !TestCategoryModifyRequestStatus.REJECTED.getCode().equals(this.status)){
                return auditUser == null ? null : auditUser.getName();
            }
        }
        return null;
    }
}
