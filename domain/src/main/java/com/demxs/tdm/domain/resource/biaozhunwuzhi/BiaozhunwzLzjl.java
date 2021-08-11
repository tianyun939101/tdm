package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;

/**
 * 标准物质流转记录Entity
 * @author zhangdengcai
 * @version 2017-07-06
 */
public class BiaozhunwzLzjl extends DataEntity<BiaozhunwzLzjl> {
    private String liuxiang;     //流转类型（流向）
    private String biaozhunwzbh;    //标准物质编号
    private String biaozhunwzmc;    //标准物质名称
    private String guigexh;    //标准物质型号
    private String jiliangdw;   //计量单位
    private String zhengshuh;   //证书号
    private String guihuanr;    //归还人
    private String lingqur;    //领取人
    private String caozuormc;     //入库人/领取人/归还人名称
    private String dengjirmc;     //登记人名称
    private String riqi;   //日期
    private String biaozhunwzzj;

    private String kaishirq;    //开始日期（查询时接收和传递值）
    private String jieshurq;    //结束日期（查询时接收和传递值）
    private String jiancegcs;   //试验工程师
    private String renwumc;     //任务名称

    private User dangqianR;//当前人
    private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

    public BiaozhunwzLzjl() {
        super();
    }
    public BiaozhunwzLzjl(String id){
        super(id);
    }

    public String getBiaozhunwzbh() {
        return biaozhunwzbh;
    }

    public void setBiaozhunwzbh(String biaozhunwzbh) {
        this.biaozhunwzbh = biaozhunwzbh;
    }

    public String getBiaozhunwzmc() {
        return biaozhunwzmc;
    }

    public void setBiaozhunwzmc(String biaozhunwzmc) {
        this.biaozhunwzmc = biaozhunwzmc;
    }

    public String getJiliangdw() {
        return jiliangdw;
    }

    public void setJiliangdw(String jiliangdw) {
        this.jiliangdw = jiliangdw;
    }

    public String getZhengshuh() {
        return zhengshuh;
    }

    public void setZhengshuh(String zhengshuh) {
        this.zhengshuh = zhengshuh;
    }

    public String getGuihuanr() {
        return guihuanr;
    }

    public void setGuihuanr(String guihuanr) {
        this.guihuanr = guihuanr;
    }

    public String getLingqur() {
        return lingqur;
    }

    public void setLingqur(String lingqur) {
        this.lingqur = lingqur;
    }

    public String getRiqi() {
        return riqi;
    }

    public void setRiqi(String riqi) {
        this.riqi = riqi;
    }

    public String getKaishirq() {
        return kaishirq;
    }

    public void setKaishirq(String kaishirq) {
        this.kaishirq = kaishirq;
    }

    public String getJieshurq() {
        return jieshurq;
    }

    public void setJieshurq(String jieshurq) {
        this.jieshurq = jieshurq;
    }

    public String getLiuxiang() {
        return liuxiang;
    }

    public void setLiuxiang(String liuxiang) {
        this.liuxiang = liuxiang;
    }

    public String getGuigexh() {
        return guigexh;
    }

    public void setGuigexh(String guigexh) {
        this.guigexh = guigexh;
    }

    public String getBiaozhunwzzj() {
        return biaozhunwzzj;
    }

    public void setBiaozhunwzzj(String biaozhunwzzj) {
        this.biaozhunwzzj = biaozhunwzzj;
    }

    public User getDangqianR() {
        return dangqianR;
    }

    public void setDangqianR(User dangqianR) {
        this.dangqianR = dangqianR;
    }

    public String getCaozuoqx() {
        return caozuoqx;
    }

    public void setCaozuoqx(String caozuoqx) {
        this.caozuoqx = caozuoqx;
    }

    public String getJiancegcs() {
        return jiancegcs;
    }

    public void setJiancegcs(String jiancegcs) {
        this.jiancegcs = jiancegcs;
    }

    public String getRenwumc() {
        return renwumc;
    }

    public void setRenwumc(String renwumc) {
        this.renwumc = renwumc;
    }

    public String getCaozuormc() {
        return caozuormc;
    }

    public void setCaozuormc(String caozuormc) {
        this.caozuormc = caozuormc;
    }

    public String getDengjirmc() {
        return dengjirmc;
    }

    public void setDengjirmc(String dengjirmc) {
        this.dengjirmc = dengjirmc;
    }
}
