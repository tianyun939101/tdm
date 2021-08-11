package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

/**
 * 试验室整体状态
 * @author wuhui
 * @date 2020/10/28 10:55
 **/
public class LabInfoState  extends DataEntity<LabInfoState> {
    private String id; //编号
    private String vId;//版本编号
    private String labId;//试验数编号
    private String year;//年度
    private Integer staffNum;//试验室人员
    private String position;//能力定位
    private String place;//试验场地
    private String personNorm;//人员规范
    private String persionCert;//人员资质
    private String plan;//应急预案
    private String manageFlow;//管理流程
    private String describe;//整体描述

    private LabInfo labInfo;

    public LabInfoState() {
    }

    public LabInfoState(String vId, String labId) {
        this.vId = vId;
        this.labId = labId;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getStaffNum() {
        return staffNum;
    }

    public void setStaffNum(Integer staffNum) {
        this.staffNum = staffNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getPersonNorm() {
        return personNorm;
    }

    public void setPersonNorm(String personNorm) {
        this.personNorm = personNorm;
    }

    public String getPersionCert() {
        return persionCert;
    }

    public void setPersionCert(String persionCert) {
        this.persionCert = persionCert;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getManageFlow() {
        return manageFlow;
    }

    public void setManageFlow(String manageFlow) {
        this.manageFlow = manageFlow;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}

