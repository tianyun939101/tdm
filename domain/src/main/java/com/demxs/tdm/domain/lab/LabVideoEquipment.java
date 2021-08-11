package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/6/11 15:34
 * @Description:试验室视频通道设备
 */
public class LabVideoEquipment extends DataEntity<LabVideoEquipment> {

    private static final long serialVersionUID = 1L;

    /**
     * IP地址
     */
    private String address;

    /**
     * 设备端口号
     */
    private String port;

    /**
     * 设备登录名
     */
    private String userName;
    /**
     * 设备访问密码
     */
    private String password;
    /**
     * 设备名
     */
    private String name;
    /**
     * 关联试验室
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 类型（0：rtmp视频设备1：IE控件2：嵌入网页）
     */
    private String type;
    /**
     * IE控件视频设备id
     */
    private String cameraId;
    /**
     * 嵌入网页地址
     */
    private String url;
    /**
     * 图片
     */
    private String picture;

    //配置编号
    private String configId;

    public LabVideoEquipment() {
    }

    public LabVideoEquipment(String id) {
        super(id);
    }

    public String getAddress() {
        return address;
    }

    public LabVideoEquipment setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public LabVideoEquipment setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public LabVideoEquipment setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public LabVideoEquipment setName(String name) {
        this.name = name;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public LabVideoEquipment setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public LabVideoEquipment setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getType() {
        return type;
    }

    public LabVideoEquipment setType(String type) {
        this.type = type;
        return this;
    }

    public String getCameraId() {
        return cameraId;
    }

    public LabVideoEquipment setCameraId(String cameraId) {
        this.cameraId = cameraId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public LabVideoEquipment setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
