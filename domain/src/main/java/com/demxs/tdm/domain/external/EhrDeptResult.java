package com.demxs.tdm.domain.external;

import java.util.Date;

/**
 * Created by ranhl on 2017-12-01.
 */
public class EhrDeptResult {
    private static final long serialVersionUID = 1L;
    private String deptId;    //部门ID/组织ID
    private String effStatus;   //生效状态（A – 有效，I -  无效）
    private String descr;       //组织名称
    private String lgiDescrEng;   //组织英文名
    private String managerId;    //组织负责人ID
    private String managerName;   //组织负责人姓名
    private String company;       //公司ID
    private String companyDescr;     //公司名称
    private String lgiCostCengerId;    //成本中心代码
    private Integer treeNodeNum;         //树节点编号，排序使用
    private String parDeptIdChn;       //父部门ID
    private Date lgiIntDt;             //时间戳
    private String lgiIntFlag;         //全量增量标记 A：新增 U：更新 D：删除 N：无变化

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getEffStatus() {
        return effStatus;
    }

    public void setEffStatus(String effStatus) {
        this.effStatus = effStatus;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getLgiDescrEng() {
        return lgiDescrEng;
    }

    public void setLgiDescrEng(String lgiDescrEng) {
        this.lgiDescrEng = lgiDescrEng;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyDescr() {
        return companyDescr;
    }

    public void setCompanyDescr(String companyDescr) {
        this.companyDescr = companyDescr;
    }

    public String getLgiCostCengerId() {
        return lgiCostCengerId;
    }

    public void setLgiCostCengerId(String lgiCostCengerId) {
        this.lgiCostCengerId = lgiCostCengerId;
    }

    public Integer getTreeNodeNum() {
        return treeNodeNum;
    }

    public void setTreeNodeNum(Integer treeNodeNum) {
        this.treeNodeNum = treeNodeNum;
    }

    public String getParDeptIdChn() {
        return parDeptIdChn;
    }

    public void setParDeptIdChn(String parDeptIdChn) {
        this.parDeptIdChn = parDeptIdChn;
    }

    public Date getLgiIntDt() {
        return lgiIntDt;
    }

    public void setLgiIntDt(Date lgiIntDt) {
        this.lgiIntDt = lgiIntDt;
    }

    public String getLgiIntFlag() {
        return lgiIntFlag;
    }

    public void setLgiIntFlag(String lgiIntFlag) {
        this.lgiIntFlag = lgiIntFlag;
    }
}