package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.SubCenter;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/10 08:53
 * @Description: 试验室试验能力属性
 */
public class TestCategoryAttr extends DataEntity<TestCategoryAttr> {

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
    /**
     * 能力属性(0:不具备 1:具备)
     */
    private String attribute;
    /**
     * 订阅(0:不订阅 1:订阅)
     */
    private String subscribe;

    /**
     * 订阅人
     */
    private String userId;
    private User user;
    private List<User> userList;

    public static final String NOT_POSSESS = "0";
    public static final String POSSESS = "1";

    List<TestCategoryAttr> attributes;

    public TestCategoryAttr() {
    }

    public TestCategoryAttr(String cId, String vId, String labId) {
        this.cId = cId;
        this.vId = vId;
        this.labId = labId;
    }

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

    public TestCategoryAttr setcId(String cId) {
        this.cId = cId;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryAttr setvId(String vId) {
        this.vId = vId;
        return this;
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

    public String getAttribute() {
        return attribute;
    }

    public TestCategoryAttr setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    public List<TestCategoryAttr> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<TestCategoryAttr> attributes) {
        this.attributes = attributes;
    }

    public SubCenter getSubCenter() {
        return subCenter;
    }

    public TestCategoryAttr setSubCenter(SubCenter subCenter) {
        this.subCenter = subCenter;
        return this;
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
