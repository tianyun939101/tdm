package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/10 08:53
 * @Description: 试验能力试验室评估状态
 */
public class TestCategoryLab extends DataEntity<TestCategoryLab> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验能力id
     * @see TestCategory id
     */
    private String cId;
    /**
     * 版本id
     * @see TestCategoryVersion id
     */
    private String vId;
    /**
     * 试验室id
     * @see com.demxs.tdm.domain.lab.LabInfo id
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 能力评估状态（A：未形成能力；B：试运行；C：初步具备试验能力，可承担试验任务；D：形成试验能力）
     */
    private String assessStatus;


    private String projectId;//建设项目编号
    private String argumentDate;//论证日期
    private String projectDate;//立项日期
    private String buildDate;//能力建设日期
    private String runDate;//试运行日期
    private String initialDate;//初步具备日期
    private String possessDate;//具备日期

    private AbilityProject project;//工程

    /**
     * 筛选条件
     */
    private List<String> filterLabIdList;

    public TestCategoryLab() {
    }

    public TestCategoryLab(String cId, String vId, String labId) {
        this.cId = cId;
        this.vId = vId;
        this.labId = labId;
    }

    public TestCategoryLab(String id) {
        super(id);
    }

    public String getcId() {
        return cId;
    }

    public TestCategoryLab setcId(String cId) {
        this.cId = cId;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryLab setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestCategoryLab setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getAssessStatus() {
        return assessStatus;
    }

    public TestCategoryLab setAssessStatus(String assessStatus) {
        this.assessStatus = assessStatus;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategoryLab setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public List<String> getFilterLabIdList() {
        return filterLabIdList;
    }

    public TestCategoryLab setFilterLabIdList(List<String> filterLabIdList) {
        this.filterLabIdList = filterLabIdList;
        return this;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getArgumentDate() {
        return argumentDate;
    }

    public void setArgumentDate(String argumentDate) {
        this.argumentDate = argumentDate;
    }

    public String getProjectDate() {
        return projectDate;
    }

    public void setProjectDate(String projectDate) {
        this.projectDate = projectDate;
    }

    public String getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate = buildDate;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(String initialDate) {
        this.initialDate = initialDate;
    }

    public String getPossessDate() {
        return possessDate;
    }

    public void setPossessDate(String possessDate) {
        this.possessDate = possessDate;
    }

    public AbilityProject getProject() {
        return project;
    }

    public void setProject(AbilityProject project) {
        this.project = project;
    }
}
