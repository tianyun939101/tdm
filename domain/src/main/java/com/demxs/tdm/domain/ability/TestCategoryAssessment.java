package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/9/16 17:37
 * @Description: 能力评估信息实体类
 */
public class TestCategoryAssessment extends DataEntity<TestCategoryAssessment> {

    private static final long serialVersionUID = 1L;

    /**
     * 试验能力
     */
    private String cId;
    private TestCategory category;
    /**
     * 试验能力版本
     * @see com.demxs.tdm.domain.ability.TestCategoryVersion
     */
    private String vId;
    /**
     * 修改申请id
     * @see com.demxs.tdm.domain.ability.TestCategoryAssessRequest
     */
    private String rId;
    /**
     * 所属试验室
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 类型（0：型号专用，1：通用）
     */
    private String type;
    /**
     * 是否关键核心
     */
    private String isKernel;
    /**
     * 具备的能力范围
     */
    private String range;
    /**
     * 已承担典型试验
     */
    private String undertaken;
    /**
     * 有资质的试验人员数量
     */
    private String testingPerNum;
    /**
     * 标准、规范或流程的编号
     */
    private String standard;

    //标准、规范或流程的名称
    private String standardName;
    /**
     * 说明
     */
    private String explain;
    /**
     * 设备名称
     */
    private String equipmentName;
    /**
     * 型号规格
     */
    private String model;
    /**
     * 数量及编号
     */
    private String numberAndCode;
    /**
     * 主要参数或测量范围
     */
    private String parameter;
    /**
     * 有资质设备操作人员数量
     */
    private String equipOperatorNum;
    /**
     * 能力评估状态（A：未形成能力；B：试运行；C：初步具备试验能力，可承担试验任务；D：形成试验能力）
     */
    private String status;
    /**
     * 能力差距产生的原因
     */
    private String gapReason;
    /**
     * 附件
     */
    private String enclosure;
    /**
     * 计划
     */
    private String plan;
    /**
     * 项目
     */
    private String project;
    private String dataStatus;//数据操作状态 01:未上传 02:已有文件 03:需更新 04:已更新

    public static final String NO_UPLOAD = "01";
    public static final String UPLOAED = "02";
    public static final String NO_UPDATE = "03";
    public static final String UPDATED = "04";
    /**
     * 有效性（0：未应用，1：已应用，2：未填写）
     */
    private String applyStatus;
    public static final String NOT_APPLIED = "0";
    public static final String APPLIED = "1";

    public static final String NOT_FILLED_IN = "2";
    private String filterApplyStatus;


    private List<EquipmentTest> equipmentTest;

    private List<StandardTest> standardTests;

    private List<EquipmentTest> equipmentDel;

    private List<StandardTest> standardDel;

    private  String  aviationCondition; //适航条约


    public String getAviationCondition() {
        return aviationCondition;
    }

    public void setAviationCondition(String aviationCondition) {
        this.aviationCondition = aviationCondition;
    }

    public String getcId() {
        return cId;
    }

    public TestCategoryAssessment() {
    }

    public TestCategoryAssessment(String cId, String labId) {
        this.cId = cId;
        this.labId = labId;
    }

    public TestCategoryAssessment(String cId, String labId,String vId) {
        this.cId = cId;
        this.labId = labId;
        this.vId = vId;
    }

    public TestCategoryAssessment setcId(String cId) {
        this.cId = cId;
        return this;
    }


    public TestCategory getCategory() {
        return category;
    }

