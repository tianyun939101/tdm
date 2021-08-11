package com.demxs.tdm.service.dataCenter.parse.rule.constant;

import com.demxs.tdm.service.dataCenter.parse.rule.DataView;
import com.demxs.tdm.service.dataCenter.parse.rule.impl.DefaultDataView;
import com.demxs.tdm.service.dataCenter.parse.rule.impl.TreeDataView;

public enum ViewType {
    DEFAULT(0, DefaultDataView.class),
    TREE_VIEW(1, TreeDataView.class);
    private Integer type;
    private Class<DataView> dataView;

    ViewType(Integer type, Class dataView) {
        this.type = type;
        this.dataView = dataView;
    }

    public Integer getType() {
        return type;
    }

    public Class<DataView> getDataView() {
        return dataView;
    }
}
