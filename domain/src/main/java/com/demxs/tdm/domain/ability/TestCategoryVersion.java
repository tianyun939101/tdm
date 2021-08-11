package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/8/3 17:56
 * @Description: 能力版本实体类
 */
public class TestCategoryVersion extends DataEntity<TestCategoryVersion> {

    private static final long serialVersionUID = 1L;
    /**
     * 版本号
     */
    private String code;
    /**
     * 版本说明
     */
    private String description;
    /**
     * 是否下发（0：否，1：是）
     */
    private String issue;
    public static final String NOT_ISSUED = "0";
    public static final String ISSUED = "1";
    /**
     * 是否启用（0：否，1：是）
     */
    private String enabled;
    public static final String NOT_ENABLED = "0";
    public static final String ENABLED = "1";

    //当前版本年度
    private String year;
    /**
     * 复制对象，视图传输使用
     */
    private TestCategoryVersion copy;

    public TestCategoryVersion() {
    }

    public TestCategoryVersion(String id) {
        super(id);
    }

    public String getCode() {
        return code;
    }

    public TestCategoryVersion setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TestCategoryVersion setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getIssue() {
        return issue;
    }

    public TestCategoryVersion setIssue(String issue) {
        this.issue = issue;
        return this;
    }

    public String getEnabled() {
        return enabled;
    }

    public TestCategoryVersion setEnabled(String enabled) {
        this.enabled = enabled;
        return this;
    }

    public TestCategoryVersion getCopy() {
        return copy;
    }

    public TestCategoryVersion setCopy(TestCategoryVersion copy) {
        this.copy = copy;
        return this;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
