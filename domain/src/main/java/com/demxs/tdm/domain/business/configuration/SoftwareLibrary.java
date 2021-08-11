package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 软件构型库Entity
 */
public class SoftwareLibrary extends DataEntity<SoftwareLibrary> {

    private static final long serialVersionUID = 1L;

    //件号
    private String code;
    //名称
    private String name;
    //英文名
    private String eName;
    //设备Id
    private String shebeiId;
    //设备
    private Shebei shebei;
    //所属实验室ID
    private String labId;
    //所属实验室
    private LabInfo labInfo;
    //设备序列号
    private String serialNum;
    //MOD data
    @JsonProperty("MODDATA")
    private String MODDATA;
    //软件版本号
    private String version;


    private String subCenter;

    private String labInfoName;

    @Override
    public String getLabInfoName() {
        return labInfoName;
    }

    @Override
    public void setLabInfoName(String labInfoName) {
        this.labInfoName = labInfoName;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShebeiId() {
        return shebeiId;
    }

    public void setShebeiId(String shebeiId) {
        this.shebeiId = shebeiId;
    }

    public Shebei getShebei() {
        return shebei;
    }

    public void setShebei(Shebei shebei) {
        this.shebei = shebei;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getMODDATA() {
        return MODDATA;
    }

    public void setMODDATA(String MODDATA) {
        this.MODDATA = MODDATA;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }
}
