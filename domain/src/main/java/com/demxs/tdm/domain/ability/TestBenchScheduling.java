package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class TestBenchScheduling extends DataEntity<TestBenchScheduling> {

    //试验台架
    private String testBench;
    //时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;
    //试验内容
    private String text;
    //工时
    private String manHour;
    //总工时
    private String countManHOur;
    //试验室
    private String labInfoId;
    private LabInfo labInfo;


    public String getTestBench() {
        return testBench;
    }

    public void setTestBench(String testBench) {
        this.testBench = testBench;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getManHour() {
        return manHour;
    }

    public void setManHour(String manHour) {
        this.manHour = manHour;
    }

    public String getCountManHOur() {
        return countManHOur;
    }

    public void setCountManHOur(String countManHOur) {
        this.countManHOur = countManHOur;
    }

    @Override
    public String getLabInfoId() {
        return labInfoId;
    }

    @Override
    public void setLabInfoId(String labInfoId) {
        this.labInfoId = labInfoId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }
}
