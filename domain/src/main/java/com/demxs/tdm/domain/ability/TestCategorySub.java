package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.SubCenter;

import java.util.List;

public class TestCategorySub extends DataEntity<TestCategorySub> {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 试验能力id
     * @see TestCategory id
     */
    private String cId;
    /**
     * 版本id
     * @see TestCategoryVersion id
     */
    private String vId;
    /**
     * 试验室id
     * @see LabInfo id
     */
    private String labId;
    private LabInfo labInfo;
    /**
     * 所属单位，由试验室id关联
     */
    private SubCenter subCenter;

    private String subscribe;

    /**
     * 订阅人
     */
    private String userId;
    private User user;
    private List<User> userList;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getvId() {
        return vId;
    }

    public void setvId(String vId) {
        this.vId = vId;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public SubCenter getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(SubCenter subCenter) {
        this.subCenter = subCenter;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
