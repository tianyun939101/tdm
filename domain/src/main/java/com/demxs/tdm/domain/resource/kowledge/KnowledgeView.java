package com.demxs.tdm.domain.resource.kowledge;


import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

public class KnowledgeView extends DataEntity<KnowledgeView> {

    /**
     * 名称
     */
    private String name;
    /**
     * 编制计划
     */
    private String plan;
    /**
     * 所属表
     */
    private String tabName;

    /**
     * 负责人
     */
    private String userName;

    /**
     * 科室
     * @return
     */
    private String labName;


    private String edited;

    private String hasLaucn;

    private String audited;

    private String officeId;

    private String yfb;

    private String daibian;

    private String labId;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
if(StringUtils.isNotBlank(plan)){

    String substring = plan.substring(0, 10);
    this.plan = substring;

}else{
    this.plan = plan;
}
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getEdited() {
        return edited;
    }

    public void setEdited(String edited) {
        this.edited = edited;
    }

    public String getHasLaucn() {
        return hasLaucn;
    }

    public void setHasLaucn(String hasLaucn) {
        this.hasLaucn = hasLaucn;
    }

    public String getAudited() {
        return audited;
    }

    public void setAudited(String audited) {
        this.audited = audited;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getYfb() {
        return yfb;
    }

    public void setYfb(String yfb) {
        this.yfb = yfb;
    }

    public String getDaibian() {
        return daibian;
    }

    public void setDaibian(String daibian) {
        this.daibian = daibian;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }
}
