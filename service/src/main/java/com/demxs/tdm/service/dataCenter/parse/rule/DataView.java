package com.demxs.tdm.service.dataCenter.parse.rule;

import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;

import java.util.List;
import java.util.Map;

public interface DataView {

    void setParseRule(DataParseRule parseRule);

    String render();

    /**
    * @author Jason
    * @date 2020/5/7 14:03
    * @params [name1, name2, metaInfo]
    * 数据对比，分析两个文件的头部，需要提供文件名，和另一个文件头
    * @return java.lang.String
    */
    String contrastRender(String name1,String name2,Map<String,String> metaInfo);

    void parse(List<String> dataLines) throws Exception;

    List<String> getColumnData(String columnName);

    String getxAxis();

    Map<String, String> getMetaInfo();
}
