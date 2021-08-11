package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.SubCenter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class ZyLabBasicInfo extends DataEntity<ZyLabBasicInfo> {

    private static final long serialVersionUID = 1L;

    private String center;//所属单位

    private String labId;//实验室名称

    private String labPosition;//实验室定位

    private String buildAccording;//建设依据

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date runTime;//运行时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date topicTime;//立项时间

    private String attachFile;//成立照片

    private String serviceType;//服务型号

    private String ability;//能力建设

    private String leader;//负责人

    private String buildCost;//建设费用

    private String buildArea;//建设场地

    private String buildNatural;//实验室资质

    private LabInfo  labInfo;

    private SubCenter  subCenter;

    private User user;


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SubCenter getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(SubCenter subCenter) {
        this.subCenter = subCenter;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getLabPosition() {
        return labPosition;
    }

    public void setLabPosition(String labPosition) {
        this.labPosition = labPosition;
    }

    public String getBuildAccording() {
        return buildAccording;
    }

    public void setBuildAccording(String buildAccording) {
        this.buildAccording = buildAccording;
    }

    public Date getRunTime() {
        return runTime;
    }

    public void setRunTime(Date runTime) {
        this.runTime = runTime;
    }

    public Date getTopicTime() {
        return topicTime;
    }

    public void setTopicTime(Date topicTime) {
        this.topicTime = topicTime;
    }

    public String getAttachFile() {
        return attachFile;
    }

    public void setAttachFile(String attachFile) {
        this.attachFile = attachFile;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAbility() {
        return ability;
    }

    public void setAbility(String ability) {
        this.ability = ability;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getBuildCost() {
        return buildCost;
    }

    public void setBuildCost(String buildCost) {
        this.buildCost = buildCost;
    }

    public String getBuildArea() {
        return buildArea;
    }

    public void setBuildArea(String buildArea) {
        this.buildArea = buildArea;
    }

    public String getBuildNatural() {
        return buildNatural;
    }

    public void setBuildNatural(String buildNatural) {
        this.buildNatural = buildNatural;
    }
}
