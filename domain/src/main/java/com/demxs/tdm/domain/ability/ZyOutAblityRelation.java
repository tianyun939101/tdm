package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;

public class ZyOutAblityRelation extends DataEntity<ZyOutAblityRelation> {

    private static final long serialVersionUID = 1L;

    private String version;

    private String suplierId;

    private String remarks;

    private String vendorName;

    private String versionName;

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    private ZyOutVendor  zyOutVendor;


    public ZyOutVendor getZyOutVendor() {
        return zyOutVendor;
    }

    public void setZyOutVendor(ZyOutVendor zyOutVendor) {
        this.zyOutVendor = zyOutVendor;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSuplierId() {
        return suplierId;
    }

    public void setSuplierId(String suplierId) {
        this.suplierId = suplierId;
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
