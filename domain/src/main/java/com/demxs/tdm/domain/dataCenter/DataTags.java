package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.List;
import java.util.Map;

/**
 * 数据标签
 */
public class DataTags extends DataEntity<DataTags>{

    private static final long serialVersionUID = 1L;

    private String dataFileId;//原始附件id

    private String parseFileId;//解析后附件id

    private List<Map<String,String>> tags;//标签

    public String getDataFileId() {
        return dataFileId;
    }

    public void setDataFileId(String dataFileId) {
        this.dataFileId = dataFileId;
    }

    public String getParseFileId() {
        return parseFileId;
    }

    public void setParseFileId(String parseFileId) {
        this.parseFileId = parseFileId;
    }

    public List<Map<String, String>> getTags() {
        return tags;
    }

    public void setTags(List<Map<String, String>> tags) {
        this.tags = tags;
    }
}