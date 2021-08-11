package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class DataBackUpFile extends DataEntity<DataBackUpFile> {

    private static final long serialVersionUID = 1L;

    private String backPeroid;//备份周期

    private Date backTime;//备份时间

    private String fileName;//文件名称

    private String fileSize;//文件大小

    private String filePath;//文件路径

    private Date insertDate;//生成备份时间

    private String backId;//备份周期


    private String attribute1;//是否手动


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBackPeroid() {
        return backPeroid;
    }

    public void setBackPeroid(String backPeroid) {
        this.backPeroid = backPeroid;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBackTime() {
        return backTime;
    }

    public void setBackTime(Date backTime) {
        this.backTime = backTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getBackId() {
        return backId;
    }

    public void setBackId(String backId) {
        this.backId = backId;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
}
