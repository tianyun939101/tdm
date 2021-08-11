package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 提报试验属性
 * @author wuhui
 * @date 2020/10/14 13:29
 **/
public class TestInfoAttr extends DataEntity<TestInfoAttr> {

    private String id;//属性编号
    private String columnName;//属性标识
    private String name;//属性名
    private String dataType;//属性类型(01:文本 02:文件)

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
