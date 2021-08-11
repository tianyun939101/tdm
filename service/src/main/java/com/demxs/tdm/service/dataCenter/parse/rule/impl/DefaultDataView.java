package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import java.util.List;
import java.util.Map;

public class DefaultDataView extends AbstractDataView {

    @Override
    public void parse(List<String> dataLines) throws Exception {
        System.out.println(dataLines.size());
        System.out.println(dataLines);
    }

    @Override
    public String render() {
        return "";
    }

    @Override
    public String contrastRender(String name1, String name2, Map<String, String> metaInfo) {
        return "";
    }
}
