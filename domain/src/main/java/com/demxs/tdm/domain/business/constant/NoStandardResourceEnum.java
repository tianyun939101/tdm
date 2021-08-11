package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 09:41
 * @Description:
 */
public enum NoStandardResourceEnum {

    RESOURCE("0","资源分配"),
    INSPECT("1","试验前检查中"),
    AUDIT_PASS("2","审核通过，待审批"),
    AUDIT_FAILED("3","审核不通过"),
    STOPPED("4","终止"),
    APPROVAL("5","已批准");

    private String code;
    private String title;

    NoStandardResourceEnum(String code,String title){
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardResourceEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardResourceEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
