package com.demxs.tdm.domain.business.constant;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 09:29
 * @Description:
 */
public enum NoStandardEntrustInfoEnum {

    EDIT("0","正在编辑"),
    RESOURCE("1","资源分配"),//试验室负责人接收
    INSPECT("2","试验前检查中"),//试验前检查
    EXECUTION("3","试验执行"),
    COMPILE("4","编制报告"),
    FILE("5","归档"),
    STOPPED("6","终止");

    private String code;
    private String title;

    NoStandardEntrustInfoEnum(String code,String title){
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public NoStandardEntrustInfoEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public NoStandardEntrustInfoEnum setTitle(String title) {
        this.title = title;
        return this;
    }

}
