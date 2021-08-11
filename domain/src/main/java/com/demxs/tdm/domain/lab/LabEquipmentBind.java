package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.shebei.Shebei;

import java.util.Objects;

/**
 * @author: Jason
 * @Date: 2020/6/10 11:18
 * @Description:
 */
public class LabEquipmentBind extends DataEntity<LabEquipmentBind> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验室id
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 设备id
     */
    private String equipId;
    private Shebei shebei;
    /**
     * 地址
     */
    private String address;
    /**
     * 端口
     */
    private String host;
    /**
     * 连接密码
     */
    private String password;
    /**
     * 是否只读
     */
    private String isViewOnly;
    /**
     * 是否支持缩放
     */
    private String scale;

    public LabEquipmentBind() {
    }

    public LabEquipmentBind(String id) {
        super(id);
    }

    public String getLabId() {
        return labId;
    }

    public LabEquipmentBind setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getEquipId() {
        return equipId;
    }

    public LabEquipmentBind setEquipId(String equipId) {
        this.equipId = equipId;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public LabEquipmentBind setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getHost() {
        return host;
    }

    public LabEquipmentBind setHost(String host) {
        this.host = host;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LabEquipmentBind setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getIsViewOnly() {
        return isViewOnly;
    }

    public LabEquipmentBind setIsViewOnly(String isViewOnly) {
        this.isViewOnly = isViewOnly;
        return this;
    }

    public String getScale() {
        return scale;
    }

    public LabEquipmentBind setScale(String scale) {
        this.scale = scale;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public LabEquipmentBind setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public Shebei getShebei() {
        return shebei;
    }

    public LabEquipmentBind setShebei(Shebei shebei) {
        this.shebei = shebei;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        LabEquipmentBind that = (LabEquipmentBind) o;
        if (!Objects.equals(labId, that.labId)) {
            return false;
        }
        return Objects.equals(equipId, that.equipId);
    }

    @Override
    public int hashCode() {
        int result = labId != null ? labId.hashCode() : 0;
        result = 31 * result + (equipId != null ? equipId.hashCode() : 0);
        return result;
    }
}
