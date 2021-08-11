package com.demxs.tdm.domain.business.instrument;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class DzCirculationRecord extends DataEntity<DzCirculationRecord> {

    private String id;

    private String current;

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    private String motion;

    private String leader;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date leaderDate;

    private String restitution;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date restitutionDate;

    private String relationId;


    private String currentType;


    private String name;

    private String email;

    private String uname;

    private String model;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date expectDate;

    private String  leaderName;

    private String  returnName;

    private String  instrumentName;

    private String  instrumentCode;

    private String  timeDate;

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public String getInstrumentCode() {
        return instrumentCode;
    }

    public void setInstrumentCode(String instrumentCode) {
        this.instrumentCode = instrumentCode;
    }

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public String getReturnName() {
        return returnName;
    }

    public void setReturnName(String returnName) {
        this.returnName = returnName;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCurrentType() {
        return currentType;
    }

    public void setCurrentType(String currentType) {
        this.currentType = currentType;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMotion() {
        return motion;
    }


    public void setMotion(String motion) {
        this.motion = motion == null ? null : motion.trim();
    }


    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader == null ? null : leader.trim();
    }

    public Date getLeaderDate() {
        return leaderDate;
    }


    public void setLeaderDate(Date leaderDate) {
        this.leaderDate = leaderDate;
    }


    public String getRestitution() {
        return restitution;
    }


    public void setRestitution(String restitution) {
        this.restitution = restitution == null ? null : restitution.trim();
    }

    public Date getRestitutionDate() {
        return restitutionDate;
    }

    public void setRestitutionDate(Date restitutionDate) {
        this.restitutionDate = restitutionDate;
    }


    public String getRelationId() {
        return relationId;
    }


    public void setRelationId(String relationId) {
        this.relationId = relationId == null ? null : relationId.trim();
    }
}