package com.demxs.tdm.domain.business.testoutline;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 试验大纲基础信息
 */
public class BaseTestOutline extends DataEntity<BaseTestOutline> {

    private static final long serialVersionUID = 1L;
    //大纲名称
    private String name;
    //所属实验室ID
    private String labId;
    //所属实验室名称
    private String labName;
    //正在编辑版本id
    private String editVersion;
    //正在编辑版本名称
    private String editVersionName;
    //正在编辑版本状态
    private String editVersionStatus;
    //最新审核通过版本id
    private String auditVersion;
    //最新审核通过版本名称
    private String auditVersionName;
    //当前生效版本
    private TestOutlineVersion curVersion;

    //非标分配资源保存时选择的大纲版本
    private TestOutlineVersion saveVersion;

    public BaseTestOutline(){
        super();
    }

    public BaseTestOutline(String id){
        super(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuditVersion() {
        return auditVersion;
    }

    public void setAuditVersion(String auditVersion) {
        this.auditVersion = auditVersion;
    }

    public String getAuditVersionName() {
        return auditVersionName;
    }

    public void setAuditVersionName(String auditVersionName) {
        this.auditVersionName = auditVersionName;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getLabName() {
        return labName;
    }

    public void setLabName(String labName) {
        this.labName = labName;
    }

    public String getEditVersion() {
        return editVersion;
    }

    public void setEditVersion(String editVersion) {
        this.editVersion = editVersion;
    }

    public String getEditVersionName() {
        return editVersionName;
    }

    public void setEditVersionName(String editVersionName) {
        this.editVersionName = editVersionName;
    }

    public String getEditVersionStatus() {
        return editVersionStatus;
    }

    public void setEditVersionStatus(String editVersionStatus) {
        this.editVersionStatus = editVersionStatus;
    }

    public TestOutlineVersion getCurVersion() {
        return curVersion;
    }

    public void setCurVersion(TestOutlineVersion curVersion) {
        this.curVersion = curVersion;
    }

    public TestOutlineVersion getSaveVersion() {
        return saveVersion;
    }

    public BaseTestOutline setSaveVersion(TestOutlineVersion saveVersion) {
        this.saveVersion = saveVersion;
        return this;
    }
}
