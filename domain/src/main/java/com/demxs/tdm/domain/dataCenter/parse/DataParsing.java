package com.demxs.tdm.domain.dataCenter.parse;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Jason
 * @Date: 2020/4/1 11:17
 * 数据解析对象
 * @Description:
 */
public class DataParsing extends DataEntity<DataParsing> {

    private static final long serialVersionUID = 1L;

    //规则说明
    private String ruleDescription;
    //文件id
    private String dataId;
    //字节数组
    private byte[] dataByte;
    //规则名称
    private String ruleName;
    //解析文件名
    private String dataName;
    //文件
    private MultipartFile data;
    //文件类型
    private String dataType;
    //表头开始行
    private Integer tableHeadStartRow;
    //表头结束行
    private Integer tableHeadEndRow;
    //表头分隔符
    private String tableHeadSplit;
    //数据头开始行
    private Integer dataHeadStartRow;
    //数据头分隔符
    private String dataHeadSplit;
    //数据行分隔符
    private String dataRowSplit;
    //表头信息
    private List<Map<String,String>> head;
    //列最大值
    private Double columnMax;
    //列头信息
    private List<String> columnHeadList;
    //当前列
    private Integer index;
    //分析后的列信息
    private ParsedColumnData[] columnDataList;
    //参照列
    private String referenceName;
    //参照列
    private ParsedColumnData[] reference;
    //解析区间
    private Integer start = -1;
    private Integer end = -1;
    private String startVal;
    private String endVal;

    //分析和的整个文本的每一行的信息
    private String[] rowList;

    //树形结构
    private List<Map<String,Object>> treeMap;

    public DataParsing() {
    }

    public DataParsing(String id) {
        super(id);
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public DataParsing setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
        return this;
    }

    public String getRuleName() {
        return ruleName;
    }

    public DataParsing setRuleName(String ruleName) {
        this.ruleName = ruleName;
        return this;
    }

    public String getDataName() {
        return dataName;
    }

    public DataParsing setDataName(String dataName) {
        this.dataName = dataName;
        return this;
    }

    public String getDataType() {
        return dataType;
    }

    public DataParsing setDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    public Integer getTableHeadStartRow() {
        return tableHeadStartRow;
    }

    public DataParsing setTableHeadStartRow(Integer tableHeadStartRow) {
        this.tableHeadStartRow = tableHeadStartRow;
        return this;
    }

    public Integer getTableHeadEndRow() {
        return tableHeadEndRow;
    }

    public DataParsing setTableHeadEndRow(Integer tableHeadEndRow) {
        this.tableHeadEndRow = tableHeadEndRow;
        return this;
    }

    public String getTableHeadSplit() {
        return tableHeadSplit;
    }

    public DataParsing setTableHeadSplit(String tableHeadSplit) {
        this.tableHeadSplit = tableHeadSplit;
        return this;
    }

    public Integer getDataHeadStartRow() {
        return dataHeadStartRow;
    }

    public DataParsing setDataHeadStartRow(Integer dataHeadStartRow) {
        this.dataHeadStartRow = dataHeadStartRow;
        return this;
    }

    public String getDataHeadSplit() {
        return dataHeadSplit;
    }

    public DataParsing setDataHeadSplit(String dataHeadSplit) {
        this.dataHeadSplit = dataHeadSplit;
        return this;
    }

    public String getDataRowSplit() {
        return dataRowSplit;
    }

    public DataParsing setDataRowSplit(String dataRowSplit) {
        this.dataRowSplit = dataRowSplit;
        return this;
    }

    public List<Map<String,String>> getHead() {
        return head;
    }

    public DataParsing setHead(List<Map<String,String>> head) {
        this.head = head;
        return this;
    }

    public List<String> getColumnHeadList() {
        return columnHeadList;
    }

    public DataParsing setColumnHeadList(List<String> columnHeadList) {
        this.columnHeadList = columnHeadList;
        return this;
    }

    public ParsedColumnData[] getColumnDataList() {
        return columnDataList;
    }

    public DataParsing setColumnDataList(ParsedColumnData[] columnDataList) {
        this.columnDataList = columnDataList;
        return this;
    }

    public MultipartFile getData() {
        return data;
    }

    public DataParsing setData(MultipartFile data) {
        this.data = data;
        return this;
    }

    public List<Map<String, Object>> getTreeMap() {
        return treeMap;
    }

    public DataParsing setTreeMap(List<Map<String, Object>> treeMap) {
        this.treeMap = treeMap;
        return this;
    }

    public String[] getRowList() {
        return rowList;
    }

    public DataParsing setRowList(String[] rowList) {
        this.rowList = rowList;
        return this;
    }

    public byte[] getDataByte() {
        return dataByte;
    }

    public DataParsing setDataByte(byte[] dataByte) {
        this.dataByte = dataByte;
        return this;
    }

    public String getDataId() {
        return dataId;
    }

    public DataParsing setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public Integer getIndex() {
        return index;
    }

    public DataParsing setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public ParsedColumnData[] getReference() {
        if(this.start != -1 && this.end != -1){
            ParsedColumnData[] newReference = new ParsedColumnData[end-start+1];
            for(int i = start,j = 0  ; i < end + 1; i ++,j ++ ){
                if(j > newReference.length){
                    break;
                }
                newReference[j] = reference[i];
            }
            return newReference;
        }
        return reference;
    }

    /**
    * @author Jason
    * @date 2020/4/16 9:40
    * @params [reference]
    * 设置参照列时把开始和结束值也设值进去
    * @return com.demxs.tdm.domain.dataCenter.parse.DataParsing
    */
    public DataParsing setReference(ParsedColumnData[] reference) {
        this.reference = reference;
        for(int i = 0 ; i < reference.length ; i ++ ){
            if(null != reference[i] && StringUtils.isNotBlank(reference[i].getVal())){
                this.startVal = reference[i].getVal();
                break;
            }
        }
        for(int i = reference.length - 1 ; i > 0 ; i -- ){
            if(null != reference[i] && StringUtils.isNotBlank(reference[i].getVal())){
                this.endVal = reference[i].getVal();
                break;
            }
        }
        return this;
    }

    public Double getColumnMax() {
        return columnMax;
    }

    public DataParsing setColumnMax(Double columnMax) {
        this.columnMax = columnMax;
        return this;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public DataParsing setReferenceName(String referenceName) {
        this.referenceName = referenceName;
        return this;
    }

    public Integer getStart() {
        return start;
    }

    public DataParsing setStart(Integer start) {
        this.start = start;
        return this;
    }

    public Integer getEnd() {
        return end;
    }

    public DataParsing setEnd(Integer end) {
        this.end = end;
        return this;
    }

    public String getStartVal() {
        return startVal;
    }

    public DataParsing setStartVal(String startVal) {
        this.startVal = startVal;
        return this;
    }

    public String getEndVal() {
        return endVal;
    }

    public DataParsing setEndVal(String endVal) {
        this.endVal = endVal;
        return this;
    }
}
