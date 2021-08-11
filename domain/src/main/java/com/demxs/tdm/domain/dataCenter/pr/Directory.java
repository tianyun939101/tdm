package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.TreeEntity;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/4/27 17:08
 * @Description: 文件夹实体类
 */
public class Directory extends TreeEntity<Directory> {

    private static final long serialVersionUID = 1L;
    /**
     * 层级
     */
    private String layer;
    /**
     * 父级id集合
     */
    private String parentIds;
    /**
     * 文件地址
     */
    private String fileUrl;
    /**
     * 文件大小
     */
    private String fileLength;
    /**
     * 地址
     */
    private String address;
    /**
     * 类型
     */
    private String type;
    private String pId;
    /**
     * 视图传输
     */
    private String fileType;

    public final static String DIRECTORY = "Directory";
    public final static String FILE = "File";

    public Directory(){
        super();
    }

    public Directory(String id){
        super(id);
    }

    @Override
    public Directory getParent() {
        return parent;
    }

    @Override
    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public String getLayer() {
        return layer;
    }

    public Directory setLayer(String layer) {
        this.layer = layer;
        return this;
    }

    @Override
    public String getParentIds() {
        return parentIds;
    }

    @Override
    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Directory setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
        return this;
    }

    public String getFileLength() {
        return fileLength;
    }

    public Directory setFileLength(String fileLength) {
        this.fileLength = fileLength;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Directory setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getpId() {
        return pId;
    }

    public Directory setpId(String pId) {
        this.pId = pId;
        return this;
    }

    public String getType() {
        return type;
    }

    public Directory setType(String type) {
        this.type = type;
        return this;
    }

    public String getFileType() {
        return fileType;
    }

    public Directory setFileType(String fileType) {
        this.fileType = fileType;
        return this;
    }
}
