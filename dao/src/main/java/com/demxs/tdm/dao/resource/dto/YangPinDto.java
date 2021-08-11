package com.demxs.tdm.dao.resource.dto;


/**
 * Created by guojinlong on 2017/6/27.
 */
public class YangPinDto{
    private static final long serialVersionUID = 1L;
    private String id;		// ID
    private String shunxu;		// 组样品/单样品顺序
    private String yangpinlb;		// 样品类别（存储的是样品类别ID）
    private String yangpinlbid;		// 样品类别ID(表中实不存在该字段，用于申请业务)
    private String yangpinmc;		// 样品名称
    private String shuliang;		// 数量
    private String bianmaqz;		// 编码前缀
    private String chushibm;		// 初始编码
    private String baocunyq;		// 保存要求
    private String shifouly;		// 是否留样
    private String liuyangsl;		// 留样数量
    private String liuyangqx;		// 留样期限
    private String shifouxysyszy;		// 是否需要试验室制样
    private String yangpinbz;		// 样品备注
    private String weixianxsm;		// 危险性说明
    private String zhiyangsm;		// 制样说明
    private String weituodzj;		// 申请单主键（业务数据）
    private String fangfazj;		// 方法主键：为最初申请单下发时的方法关系主键，而非重新生成任务的方法主键
    private String chaiyangzj;		// 拆样主键
    private String shifoumy;		// 是否母样
    private String yangpinzt;		// 样品状态：0:待入库(样品组)，1:入库(样品组)，2:出库，3:在检，4:试验完成 , 5:返库(样品)
    private String rukusj;		// 入库时间：填写入库时更新
    private String cunfangwz;		// 存放位置
    private String songyangrid;		// 送样人ID
    private String qijianhcbzwzid;		// 期间核查标准物质ID(如果为期间核查标准物质，在样品库管理界面不显示)
    private String yangpinbh;		// 样品编号
    private String yangpinzh;		// 样品组号
    private String yangpinxh;		// 样品型号
    private String jianhouypclfs;		// 检后样品处理方式
    private String shengyuypclfs;		// 剩余样品处理方式
    private String cunfangwzid;		// 存放位置id
    private String shifoubzwz;		// 是否标准物质 0：是 1：否
    private String songyangr;		// 送样人
    private String yewudh;		// 业务单号(来自申请单)
    private String yewudmc;		// 业务单名称
    private String yangpinxlh;		// 样品编号序列号（样品组）
    private String chushibmxlh;		// 初始编码序列号（样品组）
    private String  yangpinid;
    private String faqidw;
    private String lianxifs;
    private String faqirszbm;
    private String yewufqr;
    private String yangpinsysl; //样品剩余数量
    private String yangpinzid;		// 样品组id(申请样品主键zy_weituo_yp)
    private String yangpincz;    //样品材质----新增字段（20170808）
    private String fanganid;    //方案ID




    /**=================调度部分========================*/
    private String diaoduzj;        // 调度主键

    /**==================任务部分==========================*/
    private String renwuzj;        // 任务主键

    public String getShunxu() {
        return shunxu;
    }

    public void setShunxu(String shunxu) {
        this.shunxu = shunxu;
    }

    public String getYangpinlb() {
        return yangpinlb;
    }

    public void setYangpinlb(String yangpinlb) {
        this.yangpinlb = yangpinlb;
    }

    public String getYangpinmc() {
        return yangpinmc;
    }

