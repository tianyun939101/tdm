package com.demxs.tdm.domain.dataCenter.VO;

import java.util.Date;

/**
 * spdm数据关联VO
 */
public class DataRelevanceSpdmVO {

    private static final long serialVersionUID = 1L;

    private String spdmId;

    private String spdmName;

    private String businessId;

    private String testTaskId;

    private String dataId;

    private String orgCode;

    private String userCode;

    private String spdmUrl;

    public String getSpdmUrl() {
        return spdmUrl;
    }

    public void setSpdmUrl(String spdmUrl) {
        this.spdmUrl = spdmUrl;
    }

    public String getSpdmName() {
        return spdmName;
    }

    public void setSpdmName(String spdmName) {
        this.spdmName = spdmName;
    }

    public String getSpdmId() {
        return spdmId;
    }

    public void setSpdmId(String spdmId) {
        this.spdmId = spdmId;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getTestTaskId() {
        return testTaskId;
    }

    public void setTestTaskId(String testTaskId) {
        this.testTaskId = testTaskId;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}


