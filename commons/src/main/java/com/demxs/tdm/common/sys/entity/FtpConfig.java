package com.demxs.tdm.common.sys.entity;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/7/17 14:06
 * @Description:试飞ftp服务器配置类
 */
public class FtpConfig extends DataEntity<FtpConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * 访问地址
     */
    private String address;
    /**
     * 端口
     */
    private Integer port;
    /**
     * 登录名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 类型0：试飞中心，1、2、3：阎良，4：东营，5：上海
     */
    private String type;

    public FtpConfig() {
    }

    public FtpConfig(String id) {
        super(id);
    }

    public String getAddress() {
        return address;
    }

    public FtpConfig setAddress(String address) {
        this.address = address;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public FtpConfig setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public FtpConfig setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FtpConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getType() {
        return type;
    }

    public FtpConfig setType(String type) {
        this.type = type;
        return this;
    }
}
