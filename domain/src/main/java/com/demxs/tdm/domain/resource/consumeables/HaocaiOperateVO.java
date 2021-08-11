package com.demxs.tdm.domain.resource.consumeables;

import com.demxs.tdm.common.sys.entity.User;
import com.google.common.collect.Lists;

import java.util.List;

public class HaocaiOperateVO {


    private List<Haocaiku> haocaikuList = Lists.newArrayList();

    private User operateUser;

    private HaocaiCfwz haocaiCfwz;

    public List<Haocaiku> getHaocaikuList() {
        return haocaikuList;
    }

    public void setHaocaikuList(List<Haocaiku> haocaikuList) {
        this.haocaikuList = haocaikuList;
    }

    public User getOperateUser() {
        return operateUser;
    }

    public void setOperateUser(User operateUser) {
        this.operateUser = operateUser;
    }

    public HaocaiCfwz getHaocaiCfwz() {
        return haocaiCfwz;
    }

    public void setHaocaiCfwz(HaocaiCfwz haocaiCfwz) {
        this.haocaiCfwz = haocaiCfwz;
    }
}
