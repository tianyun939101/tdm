package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.dataCenter.parse.DataParsing;
import com.demxs.tdm.domain.dataCenter.parse.constant.Symbol;
import com.demxs.tdm.service.dataCenter.parse.rule.Rule;
import com.google.common.collect.Lists;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: Jason
 * @Date: 2020/4/3 11:03
 * @Description:
 */
public abstract class ParseRule implements Rule {

    DataParsing dataParsing;
    BufferedInputStream bis;
    byte[] data;
    String[] rowList;
    String REFERENCE = "TIME";
    String numReg = "^-?([0-9]\\d*|[0-9]\\d*\\.\\d{1,}|0\\.\\d{1,})$";
    Double granularity = 0.01D;
    Integer type = 0;

    private final static String LINE_FEED = "\r\n";

    protected void init() throws IOException {

        //获取文件：0从服务器本机获取、1用户手动上传
        if(dataParsing.getData() == null || dataParsing.getData().getBytes().length == 0){
            data = dataParsing.getDataByte();
            type = 0;
        }else{
            bis = new BufferedInputStream(dataParsing.getData().getInputStream());
            data = new byte[bis.available()];
            bis.read(data);
            bis.close();
            type = 1;
        }

        //转为字符串
        String resource = new String(data, "UTF-8");
        //全文解析
        rowList = resource.split(LINE_FEED);

        //初始化间隔符
        if(Symbol.SPACE.getSymbol().equals(dataParsing.getTableHeadSplit())){
            dataParsing.setTableHeadSplit(Symbol.SPACE.getVal());
        }else if(Symbol.TABLE.getSymbol().equals(dataParsing.getTableHeadSplit())){
            dataParsing.setTableHeadSplit(Symbol.TABLE.getVal());
        }else if(StringUtils.isBlank(dataParsing.getTableHeadSplit())){
            dataParsing.setTableHeadSplit(Symbol.COLON.getVal());
        }

        if(Symbol.SPACE.getSymbol().equals(dataParsing.getDataHeadSplit())){
            dataParsing.setDataHeadSplit(Symbol.SPACE.getVal());
        }else if(Symbol.TABLE.getSymbol().equals(dataParsing.getDataHeadSplit())){
            dataParsing.setDataHeadSplit(Symbol.TABLE.getVal());
        }else if(StringUtils.isBlank(dataParsing.getDataHeadSplit())){
            dataParsing.setDataHeadSplit(Symbol.TABLE.getVal());
        }

        if(Symbol.SPACE.getSymbol().equals(dataParsing.getDataRowSplit())){
            dataParsing.setDataRowSplit(Symbol.SPACE.getVal());
        }else if(Symbol.TABLE.getSymbol().equals(dataParsing.getDataRowSplit())){
            dataParsing.setDataRowSplit(Symbol.TABLE.getVal());
        }else if(StringUtils.isBlank(dataParsing.getDataRowSplit())){
            dataParsing.setDataRowSplit(Symbol.TABLE.getVal());
        }

        Integer start = this.dataParsing.getDataHeadStartRow();
        if(null == start || start < 0){
            throw new RuntimeException("数据头开始行有误");
        }

        Integer tableStart = this.dataParsing.getTableHeadStartRow()-1;
        Integer end = this.dataParsing.getTableHeadEndRow()-1;

        if(null == end || null == tableStart || end - tableStart <= 0){
            throw new RuntimeException("数据头行号有误");
        }else if(rowList.length == 1 && StringUtils.isEmpty(rowList[0])){
            throw new RuntimeException("文件有误");
        }

        if(this.dataParsing.getDataHeadStartRow() < 0){
            this.dataParsing.setDataHeadStartRow(0);
        }
    }

    @Override
    public DataParsing parse() throws IOException {
        this.init();
        this.parseTableHead();
        this.parseDataHead();
        this.setTreeMap();
        //this.dataParsing.setRowList(rowList);
        if(this.type == 0){
            this.data = null;
        }
        return this.dataParsing;
    }

    @Override
    public void setTreeMap() {
        String name = "";
        if(this.type == 0){
            name = this.dataParsing.getDataName();
        }else if(this.type == 1){
            name = this.dataParsing.getData().getOriginalFilename();
        }
        List<Map<String, Object>> mapList = Lists.newArrayList();
        //三个固定节点
        Map<String, Object> dataNameMap = new HashMap<>();
        dataNameMap.put("id","0");
        dataNameMap.put("pId",null);
        dataNameMap.put("name",name);
        mapList.add(dataNameMap);

        Map<String, Object> tableHeadMap = new HashMap<>();
        tableHeadMap.put("id","1");
        tableHeadMap.put("pId","0");
        tableHeadMap.put("name","头信息");
        mapList.add(tableHeadMap);

        Map<String, Object> columnHeadMap = new HashMap<>();
        columnHeadMap.put("id","2");
        columnHeadMap.put("pId","0");
        columnHeadMap.put("name","数据信息");
        mapList.add(columnHeadMap);

        List<Map<String, String>> head = this.dataParsing.getHead();
        for(int i = 0; i < head.size(); i ++ ){
            Map<String, String> map = head.get(i);
            for(String s : map.keySet()){
                String val = "数据头信息"+(i+1) + "：" + s + this.dataParsing.getTableHeadSplit() + map.get(s);
                Map<String, Object> m = new HashMap<>();
                m.put("id",i+3);
                m.put("pId",1);
                m.put("name",val);
                mapList.add(m);
            }
        }

        /*int dataHeadStart = Integer.parseInt(this.dataParsing.getDataHeadStartRow());
        for(int i = dataHeadStart ; i < this.rowList.length; i ++ ){
            String row = rowList[i];
            Map<String, Object> m = new HashMap<>();
            m.put("id",i+mapList.size());
            m.put("pId",2);
            m.put("name","行"+(i+1)+"："+row);
            mapList.add(m);
        }*/

        List<String> columnHeadList = this.dataParsing.getColumnHeadList();
        String referenceName = this.dataParsing.getReferenceName();
        if(StringUtils.isBlank(referenceName)){
            referenceName = this.REFERENCE;
        }
        for(int i = 0 ; i < columnHeadList.size(); i ++ ){
            Map<String, Object> m = new HashMap<>();
            m.put("id",i+mapList.size());
            m.put("pId",2);
            m.put("name",columnHeadList.get(i));
            if(referenceName.equals(columnHeadList.get(i))){
                m.put("index",-1);
            }else{
                m.put("index",i);
            }
            mapList.add(m);
        }
        this.dataParsing.setTreeMap(mapList);
    }
}
