
package com.demxs.tdm.service.external.oa.client.datashared.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="orgList" type="{http://ideal.com/user}ArrayOf_xsd_anyType"/&gt;
 *         &lt;element name="org" type="{http://domain.organization.app.ideal.sh.cn}Organization"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "orgList",
    "org"
})
@XmlRootElement(name = "getOrgSTXML", namespace = "http://webservice.user.app.ideal.sh.cn")
public class GetOrgSTXML {

    @XmlElement(namespace = "http://webservice.user.app.ideal.sh.cn", required = true)
    protected ArrayOfXsdAnyType orgList;
    @XmlElement(namespace = "http://webservice.user.app.ideal.sh.cn", required = true)
    protected Organization org;

    /**
     * 获取orgList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfXsdAnyType }
     *     
     */
    public ArrayOfXsdAnyType getOrgList() {
        return orgList;
    }

    /**
     * 设置orgList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfXsdAnyType }
     *     
     */
    public void setOrgList(ArrayOfXsdAnyType value) {
        this.orgList = value;
    }

    /**
     * 获取org属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getOrg() {
        return org;
    }

    /**
     * 设置org属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setOrg(Organization value) {
        this.org = value;
    }

}
