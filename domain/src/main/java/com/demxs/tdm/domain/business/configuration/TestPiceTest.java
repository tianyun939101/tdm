package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author zwm
 * @date 2020/12/2 18:04
 **/
public class TestPiceTest extends DataEntity<TestPiceTest> {
    private String pieceId;
    private String warnName;
    private String warnCode;
    private String warnVersion;
    private String warnSerial;  //序列号

    public String getWarnSerial() {
        return warnSerial;
    }

    public void setWarnSerial(String warnSerial) {
        this.warnSerial = warnSerial;
    }

    public String getPieceId() {
        return pieceId;
    }

    public void setPieceId(String pieceId) {
        this.pieceId = pieceId;
    }

    public String getWarnName() {
        return warnName;
    }

    public void setWarnName(String warnName) {
        this.warnName = warnName;
    }

    public String getWarnCode() {
        return warnCode;
    }

    public void setWarnCode(String warnCode) {
        this.warnCode = warnCode;
    }

    public String getWarnVersion() {
        return warnVersion;
    }

    public void setWarnVersion(String warnVersion) {
        this.warnVersion = warnVersion;
    }
}
