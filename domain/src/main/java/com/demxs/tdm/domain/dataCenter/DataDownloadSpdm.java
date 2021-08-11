package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * SPDM下载实体类
 */
public class DataDownloadSpdm extends DataEntity<DataDownloadSpdm> {

    private static final long serialVersionUID = 1L;

    private String dataId;//tdm数据ID

    private String orgCode;//操作部门

    private String userCode;//操作人员

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

