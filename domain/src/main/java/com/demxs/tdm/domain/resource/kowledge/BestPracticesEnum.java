package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.utils.StringUtils;
/**
 * 审批单状态
 */
public enum BestPracticesEnum {
    APPROVED("1","已批准"),
    REJECT("0","审核驳回"),
            ;

    private String code;
    private String title;

    BestPracticesEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }}
