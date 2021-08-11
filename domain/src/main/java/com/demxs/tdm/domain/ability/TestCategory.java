package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2019/12/19 16:20
 * 试验类型entity
 * @Description:
 */
public class TestCategory extends TreeEntity<TestCategory> {

    private static final long serialVersionUID = 1L;

    /**
     * 试验编码
     */
    private String code;
    /**
     * 试验名称
     */
    private String name;
    /**
     * 父级集合ID
     */
    private String parentIds;
    /**
     * 检测标准/规范
     */
    private String standard;
    /**
     * 最低级节点
     */
    private int lowestLevel = 4;
    /**
     * 存在驳回
     */
    private String hasRejected;
    /**
     * 存在修改记录
     */
    private String hasModify;
    /**
     * 修改记录中有删除操作
     */
    private String hasDeleted;
    /**
     * 新增操作
     */
    private String isNew;
    /**
     * 父级集合
     */
    private List<TestCategory> parentList;
    /**
     * 父级节点名称
     */
    private String parentName;
    /**
     * 版本id
     */
    private String vId;
    /**
     * 关联版本对象
     */
    private TestCategoryVersion version;
    /**
     * 升版后储存的上一版本旧信息id
     */
    private String beforeUpCId;
    /**
     * 升版后储存的上一版本旧版本的id
     */
    private String beforeUpVId;
    /**
     * 修改申请单id，视图传递对象
     */
    private String rId;
    private String rStatus;
    /**
     * 修改申请单id，视图传递对象
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 过滤试验室id，视图传递字段
     */
    private List<String> filterLabIdList;
    /**
     * 试验室能力具备情况，视图传递字段
     */
    private List<TestCategoryLab> labList;
    /**
     * 能力评估状态，视图传递字段
     */
    private String assessStatus;
    /**
     * 视图传递，有效性
     */
    private String effectiveness;
    /**
     * 视图传递，修改申请单创建人
     */
    private String createUser;
    /**
     * 视图传递，所属分中心
     */
    private String subCenterId;
    /**
     * 是否具备
    */
    private String attribute;
    /**
     * 视图传递，只查看最低级条目
     */
    private String lowLevelOnly;
    /**
     * 视图传递对象，是否过滤试验室id
     */
    private String labFilter;
    /**
     * 视图传递，评估文件id，是否填写过评估文件
     */
    private String assessmentId;
    /**
     * 视图传递，项目建设id，是否填写过项目建设
     */
    private String projectId;

    //能力评估
    private TestCategoryAssessment testCategoryAssessment;

    //试验室编号
    List<String> labIds;

    public TestCategory(){

    }

    public TestCategory(String id){
        super(id);
    }

    @ExcelField(title = "编码")
    public TestCategory setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    @ExcelField(title = "名称")
    public void setName(String name) {
        this.name = name;
    }

