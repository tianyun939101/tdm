package com.demxs.tdm.common.dto;

/**
 * @author wuhui
 * @date 2020/9/27 9:59
 **/
public class BaseInfoApplyDTO {
    private String id; //编号
    private String baseId;//数据信息编号
    private String opinion;//审批意见
    private boolean agree;//同意
    private String labId;//试验数编号
    private String applicantName;//申请人姓名
    private String approveStatus;//申请状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public boolean isAgree() {
        return agree;
    }

    public void setAgree(boolean agree) {
        this.agree = agree;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }
}
