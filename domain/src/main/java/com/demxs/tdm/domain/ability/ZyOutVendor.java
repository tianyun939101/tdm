package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.dataCenter.DataBackUpFile;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

public class ZyOutVendor extends DataEntity<ZyOutVendor> {

    private static final long serialVersionUID = 1L;

    private String name;//

    private String vendorCode;//

    private String remarks;


    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
    }
}
