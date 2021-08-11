package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;

/**
 * @author: Jason
 * @Date: 2020/9/29 16:24
 * @Description: 试验能力和能力建设项目关联对象
 */
public class TestCategoryProject extends DataEntity<TestCategoryProject> {

    private static final long serialVersionUID = 18863L;

    /**
     * 试验能力
     */
    private String cId;
    private TestCategory category;
    /**
     * 试验能力版本id
     */
    private String vId;
    private TestCategoryVersion version;
    /**
     * 试验室
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 项目
     */
    private String pId;
    private AbilityProject project;

    public TestCategoryProject() {
    }

    public TestCategoryProject(String id) {
        super(id);
    }

    public String getcId() {
        return cId;
    }

    public TestCategoryProject setcId(String cId) {
        this.cId = cId;
        return this;
    }

    public TestCategory getCategory() {
        return category;
    }

    public TestCategoryProject setCategory(TestCategory category) {
        this.category = category;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryProject setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public TestCategoryVersion getVersion() {
        return version;
    }

    public TestCategoryProject setVersion(TestCategoryVersion version) {
        this.version = version;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestCategoryProject setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public TestCategoryProject setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getpId() {
        return pId;
    }

    public TestCategoryProject setpId(String pId) {
        this.pId = pId;
        return this;
    }

    public AbilityProject getProject() {
        return project;
    }

    public TestCategoryProject setProject(AbilityProject project) {
        this.project = project;
        return this;
    }
}
