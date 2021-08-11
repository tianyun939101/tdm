package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

/**
 * 岗位职责及知识资源地图
 */
public class KnowledgeMap extends DataEntity<KnowledgeMap> {
    private String org;     //部门
    private String dept;    //科室
    private String pos;     //岗位
    private String oneDuty; //1级职责
    private String twoDuty; //2级职责
    private String threeDuty;   //3级职责
    private String code;    //知识编号
    private String versionNo;   //知识版本
    private String name;    //知识名称
    private String type;    //知识类型
    private String fun;     //功能
    private String source;  //来源
    private String cost;    //价值
    private String grad;    //分级
    private String certCode;    //资质编号
    private String appCase;     //应用情况
    private String chargeUser;  //知识维护人

    private String serialNum;   //序号

    private Date updateTime;    //更新时间

    private String  pricticesId;    //BestPractices 表ID

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Date getUpdateDate() {
        return updateDate;
    }

    @Override
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getPricticesId() {
        return pricticesId;
    }

    public void setPricticesId(String pricticesId) {
        this.pricticesId = pricticesId;
    }
    /*
    private String  providerId;

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }*/

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getOneDuty() {
        return oneDuty;
    }

    public void setOneDuty(String oneDuty) {
        this.oneDuty = oneDuty;
    }

    public String getTwoDuty() {
        return twoDuty;
    }

    public void setTwoDuty(String twoDuty) {
        this.twoDuty = twoDuty;
    }

    public String getThreeDuty() {
        return threeDuty;
    }

    public void setThreeDuty(String threeDuty) {
        this.threeDuty = threeDuty;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFun() {
        return fun;
    }

    public void setFun(String fun) {
        this.fun = fun;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }

    public String getCertCode() {
        return certCode;
    }

    public void setCertCode(String certCode) {
        this.certCode = certCode;
    }

    public String getAppCase() {
        return appCase;
    }

    public void setAppCase(String appCase) {
        this.appCase = appCase;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }
}
