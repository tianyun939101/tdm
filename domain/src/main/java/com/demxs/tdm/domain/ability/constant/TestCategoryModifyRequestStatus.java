package com.demxs.tdm.domain.ability.constant;

/**
 * @author: Jason
 * @Date: 2020/8/4 15:50
 * @Description:
 */
public enum TestCategoryModifyRequestStatus {
    //1：实验员编制
    NOT_SUBMITTED("0","编制"),
    //2：试验室主任审核
    UNDER_REVIEW("1","待审核"),
    //End：流程结束，进入升版视图
    APPROVED("2","已批准"),
    //驳回始终回到第一步
    REJECTED("3","审核驳回"),
    //5：单位批准
    UNDER_APPROVED("4","单位批准"),
    //3：会签
    JOINTLY_SIGN("5","会签"),
    //4：单位审核
    EXAMINATION("6","单位审核"),
    //6：规划部批准
    CENTER_APPROVED("7","公司试验验证中心"),
    ;
    private String code;
    private String title;

    TestCategoryModifyRequestStatus(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static TestCategoryModifyRequestStatus get(String code){
        if(code == null || code.length() == 0){
            return null;
        }
        TestCategoryModifyRequestStatus[] values = TestCategoryModifyRequestStatus.values();
        for (TestCategoryModifyRequestStatus value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public static String getApprovedCode(){
        return APPROVED.code;
    }
    public static String getUnderApprovedCode(){
        return UNDER_APPROVED.code;
    }

    public String getCode() {
        return code;
    }

    public TestCategoryModifyRequestStatus setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TestCategoryModifyRequestStatus setTitle(String title) {
        this.title = title;
        return this;
    }
}
