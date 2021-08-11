package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.dataCenter.parse.DataParsing;
import com.demxs.tdm.domain.dataCenter.parse.ParsedColumnData;

import java.util.*;

/**
 * @Auther: Jason
 * @Date: 2020/4/3 11:07
 * @Description:
 */
public class DefaultRule extends ParseRule {

    public DefaultRule(DataParsing dataParsing){
        super.dataParsing = dataParsing;
    }

    @Override
    public void parseTableHead() {
        int start = super.dataParsing.getTableHeadStartRow()-1;
        int end = super.dataParsing.getTableHeadEndRow()-1;

        String headSymbol = super.dataParsing.getTableHeadSplit();

        List<Map<String, String>> list = new ArrayList<>();
        for(int i = start; i <= end; i++){
            String row = rowList[i];
            Map<String, String> headMap = new HashMap<>();
            if(StringUtils.isNotBlank(row) && row.contains(headSymbol)){
                String[] split = row.split(headSymbol);
                if(split.length > 1){
                    headMap.put(split[0],split[1]);
                    list.add(headMap);
                }
            }else{
                throw new RuntimeException("数据头有误");
            }
        }
        super.dataParsing.setHead(list);
    }

    @Override
    public void parseDataHead() {
        //获取数据头起始行
        int start = super.dataParsing.getDataHeadStartRow()-1;

        String headSymbol = super.dataParsing.getDataHeadSplit();
        String row = rowList[start];

        if(StringUtils.isNotBlank(row) && row.contains(headSymbol)){
            String[] dataHead = row.split(headSymbol);
            List<String> list = new ArrayList<>(Arrays.asList(dataHead));
            super.dataParsing.setColumnHeadList(list);
        }
    }

    @Override
    public ParsedColumnData[] parseDataRow(Integer index) {
        //获取数据头起始行
        int start = super.dataParsing.getDataHeadStartRow();
        String[] headRow = rowList[start-1].split(super.dataParsing.getDataHeadSplit());
        //获取分隔符
        String symbol = super.dataParsing.getDataRowSplit();
        // 循环体外获取length提高效率
        int length = rowList.length;

        ParsedColumnData[] list = new ParsedColumnData[length];
        int curIndex = 0;
        if(null == index || index < 0){
            index = 0;
        }
        //该列最大值
        double maxVal = 0;

        //获取数据区间
        Integer start1 = super.dataParsing.getStart();
        Integer end = super.dataParsing.getEnd();
        if(start1 != -1 && end != -1){
            //加上数据头起始行号
            start = start1 + start -1;
            length = end + start;
        }

        for(int i = start; i < length; i++){
            String row = rowList[i];
            if(StringUtils.isNotBlank(row)){
                String[] split = row.split(symbol);
                int count = 0;
                for(int j = 0 ; j < headRow.length; j ++){
                    //只解析指定列
                    if(j != index){
                        continue;
                    }else{
                        curIndex++;
                        if(j == 0){
                            count = j;
                        }
                        ParsedColumnData columnData = new ParsedColumnData();
                        if(row.contains(symbol)){
                            if(j <= split.length-1 && StringUtils.isNotBlank(split[j])){
                                String val = split[j];
                                columnData.setRowNum(i+1+"");
                                columnData.setHeadName(headRow[j]);
                                columnData.setVal(val);
                                list[curIndex] = columnData;
                                if(val.matches(super.numReg)){
                                    double num = 0;
                                    num = Double.parseDouble(val);
                                    if(num > maxVal){
                                        maxVal = num + super.granularity;
                                    }
                                }
                            }else{
                                columnData.setRowNum(i+1+"");
                                columnData.setHeadName(headRow[j]);
                                columnData.setVal("");
                                list[curIndex] = columnData;
                            }
                        }else{
                            columnData.setRowNum(i+1+"");
                            columnData.setHeadName(headRow[j]);
                            if(count == 0){
                                columnData.setVal(split[0]);
                            }else{
                                columnData.setVal("");
                            }
                            list[curIndex] = columnData;
                            count = 1;
                        }
                    }
                }
            }else{
                for(int j = 0 ; j < headRow.length; j ++){
                    curIndex++;
                    ParsedColumnData columnData = new ParsedColumnData();
                    columnData.setRowNum(i+1+"");
                    columnData.setHeadName(headRow[j]);
                    columnData.setVal("");
                    list[curIndex] = columnData;
                }
            }
        }
        super.dataParsing.setColumnMax(maxVal);
        return list;
    }

    @Override
    public void setReference(String name) {
        if(super.dataParsing.getColumnHeadList() == null || super.dataParsing.getHead().isEmpty()){
            return;
        }
        //默认参照列
        if(StringUtils.isBlank(name)){
            name = super.REFERENCE;
        }else{
            name = name.toUpperCase();
        }

        List<String> head = super.dataParsing.getColumnHeadList();
        int index = -1 ;
        for(int i = 0 ; i < head.size(); i ++){
            if(name.equals(head.get(i).toUpperCase())){
                index = i;
                break;
            }
        }

        if(index != -1){
            ParsedColumnData[] list = this.parseDataRow(index);
            super.dataParsing.setReference(list);
        }
    }

    @Override
    public void setRange(String startVal,String endVal) {
        //参照列
        ParsedColumnData[] reference = super.dataParsing.getReference();
        if(StringUtils.isNotBlank(startVal) && StringUtils.isNotBlank(endVal) &&
                null != reference && reference.length > 0){

            int start = -1;
            int end = -1;
            //循环体外获取length提高效率
            int length = reference.length;
            String startReg = "^"+startVal+".*";
            String endReg = "^"+endVal+".*";
            for(int i = 0 ; i < length ; i ++ ){
                ParsedColumnData data = reference[i];
                if(null != data){
                    String val = data.getVal();
                    if(StringUtils.isNotBlank(val)){
                        if(start == -1 && val.matches(startReg)){
                            start = i;
                        }
                        if(end == -1 && val.matches(endReg)){
                            end = i;
                        }
                    }
                }
                if(start != -1 && end != -1){
                    break;
                }
            }

            if(start == -1 || end == -1){
                throw new RuntimeException("数据区间获取异常");
            }else if(end <= start){
                end = start + 1;
            }

            super.dataParsing.setStart(start);
            super.dataParsing.setEnd(end);
        }
    }
}
