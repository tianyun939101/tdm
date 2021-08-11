package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;

/**
 * 非标资源分配其他人员entity
 */
public class NoStandardOtherUser extends DataEntity<NoStandardOtherUser> {

    private static final long serialVersionUID = 1L;
    private String name;
    private String resourceId;
    private String userId;
    private Yuangong user;

    public NoStandardOtherUser() {
        super();
    }

    public NoStandardOtherUser(String id){
        super(id);
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Yuangong getUser() {
        return user;
    }

    public void setUser(Yuangong user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public NoStandardOtherUser setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getName() {
        if(null != user && StringUtils.isNotBlank(user.getUserName())){
            return user.getUserName();
        }
        return name;
    }

    public NoStandardOtherUser setName(String name) {
        this.name = name;
        return this;
    }
}