    public void setYangpinmc(String yangpinmc) {
        this.yangpinmc = yangpinmc;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getBianmaqz() {
        return bianmaqz;
    }

    public void setBianmaqz(String bianmaqz) {
        this.bianmaqz = bianmaqz;
    }

    public String getChushibm() {
        return chushibm;
    }

    public void setChushibm(String chushibm) {
        this.chushibm = chushibm;
    }

    public String getBaocunyq() {
        return baocunyq;
    }

    public void setBaocunyq(String baocunyq) {
        this.baocunyq = baocunyq;
    }

    public String getShifouly() {
        return shifouly;
    }

    public void setShifouly(String shifouly) {
        this.shifouly = shifouly;
    }

    public String getLiuyangsl() {
        return liuyangsl;
    }

    public void setLiuyangsl(String liuyangsl) {
        this.liuyangsl = liuyangsl;
    }

    public String getLiuyangqx() {
        return liuyangqx;
    }

    public void setLiuyangqx(String liuyangqx) {
        this.liuyangqx = liuyangqx;
    }

    public String getShifouxysyszy() {
        return shifouxysyszy;
    }

    public void setShifouxysyszy(String shifouxysyszy) {
        this.shifouxysyszy = shifouxysyszy;
    }

    public String getYangpinbz() {
        return yangpinbz;
    }

    public void setYangpinbz(String yangpinbz) {
        this.yangpinbz = yangpinbz;
    }

    public String getWeixianxsm() {
        return weixianxsm;
    }

    public void setWeixianxsm(String weixianxsm) {
        this.weixianxsm = weixianxsm;
    }

    public String getZhiyangsm() {
        return zhiyangsm;
    }

    public void setZhiyangsm(String zhiyangsm) {
        this.zhiyangsm = zhiyangsm;
    }

    public String getWeituodzj() {
        return weituodzj;
    }

    public void setWeituodzj(String weituodzj) {
        this.weituodzj = weituodzj;
    }

    public String getFangfazj() {
        return fangfazj;
    }

    public void setFangfazj(String fangfazj) {
        this.fangfazj = fangfazj;
    }

    public String getChaiyangzj() {
        return chaiyangzj;
    }

    public void setChaiyangzj(String chaiyangzj) {
        this.chaiyangzj = chaiyangzj;
    }

    public String getShifoumy() {
        return shifoumy;
    }

    public void setShifoumy(String shifoumy) {
        this.shifoumy = shifoumy;
    }

    public String getYangpinzt() {
        return yangpinzt;
    }

    public void setYangpinzt(String yangpinzt) {
        this.yangpinzt = yangpinzt;
    }

    public String getRukusj() {
        return rukusj;
    }

    public void setRukusj(String rukusj) {
        this.rukusj = rukusj;
    }

    public String getCunfangwz() {
        return cunfangwz;
    }

    public void setCunfangwz(String cunfangwz) {
        this.cunfangwz = cunfangwz;
    }

    public String getSongyangrid() {
        return songyangrid;
    }

    public void setSongyangrid(String songyangrid) {
        this.songyangrid = songyangrid;
    }

    public String getQijianhcbzwzid() {
        return qijianhcbzwzid;
    }

    public void setQijianhcbzwzid(String qijianhcbzwzid) {
        this.qijianhcbzwzid = qijianhcbzwzid;
    }

    public String getYangpinbh() {
        return yangpinbh;
    }

    public void setYangpinbh(String yangpinbh) {
        this.yangpinbh = yangpinbh;
    }

    public String getYangpinzh() {
        return yangpinzh;
    }

    public void setYangpinzh(String yangpinzh) {
        this.yangpinzh = yangpinzh;
    }

    public String getYangpinxh() {
        return yangpinxh;
    }

    public void setYangpinxh(String yangpinxh) {
        this.yangpinxh = yangpinxh;
    }

    public String getJianhouypclfs() {
        return jianhouypclfs;
    }

    public void setJianhouypclfs(String jianhouypclfs) {
        this.jianhouypclfs = jianhouypclfs;
    }

    public String getShengyuypclfs() {
        return shengyuypclfs;
    }

    public void setShengyuypclfs(String shengyuypclfs) {
        this.shengyuypclfs = shengyuypclfs;
    }

    public String getCunfangwzid() {
        return cunfangwzid;
    }

    public void setCunfangwzid(String cunfangwzid) {
        this.cunfangwzid = cunfangwzid;
    }

    public String getShifoubzwz() {
        return shifoubzwz;
    }

    public void setShifoubzwz(String shifoubzwz) {
        this.shifoubzwz = shifoubzwz;
    }

    public String getSongyangr() {
        return songyangr;
    }

    public void setSongyangr(String songyangr) {
        this.songyangr = songyangr;
    }

    public String getYewudh() {
        return yewudh;
    }

    public void setYewudh(String yewudh) {
        this.yewudh = yewudh;
    }

    public String getYewudmc() {
        return yewudmc;
    }

    public void setYewudmc(String yewudmc) {
        this.yewudmc = yewudmc;
    }

    public String getYangpinxlh() {
        return yangpinxlh;
    }

    public void setYangpinxlh(String yangpinxlh) {
        this.yangpinxlh = yangpinxlh;
    }

    public String getChushibmxlh() {
        return chushibmxlh;
    }

    public void setChushibmxlh(String chushibmxlh) {
        this.chushibmxlh = chushibmxlh;
    }

    public String getYangpinid() {
        return yangpinid;
    }

    public void setYangpinid(String yangpinid) {
        this.yangpinid = yangpinid;
    }

    public String getFaqidw() {
        return faqidw;
    }

    public void setFaqidw(String faqidw) {
        this.faqidw = faqidw;
    }

    public String getLianxifs() {
        return lianxifs;
    }

    public void setLianxifs(String lianxifs) {
        this.lianxifs = lianxifs;
    }

    public String getFaqirszbm() {
        return faqirszbm;
    }

    public void setFaqirszbm(String faqirszbm) {
        this.faqirszbm = faqirszbm;
    }

    public String getYewufqr() {
        return yewufqr;
    }

    public void setYewufqr(String yewufqr) {
        this.yewufqr = yewufqr;
    }

    public String getDiaoduzj() {
        return diaoduzj;
    }

    public void setDiaoduzj(String diaoduzj) {
        this.diaoduzj = diaoduzj;
    }

    public String getRenwuzj() {
        return renwuzj;
    }

    public void setRenwuzj(String renwuzj) {
        this.renwuzj = renwuzj;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getYangpinsysl() {
        return yangpinsysl;
    }

    public void setYangpinsysl(String yangpinsysl) {
        this.yangpinsysl = yangpinsysl;
    }

    public String getYangpinzid() {
        return yangpinzid;
    }

    public void setYangpinzid(String yangpinzid) {
        this.yangpinzid = yangpinzid;
    }

    public String getYangpinlbid() {
        return yangpinlbid;
    }

    public void setYangpinlbid(String yangpinlbid) {
        this.yangpinlbid = yangpinlbid;
    }

    public String getYangpincz() {
        return yangpincz;
    }

    public void setYangpincz(String yangpincz) {
        this.yangpincz = yangpincz;
    }

    public String getFanganid() {
        return fanganid;
    }

    public void setFanganid(String fanganid) {
        this.fanganid = fanganid;
    }
}
