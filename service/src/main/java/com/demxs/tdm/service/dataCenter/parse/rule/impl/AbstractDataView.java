package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;
import com.demxs.tdm.service.dataCenter.parse.rule.DataParseException;
import com.demxs.tdm.service.dataCenter.parse.rule.DataView;
import com.demxs.tdm.service.dataCenter.parse.rule.constant.FileResolverType;
import com.demxs.tdm.service.dataCenter.parse.rule.constant.SplitType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractDataView implements DataView {
    //解析规则
    protected DataParseRule parseRule;
    //文件元信息
    protected Map<String,String> metaInfo = new HashMap<>();
    protected List<String> dataInfo = new ArrayList<>();
    //文件列信息
    protected Map<String, List<String>> dataRows = new HashMap<>();
    //时间列
    protected String xAxis;

    @Override
    public void parse(List<String> dataLines) throws Exception {
        loadMetaInfo(dataLines);
        loadDataInfo(dataLines);
    }

    @Override
    public List<String> getColumnData(String columnName) {
        return this.getDataRows().get(columnName);
    }

    /**
     * 加载数据元信息
     * @param dataLines
     */
    private void loadMetaInfo(List<String> dataLines){
        if(parseRule == null){
            throw new DataParseException("未定义解析规则");
        }
        int start = parseRule.getInfoStart()-1;
        Integer end = parseRule.getInfoEnd();
        String split = parseRule.getInfoSplit();
        SplitType splitType = SplitType.get(split);
        Integer dataType = Integer.valueOf(parseRule.getDataType());
        if(dataType.equals( FileResolverType.CSV.getValue()) || dataType.equals(FileResolverType.EXCEL.getValue())){
            splitType = SplitType.DEFAULT;
        }
        for (int i = start; i < end; i++) {
            String metaInfoLine = dataLines.get(i);
            String[] infoStrArr = metaInfoLine.split(splitType.getTxt());
            /*if(infoStrArr.length > 2){
                throw new DataParseException("头信息解析失败");
            }*/
            if(infoStrArr.length==1){
                this.metaInfo.put(infoStrArr[0],"");
                continue;
            }
            this.metaInfo.put(infoStrArr[0],infoStrArr[1]);
        }
    }

    /**
     * 加载数据信息
     * @param dataLines
     */
    private void loadDataInfo(List<String> dataLines){
        int start = parseRule.getDataStart()-1;
        String split = parseRule.getDataSplit();
        String rowSplit = parseRule.getDataRowSplit();
        SplitType splitType = SplitType.get(split);
        SplitType rowSplitType = SplitType.get(rowSplit);
        Integer dataType = Integer.valueOf(parseRule.getDataType());
        if(dataType.equals(FileResolverType.CSV.getValue()) || dataType.equals(FileResolverType.EXCEL.getValue())){
            splitType = SplitType.DEFAULT;
            rowSplitType = SplitType.DEFAULT;
        }
        String headerInfoLine = dataLines.get(start);
        String[] headerInfoStrArr = headerInfoLine.split(splitType.getTxt());
        for (String headerInfo : headerInfoStrArr) {
            this.dataInfo.add(headerInfo);
            this.dataRows.put(headerInfo,new ArrayList<>());
        }
        for (int i = (start+1); i < dataLines.size(); i++) {
            String dataLine = dataLines.get(i);
            String[] dataRowArr = dataLine.split(rowSplitType.getTxt());
            for (int j = 0; j < dataRowArr.length; j++) {
                if(j < headerInfoStrArr.length){
                    String header = headerInfoStrArr[j];
                    String item = dataRowArr[j];
                    if(StringUtils.isNotEmpty(item) && item.indexOf(":")>0){
                        this.xAxis = header;
                        this.dataInfo.remove(header);
                        //break;
                    }
                    this.dataRows.get(header).add(dataRowArr[j]);
                }
            }
        }
    }

    public void setParseRule(DataParseRule parseRule){
        this.parseRule = parseRule;
    }

    public Map<String, String> getMetaInfo() {
        return metaInfo;
    }

    public List<String> getDataInfo() {
        return dataInfo;
    }

    public Map<String, List<String>> getDataRows() {
        return dataRows;
    }

    public String getxAxis() {
        return xAxis;
    }
}
