package com.demxs.tdm.domain.ability.constant;

/**
 * @author: Jason
 * @Date: 2020/8/4 15:50
 * @Description:
 */
public enum TestCategoryAssessEnum {
    NOT_EVALUATED("0","未评估"),
    A("A","A-未形成能力"),
    B("B","B-试运行"),
    C("C","C-初步具备试验能力，可承担试验任务"),
    D("D","D-形成试验能力"),
    ;
    private String code;
    private String title;

    TestCategoryAssessEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public static TestCategoryAssessEnum get(String code){
        if(code == null || code.length() == 0){
            return null;
        }
        TestCategoryAssessEnum[] values = TestCategoryAssessEnum.values();
        for (TestCategoryAssessEnum value : values) {
            if(value.getCode().equals(code)){
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public TestCategoryAssessEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TestCategoryAssessEnum setTitle(String title) {
        this.title = title;
        return this;
    }

    public static String getNotEvaluatedCode(){
        return NOT_EVALUATED.code;
    }

    public static TestCategoryAssessEnum getStatus(String code){
        for(TestCategoryAssessEnum assess :TestCategoryAssessEnum.values()){
            if(assess.getCode().equals(code)){
                assess =  assess == NOT_EVALUATED ? A:assess;
                return assess;
            }
        }
        return A;
    }
}
