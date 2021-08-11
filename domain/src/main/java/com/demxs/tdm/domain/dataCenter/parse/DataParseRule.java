package com.demxs.tdm.domain.dataCenter.parse;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @Auther: neo
 * @Date: 2020/4/13 11:17
 * 数据解析对象
 * @Description:
 */
public class DataParseRule extends DataEntity<DataParseRule> {

    private static final long serialVersionUID = 1L;

    //规则说明
    private String ruleDescription;
    //试验分类
    private String labId;
    //试验分类名称
    private String labName;
    //规则名称
    private String name;
    //文件类型
    private String dataType;
    //表头开始行
    private Integer infoStart;
    //表头结束行
    private Integer infoEnd;
    //表头分隔符
    private String infoSplit;
    //数据头开始行
    private Integer dataStart;
    //数据头分隔符
    private String dataSplit;
    //数据行分隔符
    private String dataRowSplit;

    //是否只解析元数据信息
    private Boolean parseMetaInfo = false;
    //是否只获取列数据信息
    private Boolean columnData = false;
    //列名
    private String columnName;
    //时间列
    private String xAxis;

    public DataParseRule() {
    }

    public DataParseRule(String id) {
        super(id);
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getInfoStart() {
        return infoStart;
    }

    public void setInfoStart(Integer infoStart) {
        this.infoStart = infoStart;
    }

    public Integer getInfoEnd() {
        return infoEnd;
    }

    public void setInfoEnd(Integer infoEnd) {
        this.infoEnd = infoEnd;
    }

    public String getInfoSplit() {
        return infoSplit;
    }

    public void setInfoSplit(String infoSplit) {
        this.infoSplit = infoSplit;
    }

    public Integer getDataStart() {
        return dataStart;
    }

    public void setDataStart(Integer dataStart) {
        this.dataStart = dataStart;
    }

    public String getDataSplit() {
        return dataSplit;
    }

    public void setDataSplit(String dataSplit) {
        this.dataSplit = dataSplit;
    }

    public String getDataRowSplit() {
        return dataRowSplit;
    }

    public void setDataRowSplit(String dataRowSplit) {
        this.dataRowSplit = dataRowSplit;
    }

    public Boolean getParseMetaInfo() {
        return parseMetaInfo;
    }

    public void setParseMetaInfo(Boolean parseMetaInfo) {
        this.parseMetaInfo = parseMetaInfo;
    }

    public Boolean getColumnData() {
        return columnData;
    }

    public void setColumnData(Boolean columnData) {
        this.columnData = columnData;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }
}
