package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 提报试验属性值
 * @author wuhui
 * @date 2020/10/14 13:29
 **/
public class TestInfoAttrValue extends DataEntity<TestInfoAttrValue> {

    private String id;//属性值编号
    private String attrId;//属性编号
    private String name;//属性名称
    private String dataType;//属性类型
    private String columnName;//属性标识
    private String value;//属性值
    private String testInfoId;//试验任务编号

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTestInfoId() {
        return testInfoId;
    }

    public void setTestInfoId(String testInfoId) {
        this.testInfoId = testInfoId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
