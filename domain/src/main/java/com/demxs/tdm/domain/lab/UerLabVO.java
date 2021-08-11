package com.demxs.tdm.domain.lab;

import java.io.Serializable;

public class UerLabVO implements Serializable {


    private String roleId;

    private String userId;

    private String userName;

    private String labIds;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLabIds() {
        return labIds;
    }

    public void setLabIds(String labIds) {
        this.labIds = labIds;
    }
}
