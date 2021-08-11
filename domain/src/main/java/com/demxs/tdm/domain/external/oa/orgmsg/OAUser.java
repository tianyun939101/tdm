package com.demxs.tdm.domain.external.oa.orgmsg;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author: Jason
 * @Date: 2020/5/18 14:50
 * @Description: 对接oa接口user实体
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class OAUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一性标志位，通常为门户登录名 eg:100001
     */
    private String uid;
    /**
     * 中文名
     */
    private String userName;
    /**
     * 拼音名
     */
    private String nameAlphabet;
    /**
     * 员工工号
     */
    private String employeeNumber;
    /**
     * 密码
     */
    private String password;
    /**
     * 电子邮件
     */
    private String email;
    /**
     * 员工类型
     */
    private String employeeType;
    /**
     * 性别
     */
    private String sex;
    /**
     * 身份证号
     */
    private String pid;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 办公电话
     */
    private String tel;
    /**
     * 传真
     */
    private String fax;
    /**
     * 办公地址
     */
    private String location;
    /**
     * 岗位名称
     */
    private String postName;
    /**
     * 岗位名称的编码
     */
    private String postCode;
    /**
     * 岗位等级
     */
    private String postLevel;
    /**
     * 岗位等级的编码
     */
    private String postLevelCode;
    /**
     * <org>
     *      <orgName>人力资源部</orgName> //部门名称，不可根据员工关联的部门直接判断其所在单位
     *      <orgCode>05200188</orgCode> //部门编码
     *      <orderNum>1</orderNum>//人员在该组织下的排序位
     *      <jobType>0</jobType >//主从单位标志,0:在职，1：兼职，2：借用，3:交流，4：外派
     * </org>
     */

    @XmlElement
    private Org org;

    public String getUid() {
        return uid;
    }

    public OAUser setUid(String uid) {
        this.uid = uid;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public OAUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getNameAlphabet() {
        return nameAlphabet;
    }

    public OAUser setNameAlphabet(String nameAlphabet) {
        this.nameAlphabet = nameAlphabet;
        return this;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public OAUser setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OAUser setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public OAUser setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public OAUser setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public OAUser setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public OAUser setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getMobile() {
        return mobile;
    }

    public OAUser setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getTel() {
        return tel;
    }

    public OAUser setTel(String tel) {
        this.tel = tel;
        return this;
    }

    public String getFax() {
        return fax;
    }

    public OAUser setFax(String fax) {
        this.fax = fax;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public OAUser setLocation(String location) {
        this.location = location;
        return this;
    }

    public String getPostName() {
        return postName;
    }

    public OAUser setPostName(String postName) {
        this.postName = postName;
        return this;
    }

    public String getPostCode() {
        return postCode;
    }

    public OAUser setPostCode(String postCode) {
        this.postCode = postCode;
        return this;
    }

    public String getPostLevel() {
        return postLevel;
    }

    public OAUser setPostLevel(String postLevel) {
        this.postLevel = postLevel;
        return this;
    }

    public String getPostLevelCode() {
        return postLevelCode;
    }

    public OAUser setPostLevelCode(String postLevelCode) {
        this.postLevelCode = postLevelCode;
        return this;
    }

    public Org getOrg() {
        return org;
    }

    public OAUser setOrg(Org org) {
        this.org = org;
        return this;
    }
}
