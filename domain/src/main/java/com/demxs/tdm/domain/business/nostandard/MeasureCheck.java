package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 计量检查
 */
public class MeasureCheck extends DataEntity<MeasureCheck> {


    /**
     * 序号
     */
    private String serNum;
    /**
     * 测量设备名称
     */
    private String testSBName;
    /**
     *  型号/规格
     */
    private String type;
    /**
     * 主要技术指标
     */
    private String mastJszb;
    /**
     * 出厂编号
     */
    private String ccCode;
    /**
     * 有效日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date youXDate;
    /**
     * 检查结果
     */
    private String checkResult;


    private String baseTable;


    public String getSerNum() {
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public String getTestSBName() {
        return testSBName;
    }

    public void setTestSBName(String testSBName) {
        this.testSBName = testSBName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMastJszb() {
        return mastJszb;
    }

    public void setMastJszb(String mastJszb) {
        this.mastJszb = mastJszb;
    }

    public String getCcCode() {
        return ccCode;
    }

    public void setCcCode(String ccCode) {
        this.ccCode = ccCode;
    }

    public Date getYouXDate() {
        return youXDate;
    }

    public void setYouXDate(Date youXDate) {
        this.youXDate = youXDate;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getBaseTable() {
        return baseTable;
    }

    public void setBaseTable(String baseTable) {
        this.baseTable = baseTable;
    }

}
