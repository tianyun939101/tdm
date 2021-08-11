package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;

/**
 * @author: Jason
 * @Date: 2020/8/27 17:47
 * @Description: 试验能力关注者实体类
 */
public class TestCategoryObserver extends DataEntity<TestCategoryObserver> {

    private static final long serialVersionUID = 1L;
    /**
     * 关注者
     */
    private String userId;
    private User user;
    /**
     * 关注试验能力
     */
    private String cId;
    private TestCategory testCategory;
    /**
     * 该试验能力版本
     */
    private String vId;
    private TestCategoryVersion version;

    public TestCategoryObserver() {
    }

    public TestCategoryObserver(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public TestCategoryObserver setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public TestCategoryObserver setUser(User user) {
        this.user = user;
        return this;
    }

    public String getcId() {
        return cId;
    }

    public TestCategoryObserver setcId(String cId) {
        this.cId = cId;
        return this;
    }

    public TestCategory getTestCategory() {
        return testCategory;
    }

    public TestCategoryObserver setTestCategory(TestCategory testCategory) {
        this.testCategory = testCategory;
        return this;
    }

    public String getvId() {
        return vId;
    }

    public TestCategoryObserver setvId(String vId) {
        this.vId = vId;
        return this;
    }

    public TestCategoryVersion getVersion() {
        return version;
    }

    public TestCategoryObserver setVersion(TestCategoryVersion version) {
        this.version = version;
        return this;
    }
}
