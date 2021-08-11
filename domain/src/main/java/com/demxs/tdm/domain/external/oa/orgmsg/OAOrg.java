package com.demxs.tdm.domain.external.oa.orgmsg;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 * @author: Jason
 * @Date: 2020/5/18 14:51
 * @Description: 对接oa接口ORG组织机构对象
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OAOrg implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "usermsg")
    private UserMsg userMsg;
    /**
     * 部门唯一编码
     */
    @XmlAttribute
    private String orgCode;
    /**
     * 部门名称
     */
    @XmlAttribute
    private String orgName;
    /**
     * 父级部门唯一编码，如果为空，则为一级组织
     */
    @XmlAttribute
    private String parentOrgCode;
    /**
     * 组织类型
     */
    @XmlAttribute
    private String orgType;
    /**
     * 该组织排序位
     */
    @XmlAttribute
    private String orderNum;

    public String getOrgCode() {
        return orgCode;
    }

    public OAOrg setOrgCode(String orgCode) {
        this.orgCode = orgCode;
        return this;
    }

    public String getOrgName() {
        return orgName;
    }

    public OAOrg setOrgName(String orgName) {
        this.orgName = orgName;
        return this;
    }

    public String getParentOrgCode() {
        return parentOrgCode;
    }

    public OAOrg setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
        return this;
    }

    public String getOrgType() {
        return orgType;
    }

    public OAOrg setOrgType(String orgType) {
        this.orgType = orgType;
        return this;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public OAOrg setOrderNum(String orderNum) {
        this.orderNum = orderNum;
        return this;
    }

    public UserMsg getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(UserMsg userMsg) {
        this.userMsg = userMsg;
    }
}
