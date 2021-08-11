package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

/**
 * @author: Jason
 * @Date: 2020/7/29 10:41
 * @Description: 试验类型修改记录实体类
 */
public class TestCategoryModify extends DataEntity<TestCategoryModify> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验类型id
     */
    private String cId;
    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;
    /**
     * 标准规范
     */
    private String standard;
    /**
     * 是否新增操作
     */
    private String isNew;
    /**
     * 是否删除操作
     */
    private String isToDeleted;
    /**
     * 父级id
     */
    private String parentId;
    /**
     * 父级id集合
     */
    private String parentIds;
    /**
     * 层级关系
     */
    private TestCategory parent;
    /**
     * 修改说明
     */
    private String description;
    /**
     * 版本id
     */
    private String vId;
    /**
     * 修改申请单id
     */
    private String rId;
    /**
     * 试验室id
     * @see com.demxs.tdm.domain.lab.LabInfo id
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 能力评估状态（0：未评估；A：未形成能力；B：试运行；C：初步具备试验能力，可承担试验任务；D：形成试验能力）
     */
    private String assessStatus;
    private TestCategoryModifyRecord record;
    /**
     * 状态（0：未应用，1：已应用，2：驳回）
     */
    private String status;
    public static final String NOT_APPLIED = "0";
    public static final String APPLIED = "1";
    public static final String REJECT = "2";
    /**
     * 行为（0：新增，1：修改，2：删除）
     */
    private String action;
    /**
     * 过滤申请单状态
     */
    private String effectiveness;
    private String rStatus;

    public TestCategoryModify() {
    }

    public TestCategoryModify(String id) {
        super(id);
    }

    public String getcId() {
        return cId;
    }

    public TestCategoryModify setcId(String cId) {
        this.cId = cId;
        return this;
    }

    public String getCode() {
        return code;
    }

    public TestCategoryModify setCode(String code) {
        this.code = code;
        return this;
    }

    public String getStandard() {
        return standard;
    }

    public TestCategoryModify setStandard(String standard) {
        this.standard = standard;
        return this;
    }

    public String getIsNew() {
        return isNew;
    }

    public TestCategoryModify setIsNew(String isNew) {
        this.isNew = isNew;
        return this;
    }

    public String getIsToDeleted() {
        return isToDeleted;
    }

    public TestCategoryModify setIsToDeleted(String isToDeleted) {
        this.isToDeleted = isToDeleted;
        return this;
    }

    public String getName() {
        return name;
    }

    public TestCategoryModify setName(String name) {
        this.name = name;
        return this;
    }

    public boolean isToDeleted(){
        return this.isToDeleted != null && isToDeleted.length() > 0;
    }

    public boolean isAddAction(){
        return this.isNew != null && isNew.length() > 0;
    }

    public String getParentId() {
        return parentId;
    }

    public TestCategoryModify setParentId(String parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getParentIds() {
        return parentIds;
    }

    public TestCategoryModify setParentIds(String parentIds) {
        this.parentIds = parentIds;
        return this;
    }

    public TestCategory getParent() {
        return parent;
    }

    public TestCategoryModify setParent(TestCategory parent) {
        this.parent = parent;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TestCategoryModify setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getrId() {
        return rId;
    }

    public TestCategoryModify setrId(String rId) {
        this.rId = rId;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryModify setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public String getAssessStatus() {
        return assessStatus;
    }

    public TestCategoryModify setAssessStatus(String assessStatus) {
        this.assessStatus = assessStatus;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestCategoryModify setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TestCategoryModify setStatus(String status) {
        this.status = status;
        return this;
    }

    public boolean getIsRejected(){
        return TestCategoryModify.REJECT.equals(this.status);
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategoryModify setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public TestCategoryModify setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }

    public String getAction() {
        return action;
    }

    public TestCategoryModify setAction(String action) {
        this.action = action;
        return this;
    }

    public String getrStatus() {
        return rStatus;
    }

    public TestCategoryModify setrStatus(String rStatus) {
        this.rStatus = rStatus;
        return this;
    }

    public TestCategoryModifyRecord getRecord() {
        return record;
    }

    public TestCategoryModify setRecord(TestCategoryModifyRecord record) {
        this.record = record;
        return this;
    }
}
