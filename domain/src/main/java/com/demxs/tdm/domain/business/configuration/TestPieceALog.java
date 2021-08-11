package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class TestPieceALog extends DataEntity<TestPieceALog> {

    private String pieceId;

    @DateTimeFormat(pattern = "yyyy-MM-ss")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateTime;    //日期时间

    private String testPieceStatus;    //试验件状态

    private String orginalTestBench;    //原试验台

    private String nowTestBench;    //现试验台

    private String orginalInstallPlace;    //原安装位置

    private String nowInstallPlace;    //现安装位置

    private String relevanceOdd;    //关联单号

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getTestPieceStatus() {
        return testPieceStatus;
    }

    public void setTestPieceStatus(String testPieceStatus) {
        this.testPieceStatus = testPieceStatus;
    }

    public String getOrginalTestBench() {
        return orginalTestBench;
    }

    public void setOrginalTestBench(String orginalTestBench) {
        this.orginalTestBench = orginalTestBench;
    }

    public String getNowTestBench() {
        return nowTestBench;
    }

    public void setNowTestBench(String nowTestBench) {
        this.nowTestBench = nowTestBench;
    }

    public String getOrginalInstallPlace() {
        return orginalInstallPlace;
    }

    public void setOrginalInstallPlace(String orginalInstallPlace) {
        this.orginalInstallPlace = orginalInstallPlace;
    }

    public String getNowInstallPlace() {
        return nowInstallPlace;
    }

    public void setNowInstallPlace(String nowInstallPlace) {
        this.nowInstallPlace = nowInstallPlace;
    }

    public String getRelevanceOdd() {
        return relevanceOdd;
    }

    public void setRelevanceOdd(String relevanceOdd) {
        this.relevanceOdd = relevanceOdd;
    }
}
