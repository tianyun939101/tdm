package com.demxs.tdm.domain.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/6/17 15:48
 * @Description:分中心大屏配置工控机通道
 */
public class IPC extends DataEntity<IPC> {

    private static final long serialVersionUID = 1L;
    /**
     * 设备名称
     */
    private String name;
    /**
     * 试验室id
     */
    private String labId;
    /**
     * 试验任务id
     */
    private String testTaskId;
    /**
     * IP地址
     */
    private String address;
    /**
     * 端口
     */
    private String port;
    /**
     * 访问密码
     */
    private String password;
    /**
     * 是否允许操作
     */
    private String isViewOnly;
    /**
     * 是否缩放
     */
    private String scale;

    //配置编号
    private String configId;

    public IPC() {
    }

    public IPC(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public IPC setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public IPC setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPort() {
        return port;
    }

    public IPC setPort(String port) {
        this.port = port;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IPC setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getIsViewOnly() {
        return isViewOnly;
    }

    public IPC setIsViewOnly(String isViewOnly) {
        this.isViewOnly = isViewOnly;
        return this;
    }

    public String getScale() {
        return scale;
    }

    public IPC setScale(String scale) {
        this.scale = scale;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public IPC setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getTestTaskId() {
        return testTaskId;
    }

    public IPC setTestTaskId(String testTaskId) {
        this.testTaskId = testTaskId;
        return this;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }
}
