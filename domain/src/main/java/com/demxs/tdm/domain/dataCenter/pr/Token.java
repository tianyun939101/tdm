package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/4/27 16:44
 * @Description: 令牌验证实体类
 */
public class Token extends DataEntity<Token> {

    private static final long serialVersionUID = 1L;
    /**
     * 令牌
     */
    private String token;
    /**
     * 下载的文件id
     */
    private String fileId;
    /**
     * 下载的文件name
     */
    private String fileName;
    /**
     * 下载文件大小
     */
    private String fileSize;
    /**
     * 下载客户端的ip地址
     */
    private String ipAddress;
    /**
     * 类型 0：试飞中心，1、2、3：阎良，4：东营，5：上海
     */
    private String type;
    /**
     * 文件类型ftp文件或本地文件
     */
    private String fileType;

    public Token(){
        super();
    }

    public Token(String id){
        super(id);
    }

    public String getToken() {
        return token;
    }

    public Token setToken(String token) {
        this.token = token;
        return this;
    }

    public String getFileId() {
        return fileId;
    }

    public Token setFileId(String fileId) {
        this.fileId = fileId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public Token setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public Token setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Token setFileSize(String fileSize) {
        this.fileSize = fileSize;
        return this;
    }

    public String getType() {
        return type;
    }

    public Token setType(String type) {
        this.type = type;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public Token setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }
}
