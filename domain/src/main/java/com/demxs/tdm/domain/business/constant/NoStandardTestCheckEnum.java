package com.demxs.tdm.domain.business.constant;


import javax.swing.plaf.IconUIResource;

public enum NoStandardTestCheckEnum {

    CHECK("01","检查"),
    MANAGER_AUDIT("02","负责人审批"),
    REPORT("03","数据提报"),
    AUDIT("04","审批"),
    MANAGER_CHECK("06","负责人批准"),
    APPROVE("05","批准");

    private String code;
    private String title;

    NoStandardTestCheckEnum(String code, String title){
        this.code = code;
        this.title = title;
    }

    public static NoStandardTestCheckEnum get(String code){
        NoStandardTestCheckEnum result = null;
        for(NoStandardTestCheckEnum check:NoStandardTestCheckEnum.values()){
            if(check.code.equals(code)){
                result = check;
                break;
            }
        }

       return result;
    }

    public String getCode() {
        return code;
    }

    public NoStandardTestCheckEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardTestCheckEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
