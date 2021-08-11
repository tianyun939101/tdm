package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 09:45
 * @Description:
 */
public enum  NoStandardExecutionEnum {

    WAIT("0","待执行"),
    EXECUTING("1","执行中"),
    COMPLETE("2","已完成"),
    STOPPED("3","终止");


    private String code;
    private String title;

    NoStandardExecutionEnum(String code,String title){
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardExecutionEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardExecutionEnum setTitle(String title) {
        this.title = title;
        return this;
    }
}
