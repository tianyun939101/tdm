package com.demxs.tdm.domain.resource.consumeables;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;

public class HaocaiRecord extends DataEntity<HaocaiRecord> {

    private String type;//动作

    private String haocaimc;//耗材名称

    private String haocaibh;//耗材编号

    private HaocaiLx haocaiLx;//耗材类型

    private String haocaiguige;//耗材规格

    private Integer inNumber;//入库数量

    private Integer outNumber;//出库数量

    private Integer backNumber;//反库数量

    private String jiliangdw;//计量单位

    private User acceptUser;//领取人

    private User backUser;//归还人


    public HaocaiRecord() {
    }
    public HaocaiRecord(String id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHaocaimc() {
        return haocaimc;
    }

    public void setHaocaimc(String haocaimc) {
        this.haocaimc = haocaimc;
    }

    public String getHaocaibh() {
        return haocaibh;
    }

    public void setHaocaibh(String haocaibh) {
        this.haocaibh = haocaibh;
    }

    public HaocaiLx getHaocaiLx() {
        return haocaiLx;
    }

    public void setHaocaiLx(HaocaiLx haocaiLx) {
        this.haocaiLx = haocaiLx;
    }

    public String getHaocaiguige() {
        return haocaiguige;
    }

    public void setHaocaiguige(String haocaiguige) {
        this.haocaiguige = haocaiguige;
    }

    public Integer getInNumber() {
        return inNumber;
    }

    public void setInNumber(Integer inNumber) {
        this.inNumber = inNumber;
    }

    public Integer getOutNumber() {
        return outNumber;
    }

    public void setOutNumber(Integer outNumber) {
        this.outNumber = outNumber;
    }

    public Integer getBackNumber() {
        return backNumber;
    }

    public void setBackNumber(Integer backNumber) {
        this.backNumber = backNumber;
    }

    public String getJiliangdw() {
        return jiliangdw;
    }

    public void setJiliangdw(String jiliangdw) {
        this.jiliangdw = jiliangdw;
    }

    public User getAcceptUser() {
        return acceptUser;
    }

    public void setAcceptUser(User acceptUser) {
        this.acceptUser = acceptUser;
    }

    public User getBackUser() {
        return backUser;
    }

    public void setBackUser(User backUser) {
        this.backUser = backUser;
    }
}
