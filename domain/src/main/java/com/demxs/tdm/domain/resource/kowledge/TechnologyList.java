package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.TreeEntity;

public class TechnologyList extends TreeEntity<TechnologyList> {

    private String name;
    private String code;

    private String technologyName;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public void setTechnologyName(String technologyName) {
        this.technologyName = technologyName;
    }

    @Override
    public TechnologyList getParent() {
        return null;
    }

    @Override
    public void setParent(TechnologyList parent) {

    }
}
