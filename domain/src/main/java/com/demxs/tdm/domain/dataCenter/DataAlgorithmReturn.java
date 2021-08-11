package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 算法返回值实体类
 */
public class DataAlgorithmReturn extends DataEntity<DataAlgorithmReturn>{

    private static final long serialVersionUID = 1L;

    private String algorithmId;//算法ID

    private String returnName;//返回值名称

    private String returnType;//返回值类型

    private String returnTypeVal;//返回值类型val

    private String returnIndex;//返回值索引

    public DataAlgorithmReturn() {
        super();
    }

    public DataAlgorithmReturn(String id){
        super(id);
    }

    public String getAlgorithmId() {
        return algorithmId;
    }

    public void setAlgorithmId(String algorithmId) {
        this.algorithmId = algorithmId;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getReturnTypeVal() {return returnTypeVal; }

    public void setReturnTypeVal(String returnTypeVal) {this.returnTypeVal = returnTypeVal; }

    public String getReturnIndex() {
        return returnIndex;
    }

    public void setReturnIndex(String returnIndex) {
        this.returnIndex = returnIndex;
    }
}