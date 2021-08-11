package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/6 22:47
 * @Description:
 */
public enum NoStandardTestLogEnum {

    NOT_SUBMITTED("0","未提交"),
    AUDITING("1","审核中"),
    AUDIT_PASS("2","审核通过"),
    AUDIT_FAILED("3","审核不通过");

    private String code;
    private String title;

    NoStandardTestLogEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardTestLogEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardTestLogEnum setTitle(String title) {
        this.title = title;
        return this;
    }
}
