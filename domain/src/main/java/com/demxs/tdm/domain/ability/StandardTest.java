package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: lzw
 * @Date: 2020/10/29 20:25
 * @Description: 标准与试验关系
 */
public class StandardTest extends DataEntity<StandardTest> {

    private static final long serialVersionUID = 1775L;
    //规范ID
    private String standardId;
    //试验ID
    private String testId;

    //规范编码
    private String code;
    //规范名称
    private String name;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
