package com.demxs.tdm.domain.external.oa.usermsg;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

/**
 * @author: Jason
 * @Date: 2020/5/21 16:29
 * @Description:
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Org implements Serializable {

    private String orgName;
    private String orgCode;
    private String orderNum;

    public String getOrgName() {
        return orgName;
    }

    public Org setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public Org setOrgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public Org setOrderNum(String orderNum) {
        this.orderNum = orderNum;
        return this;
    }
}
