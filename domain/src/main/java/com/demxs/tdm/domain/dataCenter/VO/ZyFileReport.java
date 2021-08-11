package com.demxs.tdm.domain.dataCenter.VO;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.dataCenter.DataBackUpFile;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ZyFileReport extends DataEntity<ZyFileReport> {

    private static final long serialVersionUID = 1L;

    private String fileType;//文件类型

    private String attachId;//文件ID

    private String userDate;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }
}
