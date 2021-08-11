package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.domain.resource.center.Department;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

/**
 * 设计流程体系
 */
public class DesignFlow extends TreeEntity<DesignFlow> {
    //编号
    private String code;
    //层级
    private Integer deep;
    //状态
    private String status;
    //流程验证确认
    private String valid;
    //适用性评估
    private String assess;
    //是否公共流程
    private String isPublic;
    //负责人
    private String chargeUser;
    private Yuangong chargeUserMap;
    //类型
    private String type;

    //流程编制计划
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date compilPlan;
    //流程验证计划
    private String verifiPlan;
    //流程验证状态
    private String verifiStatus;

    private String technology;   //技术图谱
    private Technology technologyMap;

    private KnowledgeMap knowledgeMap;
    private List<KnowledgeMap> knowledgeMapList;    //岗位知识地图
    private String  providerId;

    /**
     * 科室
     */
    private String officeId;
    private Department office;


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

    public Technology getTechnologyMap() {
        return technologyMap;
    }

    public void setTechnologyMap(Technology technologyMap) {
        this.technologyMap = technologyMap;
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

    public Date getCompilPlan() {
        return compilPlan;
    }

    public void setCompilPlan(Date compilPlan) {
        this.compilPlan = compilPlan;
    }

    public String getVerifiPlan() {
        return verifiPlan;
    }

    public void setVerifiPlan(String verifiPlan) {
        this.verifiPlan = verifiPlan;
    }

    public String getVerifiStatus() {
        return verifiStatus;
    }

    public void setVerifiStatus(String verifiStatus) {
        this.verifiStatus = verifiStatus;
    }

    public DesignFlow() {
    }

    public DesignFlow(String id) {
        super(id);
    }

    public DesignFlow(String code, String name,String chargeUser, String type) {
        this.code = code;
        this.name = name;
        this.chargeUser = chargeUser;
        this.type = type;

    }

    public DesignFlow(String code, String name, Integer deep, String status,String valid,String assess,String isPublic, String chargeUser, String type) {
        this.code = code;
        this.name = name;
        this.deep = deep;
        this.status = status;
        this.valid = valid;
        this.assess = assess;
        this.isPublic = isPublic;
        this.chargeUser = chargeUser;
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getValid() {
        return valid;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getAssess() {
        return assess;
    }

    public void setAssess(String assess) {
        this.assess = assess;
    }

    public String getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(String isPublic) {
        this.isPublic = isPublic;
    }

    public String getChargeUser() {
        return chargeUser;
    }

    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }

    @Override
    public DesignFlow getParent() {
        return this.parent;
    }

    @Override
    public void setParent(DesignFlow parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DesignFlow){
            if(((DesignFlow) obj).getCode().equals(this.code) && ((DesignFlow) obj).getName().equals(this.name)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return this.code+"["+this.name+"]"+(this.parent==null?"":this.parent.getCode());
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
