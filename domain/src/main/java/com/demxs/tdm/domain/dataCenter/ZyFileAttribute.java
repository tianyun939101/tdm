package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ZyFileAttribute extends DataEntity<ZyFileAttribute> {

    private static final long serialVersionUID = 1L;

    private String fileType;//文件列别

    private String content;//检查内容

    private String checkLevel;//检查级别

    private String checkContent;//检查项

    private String isFlag;//是否满足

    private String isCheck;//是否必查


    private String remarks;//问题说明

    private String num;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCheckLevel() {
        return checkLevel;
    }

    public void setCheckLevel(String checkLevel) {
        this.checkLevel = checkLevel;
    }

    public String getCheckContent() {
        return checkContent;
    }

    public void setCheckContent(String checkContent) {
        this.checkContent = checkContent;
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
