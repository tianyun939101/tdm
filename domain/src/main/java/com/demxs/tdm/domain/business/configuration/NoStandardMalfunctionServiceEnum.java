package com.demxs.tdm.domain.business.configuration;

public enum NoStandardMalfunctionServiceEnum {

    APPLY("00","已提交"),
    AUTHOR("01","审批"),
    PROO("02","IPT项目经理"),
    EXIT("03","已批准"),
    REJECT("04","驳回");


    private String code;
    private String title;

    NoStandardMalfunctionServiceEnum(String code, String title){
        this.code = code;
        this.title = title;
    }
    public static NoStandardMalfunctionServiceEnum get(String code){
        NoStandardMalfunctionServiceEnum result = null;
        for(NoStandardMalfunctionServiceEnum check: NoStandardMalfunctionServiceEnum.values()){
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

    public NoStandardMalfunctionServiceEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardMalfunctionServiceEnum setTitle(String title) {
        this.title = title;
        return this;
    }
}
