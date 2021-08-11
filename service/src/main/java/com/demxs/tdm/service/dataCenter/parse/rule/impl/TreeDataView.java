package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.IdGen;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TreeDataView extends AbstractDataView {
    @Override
    public String render() {
        Map<String, String> metaInfo = this.getMetaInfo();
        List<String> dataInfo = this.getDataInfo();
        List<TreeModel> nodes = new ArrayList<>();
        for (String key:metaInfo.keySet()){
            TreeModel treeModel = new TreeModel(IdGen.uuid(),"1",key+":"+metaInfo.get(key));
            nodes.add(treeModel);
        }
        for (String key:dataInfo){
            TreeModel treeModel = new TreeModel(IdGen.uuid(),"2",key);
            nodes.add(treeModel);
        }
        return new String(JsonMapper.toJsonString(nodes).getBytes(), StandardCharsets.UTF_8);
    }

    @Override
    public String contrastRender(String name1,String name2,Map<String, String> contrastMetaInfo) {
        Map<String, String> metaInfo = this.getMetaInfo();
        List<String> dataInfo = this.getDataInfo();
        List<TreeModel> nodes = new ArrayList<>();
        for (String key:metaInfo.keySet()){
            TreeModel treeModel = new TreeModel(IdGen.uuid(),"1",name1+key+":"+metaInfo.get(key));
            nodes.add(treeModel);
        }
        for (String key:contrastMetaInfo.keySet()){
            TreeModel treeModel = new TreeModel(IdGen.uuid(),"1",name2+key+":"+contrastMetaInfo.get(key));
            nodes.add(treeModel);
        }
        for (String key:dataInfo){
            TreeModel treeModel = new TreeModel(IdGen.uuid(),"2",key);
            nodes.add(treeModel);
        }
        return new String(JsonMapper.toJsonString(nodes).getBytes(), StandardCharsets.UTF_8);
    }
}
