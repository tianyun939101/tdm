
package com.demxs.tdm.service.external.oa.client.datashared.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Organization complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="Organization"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="createTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="creator" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="displayOrgName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="hrOrderNum" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ip1From" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ip1To" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ip2From" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ip2To" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ip3From" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ip3To" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="jobType" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="leaf" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="modifier" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="modifyTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orderNum" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *         &lt;element name="orgFullCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orgFullName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orgLevel" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="orgNewCode" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="parentOrganization" type="{http://domain.organization.app.ideal.sh.cn}Organization"/&gt;
 *         &lt;element name="shortName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="state" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="treeChecked" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Organization", namespace = "http://domain.organization.app.ideal.sh.cn", propOrder = {
    "code",
    "createTime",
    "creator",
    "description",
    "displayOrgName",
    "hrOrderNum",
    "id",
    "ip1From",
    "ip1To",
    "ip2From",
    "ip2To",
    "ip3From",
    "ip3To",
    "jobType",
    "leaf",
    "modifier",
    "modifyTime",
    "name",
    "orderNum",
    "orgFullCode",
    "orgFullName",
    "orgLevel",
    "orgNewCode",
    "parentOrganization",
    "shortName",
    "state",
    "treeChecked",
    "type"
})
public class Organization {

    @XmlElement(required = true, nillable = true)
    protected String code;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createTime;
    @XmlElement(required = true, nillable = true)
    protected String creator;
    @XmlElement(required = true, nillable = true)
    protected String description;
    @XmlElement(required = true, nillable = true)
    protected String displayOrgName;
    @XmlElement(required = true, type = Long.class, nillable = true)
    protected Long hrOrderNum;
    @XmlElement(required = true, nillable = true)
    protected String id;
    @XmlElement(required = true, nillable = true)
    protected String ip1From;
    @XmlElement(required = true, nillable = true)
    protected String ip1To;
    @XmlElement(required = true, nillable = true)
    protected String ip2From;
    @XmlElement(required = true, nillable = true)
    protected String ip2To;
    @XmlElement(required = true, nillable = true)
    protected String ip3From;
    @XmlElement(required = true, nillable = true)
    protected String ip3To;
    @XmlElement(required = true, nillable = true)
    protected String jobType;
    protected boolean leaf;
    @XmlElement(required = true, nillable = true)
    protected String modifier;
    @XmlElement(required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifyTime;
    @XmlElement(required = true, nillable = true)
    protected String name;
    protected long orderNum;
    @XmlElement(required = true, nillable = true)
    protected String orgFullCode;
    @XmlElement(required = true, nillable = true)
    protected String orgFullName;
    @XmlElement(required = true, nillable = true)
    protected String orgLevel;
    @XmlElement(required = true, nillable = true)
    protected String orgNewCode;
    @XmlElement(required = true, nillable = true)
    protected Organization parentOrganization;
    @XmlElement(required = true, nillable = true)
    protected String shortName;
    @XmlElement(required = true, nillable = true)
    protected String state;
    protected boolean treeChecked;
    @XmlElement(required = true, nillable = true)
    protected String type;

    /**
     * 获取code属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * 获取createTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreateTime() {
        return createTime;
    }

    /**
     * 设置createTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreateTime(XMLGregorianCalendar value) {
        this.createTime = value;
    }

    /**
     * 获取creator属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置creator属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreator(String value) {
        this.creator = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取displayOrgName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDisplayOrgName() {
        return displayOrgName;
    }

    /**
     * 设置displayOrgName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDisplayOrgName(String value) {
        this.displayOrgName = value;
    }

    /**
     * 获取hrOrderNum属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getHrOrderNum() {
        return hrOrderNum;
    }

    /**
     * 设置hrOrderNum属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setHrOrderNum(Long value) {
        this.hrOrderNum = value;
    }

    /**
     * 获取id属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * 获取ip1From属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp1From() {
        return ip1From;
    }

    /**
     * 设置ip1From属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp1From(String value) {
        this.ip1From = value;
    }

    /**
     * 获取ip1To属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp1To() {
        return ip1To;
    }

    /**
     * 设置ip1To属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp1To(String value) {
        this.ip1To = value;
    }

    /**
     * 获取ip2From属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp2From() {
        return ip2From;
    }

    /**
     * 设置ip2From属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp2From(String value) {
        this.ip2From = value;
    }

    /**
     * 获取ip2To属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp2To() {
        return ip2To;
    }

    /**
     * 设置ip2To属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp2To(String value) {
        this.ip2To = value;
    }

    /**
     * 获取ip3From属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp3From() {
        return ip3From;
    }

    /**
     * 设置ip3From属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp3From(String value) {
        this.ip3From = value;
    }

    /**
     * 获取ip3To属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIp3To() {
        return ip3To;
    }

    /**
     * 设置ip3To属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIp3To(String value) {
        this.ip3To = value;
    }

    /**
     * 获取jobType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobType() {
        return jobType;
    }

    /**
     * 设置jobType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobType(String value) {
        this.jobType = value;
    }

    /**
     * 获取leaf属性的值。
     * 
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * 设置leaf属性的值。
     * 
     */
    public void setLeaf(boolean value) {
        this.leaf = value;
    }

    /**
     * 获取modifier属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifier() {
        return modifier;
    }

    /**
     * 设置modifier属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifier(String value) {
        this.modifier = value;
    }

    /**
     * 获取modifyTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifyTime() {
        return modifyTime;
    }

    /**
     * 设置modifyTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifyTime(XMLGregorianCalendar value) {
        this.modifyTime = value;
    }

    /**
     * 获取name属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取orderNum属性的值。
     * 
     */
    public long getOrderNum() {
        return orderNum;
    }

    /**
     * 设置orderNum属性的值。
     * 
     */
    public void setOrderNum(long value) {
        this.orderNum = value;
    }

    /**
     * 获取orgFullCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgFullCode() {
        return orgFullCode;
    }

    /**
     * 设置orgFullCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgFullCode(String value) {
        this.orgFullCode = value;
    }

    /**
     * 获取orgFullName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgFullName() {
        return orgFullName;
    }

    /**
     * 设置orgFullName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgFullName(String value) {
        this.orgFullName = value;
    }

    /**
     * 获取orgLevel属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgLevel() {
        return orgLevel;
    }

    /**
     * 设置orgLevel属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgLevel(String value) {
        this.orgLevel = value;
    }

    /**
     * 获取orgNewCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgNewCode() {
        return orgNewCode;
    }

    /**
     * 设置orgNewCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgNewCode(String value) {
        this.orgNewCode = value;
    }

    /**
     * 获取parentOrganization属性的值。
     * 
     * @return
     *     possible object is
     *     {@link Organization }
     *     
     */
    public Organization getParentOrganization() {
        return parentOrganization;
    }

    /**
     * 设置parentOrganization属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link Organization }
     *     
     */
    public void setParentOrganization(Organization value) {
        this.parentOrganization = value;
    }

    /**
     * 获取shortName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * 设置shortName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortName(String value) {
        this.shortName = value;
    }

    /**
     * 获取state属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getState() {
        return state;
    }

    /**
     * 设置state属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setState(String value) {
        this.state = value;
    }

    /**
     * 获取treeChecked属性的值。
     * 
     */
    public boolean isTreeChecked() {
        return treeChecked;
    }

    /**
     * 设置treeChecked属性的值。
     * 
     */
    public void setTreeChecked(boolean value) {
        this.treeChecked = value;
    }

    /**
     * 获取type属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
