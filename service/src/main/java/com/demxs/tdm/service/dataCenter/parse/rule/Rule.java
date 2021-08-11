package com.demxs.tdm.service.dataCenter.parse.rule;

import com.demxs.tdm.domain.dataCenter.parse.DataParsing;
import com.demxs.tdm.domain.dataCenter.parse.ParsedColumnData;

import java.io.IOException;

/**
 * @Auther: Jason
 * @Date: 2020/4/3 11:10
 * 解析规则
 * @Description:
 */
public interface Rule {

    /**
    * @author Jason
    * @date 2020/4/15 13:42
    * @params []
    * 解析
    * @return com.demxs.tdm.domain.dataCenter.parse.DataParsing
    */
    DataParsing parse() throws IOException;

    /**
    * @author Jason
    * @date 2020/4/15 13:42
    * @params []
    * 解析文件头
    * @return void
    */
    void parseTableHead();

    /**
    * @author Jason
    * @date 2020/4/15 13:42
    * @params []
    * 解析表头
    * @return void
    */
    void parseDataHead();

    /**
    * @author Jason
    * @date 2020/4/15 13:43
    * @params [index] 指定列
    * 解析单列
    * @return com.demxs.tdm.domain.dataCenter.parse.ParsedColumnData[]
    */
    ParsedColumnData[] parseDataRow(Integer index);

    /**
    * @author Jason
    * @date 2020/4/15 13:43
    * @params []
    * 设置zTree节点
    * @return void
    */
    void setTreeMap();

    /**
    * @author Jason
    * @date 2020/4/15 13:43
    * @params [name]
    * 设置参照列
    * @return void
    */
    void setReference(String name);

    /**
     * @author Jason
     * @date 2020/4/15 14:00
     * @params  * @param null
     * 设置区间
     * @return
     */
    void setRange(String startVal,String endVal);
}
