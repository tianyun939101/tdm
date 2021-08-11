package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

public class ZyFileCheckRelation extends DataEntity<ZyFileCheckRelation> {

    private static final long serialVersionUID = 1L;

    private String fileId;//文件id

    private String attributeId;//文件自查ID

    private String isFlag;//SHIFOU满足


    private String remarks;//问题说明

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(String attributeId) {
        this.attributeId = attributeId;
    }

    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
