package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * 数据中心数据权限实体类
 */
public class DataBasePerm extends DataEntity<DataBasePerm>{

    private static final long serialVersionUID = 1L;

    private String baseId;//基础信息ID

    private String permissionType;//权限类型 1查看权限 2提报页操作权限

    private String authorizationType;//授权类型 1人员 2 部门
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;//授权开始时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;//授权结束时间

    private String authorizationId;//部门或人员ID

    private String authorizationScope;//授权范围 1全部2时间段

    private String authorizer;//授权人

    private User user;

    private Office office;

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getAuthorizationType() {
        return authorizationType;
    }

    public void setAuthorizationType(String authorizationType) {
        this.authorizationType = authorizationType;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(String authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(String authorizer) {
        this.authorizer = authorizer;
    }

    public String getAuthorizationScope() {
        return authorizationScope;
    }

    public void setAuthorizationScope(String authorizationScope) {
        this.authorizationScope = authorizationScope;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }
}