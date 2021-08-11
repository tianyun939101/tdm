package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author wuhui
 * @date 2020/11/25 18:04
 **/
public class TestPiceSoftware extends DataEntity<TestPiceSoftware> {
    private String pieceId;
    private String softwareId;

    private SoftwareLibrary software;

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public String getSoftwareId() {
        return softwareId;
    }

    public void setSoftwareId(String softwareId) {
        this.softwareId = softwareId;
    }

    public SoftwareLibrary getSoftware() {
        return software;
    }

    public void setSoftware(SoftwareLibrary software) {
        this.software = software;
    }
}
