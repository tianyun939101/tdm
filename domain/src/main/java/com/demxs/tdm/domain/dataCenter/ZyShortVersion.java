package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

public class ZyShortVersion extends DataEntity<ZyShortVersion> {

    private static final long serialVersionUID = 1L;

    private String shortId;

    private String shortCode;

    private String remarks;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getShortId() {
        return shortId;
    }

    public void setShortId(String shortId) {
        this.shortId = shortId;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
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
