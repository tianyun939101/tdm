package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.domain.ability.TestItem;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.List;

public class ZyTestMethod extends DataEntity<ZyTestMethod> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String projectId;

    private String projectName;

    private String remarks;

    private TestItem testItem;


    public TestItem getTestItem() {
        return testItem;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
