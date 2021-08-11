package com.demxs.tdm.domain.business.dz;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.Date;

public class DzProviderInfo  extends DataEntity<DzProviderInfo> {
    private String id;

    private String supplierName;

    private String professionalNumber;

    private String equipment;

    private String qualifications;

    private String equipmentattr;

    private String standard;

    private String experience;

    private String qualitysystem;

    private String authentication;
    private String fileIds;


    private Date createDate;

    private String createBy;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName == null ? null : supplierName.trim();
    }

    public String getProfessionalNumber() {
        return professionalNumber;
    }

    public void setProfessionalNumber(String professionalNumber) {
        this.professionalNumber = professionalNumber == null ? null : professionalNumber.trim();
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment == null ? null : equipment.trim();
    }

    public String getQualifications() {
        return qualifications;
    }

    public void setQualifications(String qualifications) {
        this.qualifications = qualifications == null ? null : qualifications.trim();
    }

    public String getEquipmentattr() {
        return equipmentattr;
    }

    public void setEquipmentattr(String equipmentattr) {
        this.equipmentattr = equipmentattr == null ? null : equipmentattr.trim();
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard == null ? null : standard.trim();
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience == null ? null : experience.trim();
    }

    public String getQualitysystem() {
        return qualitysystem;
    }

    public void setQualitysystem(String qualitysystem) {
        this.qualitysystem = qualitysystem == null ? null : qualitysystem.trim();
    }

    public String getAuthentication() {
        return authentication;
    }

    public void setAuthentication(String authentication) {
        this.authentication = authentication == null ? null : authentication.trim();
    }

    public String getFileIds() {
        return fileIds;
    }

    public void setFileIds(String fileIds) {
        this.fileIds = fileIds == null ? null : fileIds.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }
}