package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 算法入参实体类
 */
public class DataAlgorithmParameter extends DataEntity<DataAlgorithmParameter>{

    private static final long serialVersionUID = 1L;

    private String algorithmId;//算法ID

    private String parameterName;//参数名称

    private String parameterType;//参数类型

    private String parameterTypeVal;//参数类型val

    private String dataType;//数据类型

    private String dataTypeVal;//数据类型val

    private String parameterIndex;//参数索引

    public DataAlgorithmParameter() {
        super();
    }

    public DataAlgorithmParameter(String id){
        super(id);
    }

    public String getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public String getParameterTypeVal() {
        return parameterTypeVal;
    }

    public void setParameterTypeVal(String parameterTypeVal) {
        this.parameterTypeVal = parameterTypeVal;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getDataTypeVal() {
        return dataTypeVal;
    }

    public void setDataTypeVal(String dataTypeVal) {
        this.dataTypeVal = dataTypeVal;
    }

    public String getParameterIndex() {
        return parameterIndex;
    }

    public void setParameterIndex(String parameterIndex) {
        this.parameterIndex = parameterIndex;
    }
}