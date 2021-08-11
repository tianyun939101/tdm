package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 数据中心数据实体类
 */
public class DataInfo extends DataEntity<DataInfo>{

    private static final long serialVersionUID = 1L;

    private String testInfoId;//试验信息ID

    private String testDataType;//试验数据类型

    private String testData;//试验数据

    private String dataType;//数据类型

    private String dataName;//数据名称

    private String testItemName;//试验项目名称

    private String entrustId;//申请单id

    private String entrustCode;//申请单code

    private String entrustType;//申请单类型

    private String applicant;//申请人

    private String entrustOrg;//申请单部门

    private String company;//所属公司

    private String labLeader;//试验室负责人

    private String testLeader;//试验负责人

    private String taskNo;//任务书编号

    private String planNo;//计划编号

    private String ataChapter;//ATA章节

    private String testNature;//试验性质

    private String productModel;//产品型号

    private String testName;//试验名称

    private String testProgramType;//试验大纲类型

    private String testProgram;//试验大纲

    private String configType;//构型类型

    private String config;//构型

    private String labinfoName;//所属实验室名称

    /**
     * 门户树检索条件
     * @return
     */
    private String portal;
    private String portalPid;

    public String getPortalPid() {
        return portalPid;
    }

    public void setPortalPid(String portalPid) {
        this.portalPid = portalPid;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public String getTestInfoId() {
        return testInfoId;
    }

    public void setTestInfoId(String testInfoId) {
        this.testInfoId = testInfoId;
    }

    public String getTestDataType() {
        return testDataType;
    }

    public void setTestDataType(String testDataType) {
        this.testDataType = testDataType;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getTestItemName() {
        return testItemName;
    }

    public void setTestItemName(String testItemName) {
        this.testItemName = testItemName;
    }

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getEntrustType() {
        return entrustType;
    }

    public void setEntrustType(String entrustType) {
        this.entrustType = entrustType;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getEntrustOrg() {
        return entrustOrg;
    }

    public void setEntrustOrg(String entrustOrg) {
        this.entrustOrg = entrustOrg;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLabLeader() {
        return labLeader;
    }

    public void setLabLeader(String labLeader) {
        this.labLeader = labLeader;
    }

    public String getTestLeader() {
        return testLeader;
    }

    public void setTestLeader(String testLeader) {
        this.testLeader = testLeader;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getAtaChapter() {
        return ataChapter;
    }

    public void setAtaChapter(String ataChapter) {
        this.ataChapter = ataChapter;
    }

    public String getTestNature() {
        return testNature;
    }

    public void setTestNature(String testNature) {
        this.testNature = testNature;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestProgramType() {
        return testProgramType;
    }

    public void setTestProgramType(String testProgramType) {
        this.testProgramType = testProgramType;
    }

    public String getTestProgram() {
        return testProgram;
    }

    public void setTestProgram(String testProgram) {
        this.testProgram = testProgram;
    }

    public String getConfigType() {
        return configType;
    }

    public void setConfigType(String configType) {
        this.configType = configType;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLabinfoName() {
        return labinfoName;
    }

    public void setLabinfoName(String labinfoName) {
        this.labinfoName = labinfoName;
    }
}