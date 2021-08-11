package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

public class ZyLabTion extends DataEntity<ZyLabTion> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String labId;

    private String leafId;

    private String isFlag;


    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLeafId() {
        return leafId;
    }

    public void setLeafId(String leafId) {
        this.leafId = leafId;
    }
}
