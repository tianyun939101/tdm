package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/10 12:22
 * @Description:
 */
public enum NoStandardReportEnum {

    EDIT("1","编制报告"),
    TO_BE_AUDITED("2","待审核"),
    REJECT("4","驳回"),
    TO_BE_APPROVED("3","待批准"),
    APPROVED("5","已批准"),
    ;

    private String code;
    private String title;

    NoStandardReportEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardReportEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardReportEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
