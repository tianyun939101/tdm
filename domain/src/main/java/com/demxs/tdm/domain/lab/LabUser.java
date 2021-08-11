package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;

/**
 * @author: Jason
 * @Date: 2020/8/25 10:05
 * @Description: 试验室人员关联
 */
public class LabUser extends DataEntity<LabUser> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验室id
     */
    private String labId;
    private LabInfo labInfo;

    private String newLabId;
    private LabInfo newLabInfo;
    /**
     * 用户id
     */
    private String userId;
    private User user;

    private String flag;

    public LabUser() {
    }

    public LabUser(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public LabUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public LabUser setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public LabUser setUser(User user) {
        this.user = user;
        return this;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public LabUser setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
        return this;
    }

    public String getNewLabId() {
        return newLabId;
    }

    public void setNewLabId(String newLabId) {
        this.newLabId = newLabId;
    }

    public LabInfo getNewLabInfo() {
        return newLabInfo;
    }

    public void setNewLabInfo(LabInfo newLabInfo) {
        this.newLabInfo = newLabInfo;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }
}
