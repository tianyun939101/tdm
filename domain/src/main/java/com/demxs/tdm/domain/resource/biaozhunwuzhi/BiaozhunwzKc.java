package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * 库存 （库中没有对应的表，仅用于封装）
 * Created by zhangdengcai
 * Create date 2017/7/18.
 */
public class BiaozhunwzKc extends DataEntity<BiaozhunwzKc> {
    private String bianma;      //编码
    private String biaozhunwzmc;      //名称
    private String biaozhunwzxh;      //型号
    private String jiliangdw;      //计量单位
    private String shuliang;        //数量
    private String anquankcsl;      // 安全库存数量
    private String biaozhunwzzt;    //标准物质状态

    public String getBianma() {
        return bianma;
    }

    public void setBianma(String bianma) {
        this.bianma = bianma;
    }

    public String getBiaozhunwzmc() {
        return biaozhunwzmc;
    }

    public void setBiaozhunwzmc(String biaozhunwzmc) {
        this.biaozhunwzmc = biaozhunwzmc;
    }

    public String getBiaozhunwzxh() {
        return biaozhunwzxh;
    }

    public void setBiaozhunwzxh(String biaozhunwzxh) {
        this.biaozhunwzxh = biaozhunwzxh;
    }

    public String getJiliangdw() {
        return jiliangdw;
    }

    public void setJiliangdw(String jiliangdw) {
        this.jiliangdw = jiliangdw;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getBiaozhunwzzt() {
        return biaozhunwzzt;
    }

    public void setBiaozhunwzzt(String biaozhunwzzt) {
        this.biaozhunwzzt = biaozhunwzzt;
    }

    public String getAnquankcsl() {
        return anquankcsl;
    }

    public void setAnquankcsl(String anquankcsl) {
        this.anquankcsl = anquankcsl;
    }
}
