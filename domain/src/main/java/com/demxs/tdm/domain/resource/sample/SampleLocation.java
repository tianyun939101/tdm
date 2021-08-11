package com.demxs.tdm.domain.resource.sample;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * 样品存放位置
 */
public class SampleLocation extends TreeEntity<SampleLocation> {


    private String name;      //位置名称
    private String valid;     //有效性  0否1是
    private SampleLocation parent;
    private String parentIds;        // parent_ids

    private LabInfo labInfo;

    public SampleLocation() {
    }
    public SampleLocation(String id) {
        super(id);
    }

    @Override
    @JsonBackReference
    public SampleLocation getParent() {
        return parent;
    }

    @Override
    public void setParent(SampleLocation parent) {

        this.parent = parent;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Override
    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }


    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }
}
