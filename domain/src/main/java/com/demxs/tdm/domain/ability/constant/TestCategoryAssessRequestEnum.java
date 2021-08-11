package com.demxs.tdm.domain.ability.constant;

import com.demxs.tdm.common.utils.StringUtils;

/**
 * @author: Jason
 * @Date: 2020/9/23 10:51
 * @Description: 评估申请单状态
 */
public enum TestCategoryAssessRequestEnum {

    EDIT("0","编制"),
    LAB_AUDIT("1","实验室审核"),
    COMPANY_AUDIT("2","科技部审核"),
    COMPANY_APPROVAL("3","科技部审批"),
    CENTER_AUDIT("4","公司验证中心审核"),
    CENTER_APPROVAL("5","公司验证中心审批"),
    APPROVED("6","已批准"),
    REJECT("7","审核驳回"),
    ;

    private String code;
    private String title;

    TestCategoryAssessRequestEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public TestCategoryAssessRequestEnum getByCode(String code){
        if(StringUtils.isBlank(code)){
            return null;
        }
        for (TestCategoryAssessRequestEnum value : TestCategoryAssessRequestEnum.values()) {
            if(value.code.equals(code)){
                return value;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public TestCategoryAssessRequestEnum setCode(String code) {
        this.code = code;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TestCategoryAssessRequestEnum setTitle(String title) {
        this.title = title;
        return this;
    }
}
