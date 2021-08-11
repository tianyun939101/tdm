package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.lab.LabInfo;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 设备台套信息
 */
public class ShebeiTaiTao extends DataEntity<ShebeiTaiTao> {

    private static final long serialVersionUID = 1L;
    private String id;
    private String taitaobh; //台套编号
    private String taitaomc;//台套名称
    private String taitaozt;//台套状态
    private String abilityId;//关联资质ID
    private String shebeixh;//设备型号
    private String labInfoId;//所属实验室ID
    private String labInfoName;//所属实验室名称
    private LabInfo labInfo;//所属试验室
    private Aptitude aptitude;//设备资质
    private List<Shebei> sbList = Lists.newArrayList();//设备集合

    public ShebeiTaiTao() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTaitaobh() {
        return taitaobh;
    }

    public void setTaitaobh(String taitaobh) {
        this.taitaobh = taitaobh;
    }

    public String getTaitaomc() {
        return taitaomc;
    }

    public void setTaitaomc(String taitaomc) {
        this.taitaomc = taitaomc;
    }

    public String getTaitaozt() {
        return taitaozt;
    }

    public void setTaitaozt(String taitaozt) {
        this.taitaozt = taitaozt;
    }

    public String getAbilityId() {
        return abilityId;
    }

    public void setAbilityId(String abilityId) {
        this.abilityId = abilityId;
    }

    public String getShebeixh() {
        return shebeixh;
    }

    public void setShebeixh(String shebeixh) {
        this.shebeixh = shebeixh;
    }

    @Override
    public String getLabInfoId() {
        return labInfoId;
    }

    @Override
    public void setLabInfoId(String labInfoId) {
        this.labInfoId = labInfoId;
    }

    @Override
    public String getLabInfoName() {
        return labInfoName;
    }

    @Override
    public void setLabInfoName(String labInfoName) {
        this.labInfoName = labInfoName;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public Aptitude getAptitude() {
        return aptitude;
    }

    public void setAptitude(Aptitude aptitude) {
        this.aptitude = aptitude;
    }

    public List<Shebei> getSbList() {
        return sbList;
    }

    public void setSbList(List<Shebei> sbList) {
        this.sbList = sbList;
    }
}