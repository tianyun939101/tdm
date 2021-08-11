package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class DataFile extends DataEntity<DataFile> {

    private static final long serialVersionUID = 1L;

    private String fileName;//备份周期

    private String attachId;//备份时间


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }
}
