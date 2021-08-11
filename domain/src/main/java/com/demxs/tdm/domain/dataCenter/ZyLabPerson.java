package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.List;

public class ZyLabPerson extends DataEntity<ZyLabPerson> {

    private static final long serialVersionUID = 1L;

    private String name;

    private String labId;

    private String station;

    private String department;

    private String introduce;//

    private String personFile;

    private String remarks;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
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

    public String getPersonFile() {
        return personFile;
    }

    public void setPersonFile(String persionFile) {
        this.personFile = persionFile;
    }

    @Override
    public String getRemarks() {
        return remarks;
    }

    @Override
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
