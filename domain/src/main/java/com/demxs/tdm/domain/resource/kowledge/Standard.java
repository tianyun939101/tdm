package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.ability.TestCategory;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.center.Department;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Standard extends TreeEntity<Standard> {
    //编号
    private String code;
    //全称
    private String fullName;
    //国标号
    private String gbCode;
    //版本号
    private String versionNo;
    //层级
    private Integer deep;
    //类型
    private String type;
    //状态
    private String status;
    //编制计划
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date plan;
    //替代名称
    private String reFullName;
    private Standard reFullNameList;
    //替代国标号
    private String reGbCode;
    private Standard reGbCodeList;
    //应用型号
    private String applyModel;
    //实施途径
    private String impWay;
    //责任部门
    private String chargeOrg;
    private Department chargeOrgList;
    //维护人
    private String chargeUser;
    private Yuangong chargeUserMap;
    //类型
    private String category;
    //规范体系
    private String gftx;
    //技术图谱
    private String technology;
    private List<Technology> technologyList;



    private Standard stand;

    /**
     * 版本id
     */
    private String vId;

    /**
     * 父级集合ID
     */
    private String parentIds;

    /**
     * 父级集合
     */
    private List<TestCategory> parentList;
    /**
     * 父级节点名称
     */
    private String parentName;

    /**
     * 修改申请单id，视图传递对象
     */
    private String labId;
    private String labName;

    /**
     *
     */
    private List<Standard> standardList;

    /**
     *子集
     */
    private List<Standard> chilend;

    /**
     *数据
     */
    private List<Standard> data;
    /**
     *岗位知识地图
     */
    private KnowledgeMap knowledgeMap;
    private List<KnowledgeMap> knowledgeMapList;
    private String  providerId;

    /**
     * 目录
     */
    private String  firstName;
    private String seccendName;
    private String thirdName;
    private String forthName;
    private String fifthName;
    private String sixthName;
    private String senthName;

    private String firstCode;
    private String seccendCode;
    private String thirdCode;
    private String forthCode;
    private String fifthCode;
    private String sixthCode;
    private String senthCode;


    /**
     *portal 门户/表格进入
     * @return
     */
    private String portal;

    /**
     *
     * @return
     */
    private String officeId;
    private Department office;
    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public Yuangong getChargeUserMap() {
        return chargeUserMap;
    }

    public void setChargeUserMap(Yuangong chargeUserMap) {
        this.chargeUserMap = chargeUserMap;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSeccendName() {
        return seccendName;
    }

    public void setSeccendName(String seccendName) {
        this.seccendName = seccendName;
    }

    public String getThirdName() {
        return thirdName;
    }

    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }

    public String getForthName() {
        return forthName;
    }

    public void setForthName(String forthName) {
        this.forthName = forthName;
    }

    public String getFifthName() {
        return fifthName;
    }

    public void setFifthName(String fifthName) {
        this.fifthName = fifthName;
    }

    public String getSixthName() {
        return sixthName;
    }

    public void setSixthName(String sixthName) {
        this.sixthName = sixthName;
    }

    public String getSenthName() {
        return senthName;
    }

    public void setSenthName(String senthName) {
        this.senthName = senthName;
    }

    public String getFirstCode() {
        return firstCode;
    }

    public void setFirstCode(String firstCode) {
        this.firstCode = firstCode;
    }

    public String getSeccendCode() {
        return seccendCode;
    }

    public void setSeccendCode(String seccendCode) {
        this.seccendCode = seccendCode;
    }

    public String getThirdCode() {
        return thirdCode;
    }

    public void setThirdCode(String thirdCode) {
        this.thirdCode = thirdCode;
    }

    public String getForthCode() {
        return forthCode;
    }

    public void setForthCode(String forthCode) {
        this.forthCode = forthCode;
    }

    public String getFifthCode() {
        return fifthCode;
    }

    public void setFifthCode(String fifthCode) {
        this.fifthCode = fifthCode;
    }

    public String getSixthCode() {
        return sixthCode;
    }

    public void setSixthCode(String sixthCode) {
        this.sixthCode = sixthCode;
    }

    public String getSenthCode() {
        return senthCode;
    }

    public void setSenthCode(String senthCode) {
        this.senthCode = senthCode;
    }

    public List<Standard> getChilend() {
        return chilend;
    }

    public void setChilend(List<Standard> chilend) {
        this.chilend = chilend;
    }

    public List<Standard> getData() {
        return data;
    }

    public void setData(List<Standard> data) {
        this.data = data;
    }

    public List<Standard> getStandardList() {
        return standardList;
    }

    public void setStandardList(List<Standard> standardList) {
        this.standardList = standardList;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public KnowledgeMap getKnowledgeMap() {
        return knowledgeMap;
    }

    public void setKnowledgeMap(KnowledgeMap knowledgeMap) {
        this.knowledgeMap = knowledgeMap;
    }

    public List<KnowledgeMap> getKnowledgeMapList() {
        return knowledgeMapList;
    }

    public void setKnowledgeMapList(List<KnowledgeMap> knowledgeMapList) {
        this.knowledgeMapList = knowledgeMapList;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getGftx() {
        return gftx;
    }

    public void setGftx(String gftx) {
        this.gftx = gftx;
    }

    private LabInfo labInfo;

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public List<TestCategory> getParentList() {
        return parentList;
    }

    public void setParentList(List<TestCategory> parentList) {
        this.parentList = parentList;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Standard() {
    }

    public Standard(String id) {
        super(id);
    }

    public Standard(String code,String name,String type) {
        this.code = code;
        this.name = name;
        this.type = type;
    }

    public Standard(String code,String name,String versionNo,Integer deep,String category,String status, Date plan,String reFullName,String reGbCode,String applyModel,String impWay,String chargeOrg,String chargeUser,String remarks,String type) {
        this.code = code;
        this.name = name;
        this.versionNo = versionNo;
        this.deep = deep;
        this.category = category;
        this.status = status;
        this.plan = plan;
        this.reFullName = reFullName;
        this.reGbCode = reGbCode;
        this.applyModel = applyModel;
        this.impWay = impWay;
        this.chargeOrg = chargeOrg;
        this.chargeUser = chargeUser;
        this.remarks = remarks;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGbCode() {
        return gbCode;
    }

    public void setGbCode(String gbCode) {
        this.gbCode = gbCode;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public Integer getDeep() {
        return deep;
    }

    public void setDeep(Integer deep) {
        this.deep = deep;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public Date getPlan() {
        return plan;
    }

    public void setPlan(Date plan) {
        this.plan = plan;
    }

    public String getReFullName() {
        return reFullName;
    }

    public void setReFullName(String reFullName) {
        this.reFullName = reFullName;
    }

    public String getReGbCode() {
        return reGbCode;
    }

    public void setReGbCode(String reGbCode) {
        this.reGbCode = reGbCode;
    }

    public String getChargeOrg() {
        return chargeOrg;
    }

    public void setChargeOrg(String chargeOrg) {
        this.chargeOrg = chargeOrg;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    public String getApplyModel() {
        return applyModel;
    }

    public void setApplyModel(String applyModel) {
        this.applyModel = applyModel;
    }

    public String getImpWay() {
        return impWay;
    }

    public void setImpWay(String impWay) {
        this.impWay = impWay;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    @Override
    public Standard getParent() {
        return this.parent;
    }

    @Override
    public void setParent(Standard parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Standard){
            if(((Standard) obj).getCode().equals(this.code) && ((Standard) obj).getName().equals(this.name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.code+"["+this.name+"]"+(this.parent==null?"":this.parent.getCode());
    }

    public Standard getReFullNameList() {
        return reFullNameList;
    }

    public void setReFullNameList(Standard reFullNameList) {
        this.reFullNameList = reFullNameList;
    }

    public Standard getReGbCodeList() {
        return reGbCodeList;
    }

    public void setReGbCodeList(Standard reGbCodeList) {
        this.reGbCodeList = reGbCodeList;
    }

    public Department getChargeOrgList() {
        return chargeOrgList;
    }

    public void setChargeOrgList(Department chargeOrgList) {
        this.chargeOrgList = chargeOrgList;
    }

    public List<Technology> getTechnologyList() {
        return technologyList;
    }

    public void setTechnologyList(List<Technology> technologyList) {
        this.technologyList = technologyList;
    }

    public Standard getStand() {
        return stand;
    }

    public void setStand(Standard stand) {
        this.stand = stand;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public Department getOffice() {
        return office;
    }

    public void setOffice(Department office) {
        this.office = office;
    }
}
