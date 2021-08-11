package com.demxs.tdm.domain.external;


import java.util.Date;
/**
 * Created by zhaoqi2 on 2017-12-01.
 */
public class EhrEmployeeResult {
    private static final long serialVersionUID = 1L;
    private  String emplId;              //员工ID
    private  String name;                //姓名
    private  String nameAc;              //英文名
    private  String sex;                 //性别：M-男；F-女；U-未知
    private  String mobilePhone;        //移动电话
    private  String emailAddr;          //公司邮箱
    private  String hrStatus;           //HR状态A-在职；I-离职(用户不能登录lims)
    private  String company;            //公司
    private  String deptId;             //部门编码
    private  String oprId;              //用户账号:登录系统账号的名称
    private  Date lastUpdDtTm;         //上次更新日期时间
    private  String lgiIntFlag;        //全量增量标记 A：新增 U：更新 D：删除 N：无变化

    public String getEmplId() {
        return emplId;
    }

    public void setEmplId(String emplId) {
        this.emplId = emplId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameAc() {
        return nameAc;
    }

    public void setNameAc(String nameAc) {
        this.nameAc = nameAc;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getHrStatus() {
        return hrStatus;
    }

    public void setHrStatus(String hrStatus) {
        this.hrStatus = hrStatus;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getOprId() {
        return oprId;
    }

    public void setOprId(String oprId) {
        this.oprId = oprId;
    }

    public Date getLastUpdDtTm() {
        return lastUpdDtTm;
    }

    public void setLastUpdDtTm(Date lastUpdDtTm) {
        this.lastUpdDtTm = lastUpdDtTm;
    }

    public String getLgiIntFlag() {
        return lgiIntFlag;
    }

    public void setLgiIntFlag(String lgiIntFlag) {
        this.lgiIntFlag = lgiIntFlag;
    }
}
