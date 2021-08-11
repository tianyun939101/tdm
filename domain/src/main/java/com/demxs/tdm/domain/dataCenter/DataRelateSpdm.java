package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.business.ATAChapter;

import java.util.Date;
import java.util.List;

/**
 * 关联SPDM 实体类
 */
public class DataRelateSpdm extends DataEntity<DataRelateSpdm>{

    private static final long serialVersionUID = 1L;

    private String spdmId;//spdm ID

    private String spdmUrl;//spdm数据访问地址

    private String spdmName;//spdm业务name

    private String businessId;//tdm业务ID

    private String testTaskId;//tdm试验任务ID

    private String dataId;//tdm数据ID

    private String orgCode;//操作部门

    private String userCode;//操作人员

    private String relateType;//关联类型

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

    public String getRelateType() {
        return relateType;
    }

    public void setRelateType(String relateType) {
        this.relateType = relateType;
    }

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
}