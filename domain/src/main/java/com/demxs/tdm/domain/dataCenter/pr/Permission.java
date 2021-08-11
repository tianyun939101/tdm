package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.List;
import java.util.Objects;

/**
 * @author: Jason
 * @Date: 2020/4/27 17:18
 * @Description: 控制下载及查看的权限实体类
 */
public class Permission extends DataEntity<Permission> {

    private static final long serialVersionUID = 1L;
    /**
     * 部门id
     */
    private String deptId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 文件夹id
     */
    private String dirId;
    /**
     * 文件夹对象
     */
    private Directory directory;
    /**
     * 文件夹集合
     */
    private List<Directory> directoryList;

    public Permission(){
        super();
    }

    public Permission(String id){
        super(id);
    }

    public String getDeptId() {
        return deptId;
    }

    public Permission setDeptId(String deptId) {
        this.deptId = deptId;
        return this;
    }

    public String getDeptName() {
        return deptName;
    }

    public Permission setDeptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    public String getDirId() {
        return dirId;
    }

    public Permission setDirId(String dirId) {
        this.dirId = dirId;
        return this;
    }

    public Directory getDirectory() {
        return directory;
    }

    public Permission setDirectory(Directory directory) {
        this.directory = directory;
        return this;
    }

    public List<Directory> getDirectoryList() {
        return directoryList;
    }

    public Permission setDirectoryList(List<Directory> directoryList) {
        this.directoryList = directoryList;
        return this;
    }

    public String getName(){
        return this.directory == null ? "" : directory.getName();
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

        Permission that = (Permission) o;

        return Objects.equals(deptId, that.deptId);
    }

    @Override
    public int hashCode() {
        return deptId != null ? deptId.hashCode() : 0;
    }
}
