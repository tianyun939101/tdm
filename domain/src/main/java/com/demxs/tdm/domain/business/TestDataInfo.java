package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class TestDataInfo extends DataEntity<TestDataInfo> {
    /**
     *唯一编号
     */
    private String testId;
    /**
     * 测试名称
     */
    private String testName;
    /**
     *测试时间
     */
    private Date testTime;
    /**
     * 测试人
     */
    private String tester;
    /**
     *试验数据文件ID
     */
    private String testDataFile;
    /**
     *试飞数据文件ID
     */
    private String trueDataFile;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public String getTestDataFile() {
        return testDataFile;
    }

    public void setTestDataFile(String testDataFile) {
        this.testDataFile = testDataFile;
    }

    public String getTrueDataFile() {
        return trueDataFile;
    }

    public void setTrueDataFile(String trueDataFile) {
        this.trueDataFile = trueDataFile;
    }
}
