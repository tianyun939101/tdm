package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.TreeEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import java.util.List;

public class ZyLabInfo extends TreeEntity<ZyLabInfo> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String team;

    private String parentId;

    private String parentIds;

    private String duty;    //科室职能

    private String ability; //能力介绍

    private String leader;

    private String station; //

    private String introduce;//

    private String labFile;

    private String leaderFile;

    private String remarks;

    private String isFlag;
    //科室人员
    private List<ZyLabPerson> zyLabPersonList;

    //子集实验室
    private List<ZyLabTion>  zyLabTionList;
    //菜单
    private List<ZyLabInfo>  zyLabInfolist;
    //
    private ZyLabInfo parent;

    private String parentInfo;

    private String company;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<ZyLabInfo> getZyLabInfolist() {
        return zyLabInfolist;
    }

    public void setZyLabInfolist(List<ZyLabInfo> zyLabInfolist) {
        this.zyLabInfolist = zyLabInfolist;
    }

    public List<ZyLabTion> getZyLabTionList() {
        return zyLabTionList;
    }

    public void setZyLabTionList(List<ZyLabTion> zyLabTionList) {
        this.zyLabTionList = zyLabTionList;
    }

    public ZyLabInfo() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getParentInfo() {
        return parentInfo;
    }

    public void setParentInfo(String parentInfo) {
        this.parentInfo = parentInfo;
    }

    @Override
    @JsonBackReference
    public ZyLabInfo getParent() {
        return parent;
    }

    public ZyLabInfo(String id) {
        this.id = id;
    }

    @Override
    public void setParent(ZyLabInfo parent) {
        this.parent = parent;
    }


    public String getIsFlag() {
        return isFlag;
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    @Override
    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
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

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getLabFile() {
        return labFile;
    }

    public void setLabFile(String labFile) {
        this.labFile = labFile;
    }

    public String getLeaderFile() {
        return leaderFile;
    }

    public void setLeaderFile(String leaderFile) {
        this.leaderFile = leaderFile;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public List<ZyLabPerson> getZyLabPersonList() {
        return zyLabPersonList;
    }

    public void setZyLabPersonList(List<ZyLabPerson> zyLabPersonList) {
        this.zyLabPersonList = zyLabPersonList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


}
