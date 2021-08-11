package com.demxs.tdm.domain.business.configuration;

public enum ConfigurationChangeApplyEnum {

    APPLY("00","已申请"),
    AUTHOR("01","编制"),
    PROO("02","校对"),
    RATIFY("03","批准"),
    EXIT("04","已批准"),
    REJECT("05","驳回");


    private String code;
    private String title;

    ConfigurationChangeApplyEnum(String code, String title){
        this.code = code;
        this.title = title;
    }
    public static ConfigurationChangeApplyEnum get(String code){
        ConfigurationChangeApplyEnum result = null;
        for(ConfigurationChangeApplyEnum check:ConfigurationChangeApplyEnum.values()){
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

    public ConfigurationChangeApplyEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ConfigurationChangeApplyEnum setTitle(String title) {
        this.title = title;
        return this;
    }
}
