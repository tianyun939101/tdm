package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

public class ZyDownLoadLog extends DataEntity<ZyDownLoadLog> {

    private static final long serialVersionUID = 1L;

    private String dataId;//数据ID
    private String dataNames;

    private String fileLength;//文件大小

    private String applicant;//申请人
    private String applicantName;
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getFileLength() {
        return fileLength;
    }

    public void setFileLength(String fileLength) {
        this.fileLength = fileLength;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getDataNames() {
        return dataNames;
    }

    public void setDataNames(String dataNames) {
        this.dataNames = dataNames;
    }
}
