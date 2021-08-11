package com.demxs.tdm.domain.dataCenter.parse;

import java.io.Serializable;

/**
 * @Auther: Jason
 * @Date: 2020/4/1 11:27
 * 不继承BaseEntity对象，节省内存
 * @Description:
 */
public class ParsedColumnData implements Serializable {

    //所属列
    private String headName;
    //所属行
    private String rowNum;
    //值
    private String val;

    public String getHeadName() {
        return headName;
    }

    public ParsedColumnData setHeadName(String headName) {
        this.headName = headName;
        return this;
    }

    public String getRowNum() {
        return rowNum;
    }

    public ParsedColumnData setRowNum(String rowNum) {
        this.rowNum = rowNum;
        return this;
    }

    public String getVal() {
        return val;
    }

    public ParsedColumnData setVal(String val) {
        this.val = val;
        return this;
    }


}
