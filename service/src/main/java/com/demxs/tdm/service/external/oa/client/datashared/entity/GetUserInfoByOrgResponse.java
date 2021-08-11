
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
 *         &lt;element name="getUserInfoByOrgResponse" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
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
    "usermsg"
})
@XmlRootElement(name = "usermsg", namespace = "http://ideal.com/getUserInfoByOrg")
public class GetUserInfoByOrgResponse {

    @XmlElement(namespace = "http://ideal.com/getUserInfoByOrg", required = true)
    protected String usermsg;

    /**
     * 获取getUserInfoByOrgResponse属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsermsg() {
        return usermsg;
    }

    /**
     * 设置getUserInfoByOrgResponse属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsermsg(String value) {
        this.usermsg = value;
    }

}
