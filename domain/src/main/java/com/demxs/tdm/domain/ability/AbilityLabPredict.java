package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 试验室能力预测：用于存储建设计划预测试验室能力数据
 * @author wuhui
 * @date 2020/12/17 19:11
 **/
public class AbilityLabPredict extends DataEntity<AbilityLabPredict> {
    private String vId;
    private String cId;
    private String labId;
    private String status;
    private String year;

    public AbilityLabPredict() {
    }

    public AbilityLabPredict(String vId, String cId, String labId, String status, String year) {
        this.vId = vId;
        this.cId = cId;
        this.labId = labId;
        this.status = status;
        this.year = year;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

