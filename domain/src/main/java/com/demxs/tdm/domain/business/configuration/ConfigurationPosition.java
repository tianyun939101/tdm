package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.TreeEntity;

/**
 * 构型存放位置
 */
public class ConfigurationPosition extends TreeEntity<ConfigurationPosition> {

    private static final long serialVersionUID = 1L;

    //存放位置名称
    private String name;
    //父级ID
    private String parentIds;

    public ConfigurationPosition(){
        super();
    }

    public ConfigurationPosition(String id){
        this.id = id;
    }

    @Override
    public ConfigurationPosition getParent() {
        return parent;
    }

    @Override
    public void setParent(ConfigurationPosition parent) {
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

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }
}
