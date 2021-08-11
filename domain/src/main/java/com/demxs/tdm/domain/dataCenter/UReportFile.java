package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

public class UReportFile extends DataEntity<UReportFile> {

    private static final long serialVersionUID = 1L;

    private String name;//报表名称

    private byte[] content;//报表内容

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
