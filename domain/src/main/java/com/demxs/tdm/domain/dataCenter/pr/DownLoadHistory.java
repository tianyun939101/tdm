package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Jason
 * @Date: 2020/4/27 16:46
 * @Description: 下载记录实体类
 */
public class DownLoadHistory extends DataEntity<DownLoadHistory> {

    private static final long serialVersionUID = 1L;
    /**
     * 下载人id
     */
    private String downLoaderId;
    private User downLoader;
    /**
     * 下载人名字
     */
    private String downLoaderName;
    /**
     * 下载时间
     */
    private Date downLoadDate;
    /**
     * 下载文件id
     */
    private String downLoadFileId;
    /**
     * 下载文件名
     */
    private String downLoadFileName;
    /**
     * 下载文件大小
     */
    private String downLoadFileSize;
    /**
     * 下载人id
     */
    private String ipAddress;
    /**
     * 是否有效（1：有效，0：无效）
     */
    private String isValid;
    /**
     * 令牌id
     */
    private String tokenId;
    /**
     * 令牌
     */
    private String token;
    /**
     * 文件源 0：试飞中心，1、2、3：阎良，4：东营，5：上海
     */
    private String type;
    /**
     * 文件类型：ftp文件、本地文件
     */
    private String fileType;

    public DownLoadHistory(){
        super();
    }

    public DownLoadHistory(String id){
        super(id);
    }

    public String getDownLoaderId() {
        return downLoaderId;
    }

    public DownLoadHistory setDownLoaderId(String downLoaderId) {
        this.downLoaderId = downLoaderId;
        return this;
    }

    public String getDownLoaderName() {
        return downLoaderName;
    }

    public DownLoadHistory setDownLoaderName(String downLoaderName) {
        this.downLoaderName = downLoaderName;
        return this;
    }

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    public Date getDownLoadDate() {
        return downLoadDate;
    }

    public DownLoadHistory setDownLoadDate(Date downLoadDate) {
        this.downLoadDate = downLoadDate;
        return this;
    }

    public String getDownLoadFileId() {
        return downLoadFileId;
    }

    public DownLoadHistory setDownLoadFileId(String downLoadFileId) {
        this.downLoadFileId = downLoadFileId;
        return this;
    }

    public String getDownLoadFileName() {
        return downLoadFileName;
    }

    public DownLoadHistory setDownLoadFileName(String downLoadFileName) {
        this.downLoadFileName = downLoadFileName;
        return this;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public DownLoadHistory setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
        return this;
    }

    public String getIsValid() {
        return isValid;
    }

    public DownLoadHistory setIsValid(String isValid) {
        this.isValid = isValid;
        return this;
    }

    public String getTokenId() {
        return tokenId;
    }

    public DownLoadHistory setTokenId(String tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public String getToken() {
        return token;
    }

    public DownLoadHistory setToken(String token) {
        this.token = token;
        return this;
    }

    public User getDownLoader() {
        return downLoader;
    }

    public DownLoadHistory setDownLoader(User downLoader) {
        this.downLoader = downLoader;
        return this;
    }

    public String getDownLoadFileSize() {
        return downLoadFileSize;
    }

    public DownLoadHistory setDownLoadFileSize(String downLoadFileSize) {
        this.downLoadFileSize = downLoadFileSize;
        return this;
    }

    public String getType() {
        return type;
    }

    public DownLoadHistory setType(String type) {
        this.type = type;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public DownLoadHistory setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }
}
