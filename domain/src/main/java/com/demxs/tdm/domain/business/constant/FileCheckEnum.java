package com.demxs.tdm.domain.business.constant;


public enum FileCheckEnum {

    CHECK("01","驳回"),
    MANAGER_AUDIT("02","校对人审批"),
    AUDIT("03","编制人检查"),
    SUBMIT("04","批准");

    private String code;
    private String title;

    FileCheckEnum(String code, String title){
        this.code = code;
        this.title = title;
    }

    public static FileCheckEnum get(String code){
        FileCheckEnum result = null;
        for(FileCheckEnum check: FileCheckEnum.values()){
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

    public FileCheckEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public FileCheckEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
