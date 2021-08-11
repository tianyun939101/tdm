package com.demxs.tdm.domain.business.constant;

/**
 * @author wuhui
 * @date 2020/9/27 10:45
 **/
public enum ApproveStatus {
    //未提交
    UNSUBMIT("01","未提交"),
    //审批中
    APPROVAL("02","审批中"),
    //已通过
    AGREE("03","已通过"),
    //已驳回
    REJEDTED("04","已驳回"),
    ;

    private String code;
    private String label;

    private ApproveStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
