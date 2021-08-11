package com.demxs.tdm.common.dto;

/**
 * @author wuhui
 * @date 2020/9/27 9:59
 **/
public class ApproveDTO {
    private String id; //数据编号
    private String opinion;//审批意见
    private boolean agree;//同意
    private String labId;//试验数编号

    public ApproveDTO() {
    }

    public ApproveDTO(String id, String opinion, String nextUser) {
        this.id = id;
        this.opinion = opinion;
        this.nextUser = nextUser;
    }

    private String nextUser;//下一节点审批人

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNextUser() {
        return nextUser;
    }

    public void setNextUser(String nextUser) {
        this.nextUser = nextUser;
    }
}
