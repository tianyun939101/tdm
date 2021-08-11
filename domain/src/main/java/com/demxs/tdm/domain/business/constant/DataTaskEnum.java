package com.demxs.tdm.domain.business.constant;


public enum DataTaskEnum {

    CHECK("01","驳回"),
    MANAGER_AUDIT("02","数据中心"),
    AUDIT("03","完成");

    private String code;
    private String title;

    DataTaskEnum(String code, String title){
        this.code = code;
        this.title = title;
    }

    public static DataTaskEnum get(String code){
        DataTaskEnum result = null;
        for(DataTaskEnum check: DataTaskEnum.values()){
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

    public DataTaskEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public DataTaskEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
