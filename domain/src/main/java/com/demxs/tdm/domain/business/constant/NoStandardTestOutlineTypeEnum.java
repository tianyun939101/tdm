package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/9 10:08
 * @Description:
 */
public enum NoStandardTestOutlineTypeEnum {

    UPLOAD_FILE("0","上传"),
    INPUT_CODE("1","输入大纲编号"),
    SYS("2","选择系统大纲");

    private String code;
    private String title;

    NoStandardTestOutlineTypeEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardTestOutlineTypeEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardTestOutlineTypeEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
