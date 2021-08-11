package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class DataInterface extends DataEntity<DataInterface> {

    private static final long serialVersionUID = 1L;

    private String dataId;//数据ID

    private String dataNo;//数据编号

    private String dataType;

    private String status;//状态


    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getDataNo() {
        return dataNo;
    }

    public void setDataNo(String dataNo) {
        this.dataNo = dataNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