    public TestCategoryAssessment setCategory(TestCategory category) {
        this.category = category;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryAssessment setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestCategoryAssessment setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategoryAssessment setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getType() {
        return type;
    }

    public TestCategoryAssessment setType(String type) {
        this.type = type;
        return this;
    }

    public String getIsKernel() {
        return isKernel;
    }

    public TestCategoryAssessment setIsKernel(String isKernel) {
        this.isKernel = isKernel;
        return this;
    }

    public String getRange() {
        return range;
    }

    public TestCategoryAssessment setRange(String range) {
        this.range = range;
        return this;
    }

    public String getUndertaken() {
        return undertaken;
    }

    public TestCategoryAssessment setUndertaken(String undertaken) {
        this.undertaken = undertaken;
        return this;
    }

    public String getTestingPerNum() {
        return testingPerNum;
    }

    public TestCategoryAssessment setTestingPerNum(String testingPerNum) {
        this.testingPerNum = testingPerNum;
        return this;
    }

    public String getStandard() {
        return standard;
    }

    public TestCategoryAssessment setStandard(String standard) {
        this.standard = standard;
        return this;
    }

    public String getExplain() {
        return explain;
    }

    public TestCategoryAssessment setExplain(String explain) {
        this.explain = explain;
        return this;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public TestCategoryAssessment setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
        return this;
    }

    public String getModel() {
        return model;
    }

    public TestCategoryAssessment setModel(String model) {
        this.model = model;
        return this;
    }

    public String getNumberAndCode() {
        return numberAndCode;
    }

    public TestCategoryAssessment setNumberAndCode(String numberAndCode) {
        this.numberAndCode = numberAndCode;
        return this;
    }

    public String getParameter() {
        return parameter;
    }

    public TestCategoryAssessment setParameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public String getEquipOperatorNum() {
        return equipOperatorNum;
    }

    public TestCategoryAssessment setEquipOperatorNum(String equipOperatorNum) {
        this.equipOperatorNum = equipOperatorNum;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TestCategoryAssessment setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getGapReason() {
        return gapReason;
    }

    public TestCategoryAssessment setGapReason(String gapReason) {
        this.gapReason = gapReason;
        return this;
    }

    public String getEnclosure() {
        return enclosure;
    }

    public TestCategoryAssessment setEnclosure(String enclosure) {
        this.enclosure = enclosure;
        return this;
    }

    public String getPlan() {
        return plan;
    }

    public TestCategoryAssessment setPlan(String plan) {
        this.plan = plan;
        return this;
    }

    public String getProject() {
        return project;
    }

    public TestCategoryAssessment setProject(String project) {
        this.project = project;
        return this;
    }

    public String getrId() {
        return rId;
    }

    public TestCategoryAssessment setrId(String rId) {
        this.rId = rId;
        return this;
    }

    public String getApplyStatus() {
        return applyStatus;
    }

    public TestCategoryAssessment setApplyStatus(String applyStatus) {
        this.applyStatus = applyStatus;
        return this;
    }

    public String getFilterApplyStatus() {
        return filterApplyStatus;
    }

    public TestCategoryAssessment setFilterApplyStatus(String filterApplyStatus) {
        this.filterApplyStatus = filterApplyStatus;
        return this;
    }

    @Override
    public void preInsert() {
        this.setApplyStatus(TestCategoryAssessment.NOT_APPLIED);
        super.preInsert();
    }

    public String getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(String dataStatus) {
        this.dataStatus = dataStatus;
    }


    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }

    public List<EquipmentTest> getEquipmentTest() {
        return equipmentTest;
    }

    public void setEquipmentTest(List<EquipmentTest> equipmentTest) {
        this.equipmentTest = equipmentTest;
    }

    public List<StandardTest> getStandardTests() {
        return standardTests;
    }

    public void setStandardTests(List<StandardTest> standardTests) {
        this.standardTests = standardTests;
    }

    public List<EquipmentTest> getEquipmentDel() {
        return equipmentDel;
    }

    public void setEquipmentDel(List<EquipmentTest> equipmentDel) {
        this.equipmentDel = equipmentDel;
    }

    public List<StandardTest> getStandardDel() {
        return standardDel;
    }

    public void setStandardDel(List<StandardTest> standardDel) {
        this.standardDel = standardDel;
    }
}
