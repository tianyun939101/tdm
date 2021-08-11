package com.demxs.tdm.service.dataCenter.parse;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.dataCenter.parse.DataParsing;
import com.demxs.tdm.domain.dataCenter.parse.ParsedColumnData;
import com.demxs.tdm.service.dataCenter.parse.rule.impl.DefaultRule;
import com.demxs.tdm.service.dataCenter.parse.rule.Rule;

import java.io.IOException;

/**
 * @Auther: Jason
 * @Date: 2020/4/1 14:02
 * @Description:
 */
public class ParsingUtil {

    private static Rule rule;

    /**
    * @author Jason
    * @date 2020/4/16 8:55
    * @params [dataParsing]
    * 解析头部
    * @return void
    */
    public void parse(DataParsing dataParsing) throws IOException {

        if(StringUtils.isNotBlank(dataParsing.getRuleName())){

        }else{
            rule = new DefaultRule(dataParsing);
        }

        rule.parse();
    }

    /**
    * @author Jason
    * @date 2020/4/16 8:55
    * @params [index, referenceName, startVal, endVal]
    * 解析单列 正确顺序：先设置参照列，再设置数据区间，最后解析列
    * @return com.demxs.tdm.domain.dataCenter.parse.ParsedColumnData[]
    */
    public ParsedColumnData[] parse(String referenceName,String startVal,String endVal,
                                    Integer index){
        rule.setReference(referenceName);
        rule.setRange(startVal,endVal);
        return rule.parseDataRow(index);
    }

    public void setRange(String startVal,String endVal){
        rule.setRange(startVal,endVal);
    }

    public void getReference(String name){
        rule.setReference(name);
    }
}
