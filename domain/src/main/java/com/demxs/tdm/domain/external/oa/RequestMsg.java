package com.demxs.tdm.domain.external.oa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "requestMsg")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestMsg {

    private String ssoQuery = "1";
    private String sysCode = "tdm";
    private String orgCode;

    public String getSsoQuery() {
        return ssoQuery;
    }

    public void setSsoQuery(String ssoQuery) {
        this.ssoQuery = ssoQuery;
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}
