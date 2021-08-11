package com.demxs.tdm.domain.resource.consumeables;


import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 耗材库信息
 * @Author sunjunhui
 */
public class Haocaiku extends DataEntity<Haocaiku> {

    private String haocaibh;//耗材编号
    private HaocaiLx haocaiLx;//耗材类型

    private String haocaimc;//耗材名称

    private String haocaiguige;//耗材规格

    private String pihao;//批号

    private Integer shuliang;//数量

    private String jiliangdw;//计量单位

    private Date youxiaoqi;//有效期

    private Integer yongjiu;//是否永久

    private HaocaiCfwz haocaiCfwz;//存放位置

    private Integer kucunyjsl;//库存预警数量

    private String danjai;//单价

    private String zonge;//总额

    private String yongtu;//用途

    private Changshanggysxx changshang;//厂商

    private String guobie;//国别

    private Changshanggysxx gongyingshang;//供应商

    private User yanshour;//验收人

    private String yanshouzl;//验收资料

    private String moneytype;//币种

    private String model;//型号

    private String admin;//耗材管理员


    private LabInfo labInfo;
    private String ids;//页面传值
    private String dateRange;//入库时间范围 页面传值
    private Date startDate;//入库开始时间
    private Date endDate;//入库结束时间

    public Haocaiku() {
    }
    public Haocaiku(String id) {
        super(id);
    }

    public Haocaiku(HaocaiLx haocaiLx) {
        this.haocaiLx = haocaiLx;
    }

    public HaocaiLx getHaocaiLx() {
        return haocaiLx;
    }

    public void setHaocaiLx(HaocaiLx haocaiLx) {
        this.haocaiLx = haocaiLx;
    }

    public String getHaocaimc() {
        return haocaimc;
    }

    public void setHaocaimc(String haocaimc) {
        this.haocaimc = haocaimc;
    }

    public String getHaocaiguige() {
        return haocaiguige;
    }

    public void setHaocaiguige(String haocaiguige) {
        this.haocaiguige = haocaiguige;
    }

    public String getPihao() {
        return pihao;
    }

    public void setPihao(String pihao) {
        this.pihao = pihao;
    }

    public Integer getShuliang() {
        return shuliang;
    }

    public void setShuliang(Integer shuliang) {
        this.shuliang = shuliang;
    }

    public String getJiliangdw() {
        return jiliangdw;
    }

    public void setJiliangdw(String jiliangdw) {
        this.jiliangdw = jiliangdw;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getYouxiaoqi() {
        return youxiaoqi;
    }

    public void setYouxiaoqi(Date youxiaoqi) {
        this.youxiaoqi = youxiaoqi;
    }

    public Integer getYongjiu() {
        return yongjiu;
    }

    public void setYongjiu(Integer yongjiu) {
        this.yongjiu = yongjiu;
    }

    public HaocaiCfwz getHaocaiCfwz() {
        return haocaiCfwz;
    }

    public void setHaocaiCfwz(HaocaiCfwz haocaiCfwz) {
        this.haocaiCfwz = haocaiCfwz;
    }

    public Integer getKucunyjsl() {
        return kucunyjsl;
    }

    public void setKucunyjsl(Integer kucunyjsl) {
        this.kucunyjsl = kucunyjsl;
    }

    public String getDanjai() {
        return danjai;
    }

    public void setDanjai(String danjai) {
        this.danjai = danjai;
    }

    public String getZonge() {
        return zonge;
    }

    public void setZonge(String zonge) {
        this.zonge = zonge;
    }

    public String getYongtu() {
        return yongtu;
    }

    public void setYongtu(String yongtu) {
        this.yongtu = yongtu;
    }

    public Changshanggysxx getChangshang() {
        return changshang;
    }

    public void setChangshang(Changshanggysxx changshang) {
        this.changshang = changshang;
    }

    public String getGuobie() {
        return guobie;
    }

    public void setGuobie(String guobie) {
        this.guobie = guobie;
    }

    public Changshanggysxx getGongyingshang() {
        return gongyingshang;
    }

    public void setGongyingshang(Changshanggysxx gongyingshang) {
        this.gongyingshang = gongyingshang;
    }

    public User getYanshour() {
        return yanshour;
    }

    public void setYanshour(User yanshour) {
        this.yanshour = yanshour;
    }

    public String getYanshouzl() {
        return yanshouzl;
    }

    public void setYanshouzl(String yanshouzl) {
        this.yanshouzl = yanshouzl;
    }

    public String getHaocaibh() {
        return haocaibh;
    }

    public void setHaocaibh(String haocaibh) {
        this.haocaibh = haocaibh;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
    @JsonIgnore
    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    @JsonIgnore
    public Date getStartDate() {
        if(StringUtils.isNotBlank(dateRange)){
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[0]);
        }else{
            return null;
        }

    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonIgnore
    public Date getEndDate() {
        if(StringUtils.isNotBlank(dateRange)){
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[1]);
        }else{
            return null;
        }
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMoneytype() {
        return moneytype;
    }

    public void setMoneytype(String moneytype) {
        this.moneytype = moneytype;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getModel() {
        return model;
    }

    public Haocaiku setModel(String model) {
        this.model = model;
        return this;
    }

    public String getAdmin() {
        return admin;
    }

    public Haocaiku setAdmin(String admin) {
        this.admin = admin;
        return this;
    }
}
