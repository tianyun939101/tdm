package com.demxs.tdm.domain.business.constant;


public enum TestTaskEnum {

    CHECK("01","驳回"),
    MANAGER_AUDIT("02","编制审批"),
    REPORT("03","报告审批"),
    AUDIT("04","批准");

    private String code;
    private String title;

    TestTaskEnum(String code, String title){
        this.code = code;
        this.title = title;
    }

    public static TestTaskEnum get(String code){
        TestTaskEnum result = null;
        for(TestTaskEnum check: TestTaskEnum.values()){
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

    public TestTaskEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TestTaskEnum setTitle(String title) {
        this.title = title;
        return this;
    }}
