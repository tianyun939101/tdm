package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.domain.business.instrument.DzCategory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

public class ZyProjectTree extends TreeEntity<ZyProjectTree> {

    private String id;

    private String projectName;

    private String parentId;

    private String parentIds;

    private String state;

    private String name;

    private ZyProjectTree parent;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    @JsonBackReference
    public ZyProjectTree getParent() {
        return parent;
    }
    public ZyProjectTree() {

    }

    public ZyProjectTree(String id) {
        this.id = id;
    }

    @Override
    public void setParent(ZyProjectTree parent) {
        this.parent = parent;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}