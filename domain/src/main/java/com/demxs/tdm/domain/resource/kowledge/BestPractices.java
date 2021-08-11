package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.BaseEntity;
import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.demxs.tdm.domain.ability.TestCategoryVersion;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.dataCenter.DataTestInfo;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.center.Department;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 典型案例
 */
public class BestPractices extends DataEntity<BestPractices> {
    public static final SimpleDateFormat s2 = new SimpleDateFormat("yyyy/MM/dd");
    private String dept;    //科室
    private Department deptMap;
    private String name;    //名称
    private String type;    //类型
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date pubDate; //发布日期
    private String cycle;   //周期
    private String chargeUser;  //责任人
    private Yuangong chargeUserMap;

    private String deptment;    //部门
    private Department deptmentMap;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date plan;    //编制计划
    private String technology;   //技术图谱
    private Technology technologyMap;

    private KnowledgeMap knowledgeMap;
    private List<KnowledgeMap> knowledgeMapList;    //岗位知识地图
    private String  providerId;

    //状态
    private String status;
    //审核人
    private String auditId;
    private User auditUser;


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

    public Department getDeptmentMap() {
        return deptmentMap;
    }

    public void setDeptmentMap(Department deptmentMap) {
        this.deptmentMap = deptmentMap;
    }

    public Technology getTechnologyMap() {
        return technologyMap;
    }

    public void setTechnologyMap(Technology technologyMap) {
        this.technologyMap = technologyMap;
    }

    public Department getDeptMap() {
        return deptMap;
    }

    public void setDeptMap(Department deptMap) {
        this.deptMap = deptMap;
    }

    public String getAuditId() {
        return auditId;
    }

    public void setAuditId(String auditId) {
        this.auditId = auditId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public List<KnowledgeMap> getKnowledgeMapList() {
        return knowledgeMapList;
    }

    public void setKnowledgeMapList(List<KnowledgeMap> knowledgeMapList) {
        this.knowledgeMapList = knowledgeMapList;
    }

    public KnowledgeMap getKnowledgeMap() {
        return knowledgeMap;
    }

    public void setKnowledgeMap(KnowledgeMap knowledgeMap) {
        this.knowledgeMap = knowledgeMap;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public Date getPlan() {
        return plan;
    }

    public void setPlan(Date plan) {
        this.plan = plan;
    }

    public String getDeptment() {
        return deptment;
    }

    public void setDeptment(String deptment) {
        this.deptment = deptment;
    }

    public BestPractices(){}

    public BestPractices(String dept, String name, String type, Date pubDate, String cycle, String chargeUser, String deptment){
        this.dept = dept;
        this.name = name;
        this.type = type;
        this.pubDate = pubDate;
        this.chargeUser = chargeUser;
        this.cycle = cycle;
        this.deptment =  deptment;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }


    @ExcelField(title = "科室",sort = 0,type = 1)
    public String getDept() {
        return dept;
    }
    @ExcelField(title = "科室",sort = 0,type = 2)
    public void setDept(String dept) {
        this.dept = dept;
    }
    @ExcelField(title = "名称",sort = 1,type = 1)
    public String getName() {
        return name;
    }
    @ExcelField(title = "名称",sort = 1,type = 2)
    public void setName(String name) {
        this.name = name;
    }
    @ExcelField(title = "类型",sort = 2,type = 1)
    public String getType() {
        return type;
    }
    @ExcelField(title = "类型",sort = 2,type = 2)
    public void setType(String type) {
        this.type = type;
    }
    @ExcelField(title = "发布日期",sort = 3,type = 1)
    public Date getPubDate() {
        return pubDate;
    }
    @ExcelField(title = "发布日期",sort = 3,type = 2)
    public void setPubDateStr(String pubDate) {
        if(pubDate!=null && StringUtils.isNotBlank(pubDate)){
            String[] split = pubDate.split("/");
            if(split.length<2){
                pubDate= pubDate+ "/1/1";
            }
        }
        try {
            this.pubDate = s2.parse(pubDate);
        }catch (ParseException e){
            this.pubDate = null;
        }
    }
    @ExcelField(title = "姓名",sort = 4,type = 1)
    public String getChargeUser() {
        return chargeUser;
    }
    @ExcelField(title = "姓名",sort = 4,type = 2)
    public void setChargeUser(String chargeUser) {
        this.chargeUser = chargeUser;
    }
}
