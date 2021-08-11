package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

/**
 *  能力评估自查
 * @author wuhui
 * @date 2020/10/17 10:54
 **/
public  class AbilityEvaluationCheck extends DataEntity<AbilityEvaluationCheck> {

    private String id; //编号
    private String vId;//版本编号
    private String cId;//能力编号
    private String labId;//实验室编号
    private Boolean accept;//国家认证
    private Boolean ripe;//成熟的试验能力
    private Boolean testStandard;//测试规范
    private String other;//其他能力
    private Boolean install;//设备安装调试
    private Boolean workStandard;//设备操作规范
    private Boolean equipmentAccept;//设备验收
    private Boolean documentA;//文档A
    private Boolean documentB;//文档B
    private Boolean documentC;//文档C

    private LabInfo labInfo;
    private TestCategory testCategory;


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

    public Boolean getAccept() {
        return accept;
    }

    public void setAccept(Boolean accept) {
        this.accept = accept;
    }

    public Boolean getRipe() {
        return ripe;
    }

    public void setRipe(Boolean ripe) {
        this.ripe = ripe;
    }

    public Boolean getTestStandard() {
        return testStandard;
    }

    public void setTestStandard(Boolean testStandard) {
        this.testStandard = testStandard;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Boolean getInstall() {
        return install;
    }

    public void setInstall(Boolean install) {
        this.install = install;
    }

    public Boolean getWorkStandard() {
        return workStandard;
    }

    public void setWorkStandard(Boolean workStandard) {
        this.workStandard = workStandard;
    }

    public Boolean getEquipmentAccept() {
        return equipmentAccept;
    }

    public void setEquipmentAccept(Boolean equipmentAccept) {
        this.equipmentAccept = equipmentAccept;
    }

    public Boolean getDocumentA() {
        return documentA;
    }

    public void setDocumentA(Boolean documentA) {
        this.documentA = documentA;
    }

    public Boolean getDocumentB() {
        return documentB;
    }

    public void setDocumentB(Boolean documentB) {
        this.documentB = documentB;
    }

    public Boolean getDocumentC() {
        return documentC;
    }

    public void setDocumentC(Boolean documentC) {
        this.documentC = documentC;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public TestCategory getTestCategory() {
        return testCategory;
    }

    public void setTestCategory(TestCategory testCategory) {
        this.testCategory = testCategory;
    }
}