    @Override
    @ExcelField(title = "parent")
    public void setParent(TestCategory parent) {
        this.parent = parent;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TestCategory getParent() {
        return parent;
    }

    /**
    * @author Jason
    * @date 2020/8/3 13:33
    * @params []
    * 避开treeEntity的get方法中的@JsonBackReference注解
    * @return com.demxs.tdm.domain.ability.TestCategory
    */
    public TestCategory getOverrideParent() {
        return parent;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    public String getStandard() {
        return standard;
    }

    public TestCategory setStandard(String standard) {
        this.standard = standard;
        return this;
    }

    public int getLowestLevel() {
        return lowestLevel;
    }

    public TestCategory setLowestLevel(int lowestLevel) {
        this.lowestLevel = lowestLevel;
        return this;
    }

    public String getHasModify() {
        return hasModify;
    }

    public TestCategory setHasModify(String hasModify) {
        this.hasModify = hasModify;
        return this;
    }

    public String getHasDeleted() {
        return hasDeleted;
    }

    public TestCategory setHasDeleted(String hasDeleted) {
        this.hasDeleted = hasDeleted;
        return this;
    }

    public String getIsNew() {
        return isNew;
    }

    public TestCategory setIsNew(String isNew) {
        this.isNew = isNew;
        return this;
    }

    public List<TestCategory> getParentList() {
        return parentList;
    }

    /**
    * @author Jason
    * @date 2020/8/3 10:38
    * @params [parentList]
    * 设置并排序
    * @return com.demxs.tdm.domain.ability.TestCategory
    */
    public TestCategory setParentList(List<TestCategory> parentList) {
        this.parentList = parentList;
        return this;
    }

    public String getParentName() {
        return parentName;
    }

    public TestCategory setParentName(String parentName) {
        this.parentName = parentName;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategory setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public TestCategoryVersion getVersion() {
        return version;
    }

    public TestCategory setVersion(TestCategoryVersion version) {
        this.version = version;
        return this;
    }

    public String getrId() {
        return rId;
    }

    public TestCategory setrId(String rId) {
        this.rId = rId;
        return this;
    }

    public List<TestCategoryLab> getLabList() {
        return labList;
    }

    public TestCategory setLabList(List<TestCategoryLab> labList) {
        this.labList = labList;
        return this;
    }

    public List<String> getFilterLabIdList() {
        return filterLabIdList;
    }

    public TestCategory setFilterLabIdList(List<String> filterLabIdList) {
        this.filterLabIdList = filterLabIdList;
        return this;
    }

    public String getAssessStatus() {
        return assessStatus;
    }

    public TestCategory setAssessStatus(String assessStatus) {
        this.assessStatus = assessStatus;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestCategory setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getHasRejected() {
        return hasRejected;
    }

    public TestCategory setHasRejected(String hasRejected) {
        this.hasRejected = hasRejected;
        return this;
    }

    public boolean getIsRejected(){
        return TestCategoryModify.REJECT.equals(this.hasRejected);
    }

    public String getEffectiveness() {
        return effectiveness;
    }

    public TestCategory setEffectiveness(String effectiveness) {
        this.effectiveness = effectiveness;
        return this;
    }

    public String getrStatus() {
        return rStatus;
    }

    public TestCategory setrStatus(String rStatus) {
        this.rStatus = rStatus;
        return this;
    }

    public String getCreateUser() {
        return createUser;
    }

    public TestCategory setCreateUser(String createUser) {
        this.createUser = createUser;
        return this;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getSubCenterId() {
        return subCenterId;
    }

    public TestCategory setSubCenterId(String subCenterId) {
        this.subCenterId = subCenterId;
        return this;
    }

    public String getLabFilter() {
        return labFilter;
    }

    public TestCategory setLabFilter(String labFilter) {
        this.labFilter = labFilter;
        return this;
    }

    public String getLowLevelOnly() {
        return lowLevelOnly;
    }

    public TestCategory setLowLevelOnly(String lowLevelOnly) {
        this.lowLevelOnly = lowLevelOnly;
        return this;
    }

    public String getBeforeUpCId() {
        return beforeUpCId;
    }

    public TestCategory setBeforeUpCId(String beforeUpCId) {
        this.beforeUpCId = beforeUpCId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategory setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getLabName(){
        if(this.labInfo != null){
            return this.labInfo.getName();
        }
        return null;
    }

    public String getAssessmentId() {
        return assessmentId;
    }

    public TestCategory setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
        return this;
    }

    public String getBeforeUpVId() {
        return beforeUpVId;
    }

    public TestCategory setBeforeUpVId(String beforeUpVId) {
        this.beforeUpVId = beforeUpVId;
        return this;
    }

    @Override
    public String getParentId() {
        return this.parent == null ? null : parent.getId();
    }

    @Override
    public boolean equals(Object o) {
        TestCategory that = (TestCategory) o;
        return this.code.equals(that.code);
    }

    @Override
    public int hashCode() {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (parent != null ? parent.hashCode() : 0);
        result = 31 * result + (parentIds != null ? parentIds.hashCode() : 0);
        return result;
    }

    public TestCategoryAssessment getTestCategoryAssessment() {
        return testCategoryAssessment;
    }

    public void setTestCategoryAssessment(TestCategoryAssessment testCategoryAssessment) {
        this.testCategoryAssessment = testCategoryAssessment;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public List<String> getLabIds() {
        return labIds;
    }

    public void setLabIds(List<String> labIds) {
        this.labIds = labIds;
    }
}
