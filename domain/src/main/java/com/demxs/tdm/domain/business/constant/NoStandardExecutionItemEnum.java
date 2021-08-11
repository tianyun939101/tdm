package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/6 15:10
 * @Description:
 */
public enum NoStandardExecutionItemEnum {

    UN_AUDITED("0","未审核"),
    AUDIT_PASS("1","审核通过"),
    AUDIT_FAILED("2","审核不通过"),
    IN_AUDIT("3","审核中"),
    ;

    private String code;
    private String title;

    NoStandardExecutionItemEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardExecutionItemEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardExecutionItemEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
