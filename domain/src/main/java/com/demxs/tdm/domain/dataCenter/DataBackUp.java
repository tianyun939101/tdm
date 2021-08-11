package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class DataBackUp  extends DataEntity<DataBackUp> {

    private static final long serialVersionUID = 1L;

    private String backPeroid;//备份周期

    private Date backTime;//备份时间

    private String attribute1;//是否自动备份


    private List<DataBackUpFile> dataBackUpFileList;

    public List<DataBackUpFile> getDataBackUpFileList() {
        return dataBackUpFileList;
    }

    public void setDataBackUpFileList(List<DataBackUpFile> dataBackUpFileList) {
        this.dataBackUpFileList = dataBackUpFileList;
    }

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

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
}
