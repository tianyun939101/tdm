package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

import java.util.Map;

/**
 * 样品入库/出库/返库操作Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class YangpinCkfkd extends DataEntity<YangpinCkfkd> {
	
	private static final long serialVersionUID = 1L;
	private String yangpinzj;		// 样品主键
	private String yangpinlx;		// 样品类型
	private String yangpinr;		// 领样人/来样人
	private String chukurq;		// 入库日期/出库日期/返库日期
	private String dengjirid;		// 登记人ID
	private String dengjir;		// 登记人
	private String leixing;		// 样品流向: 0:入库,1:出库,2:返库
	private String chukuglrwid;		// 出库关联任务ID
	private String shuliang;		// 入库/出库/返库数量
	private String yangpinzid;		// 样品组ID
	private String yangpinrid;		// 领样人/来样人id
	private Attachment[] tupianlist; //样品照片
	private Attachment[] shujulist;//数据文件
	private String guihuanrid;		//归还人id
	private String guihuanr;		//归还人
	private String shujubq;			//数据标签（一个出库单的样品对应一个标签）
	//=======查询
	private String yangpinbh;       //样品编号
	private String yangpinmc;		//样品名称
	private String yewudh;          //业务单号
	private String danweiid;		//单位id
	private String danweimc;		//单位名称
	private String beginDate;		//开始时间
	private String endDate;			//结束时间
	private Map yangpinsx;			//样品属性
	private Yangpin[] yangpinzjs;   //样品id
	private String songyangr;		//送样人
	private String songyangrid;		//送样人id
	//==============查询==============//
	private String filed;			//
	private String addSqlParam;		//
	private String filedNames;
	private String ckdid;
	private YangpinSx[] shuxing;
	//==============查询==============//
	private String cunfangwzid;    //存放位置
	private String cunfangwz;    //存放位置
	private String dsf;

	private String renwumc; //任务名称
	private String jiancegcs; //试验工程师


	private String type;//  1:正常  2:出库单样品排序(如果是出库单样品编号 正序)

	public String getCunfangwz() {
		return cunfangwz;
	}

	public void setCunfangwz(String cunfangwz) {
		this.cunfangwz = cunfangwz;
	}

	public String getRenwumc() {
		return renwumc;
	}

	public void setRenwumc(String renwumc) {
		this.renwumc = renwumc;
	}

	public String getJiancegcs() {
		return jiancegcs;
	}

	public void setJiancegcs(String jiancegcs) {
		this.jiancegcs = jiancegcs;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSongyangr() {
		return songyangr;
	}

	public void setSongyangr(String songyangr) {
		this.songyangr = songyangr;
	}

	public String getSongyangrid() {
		return songyangrid;
	}

	public void setSongyangrid(String songyangrid) {
		this.songyangrid = songyangrid;
	}

	public String getCunfangwzid() {
		return cunfangwzid;
	}

	public void setCunfangwzid(String cunfangwzid) {
		this.cunfangwzid = cunfangwzid;
	}

	public YangpinCkfkd() {
		super();
	}

	public YangpinCkfkd(String id){
		super(id);
	}

	public String getYangpinzj() {
		return yangpinzj;
	}

	public Yangpin[] getYangpinzjs() {
		return yangpinzjs;
	}

	public void setYangpinzjs(Yangpin[] yangpinzjs) {
		this.yangpinzjs = yangpinzjs;
	}

	@Length(min=0, max=200, message="样品流向：0:入库 1:出库试验、2:样品归还、3:自行处理；4:返库长度必须介于 0 和 200 之间")
	public String getYangpinlx() {
		return yangpinlx;
	}

	public void setYangpinlx(String yangpinlx) {
		this.yangpinlx = yangpinlx;
	}
	
	@Length(min=0, max=200, message="领样人/来样人长度必须介于 0 和 200 之间")
	public String getYangpinr() {
		return yangpinr;
	}

	public void setYangpinr(String yangpinr) {
		this.yangpinr = yangpinr;
	}
	
	@Length(min=0, max=200, message="入库日期/出库日期/返库日期长度必须介于 0 和 200 之间")
	public String getChukurq() {
		return chukurq;
	}

	public void setChukurq(String chukurq) {
		this.chukurq = chukurq;
	}

	public void setYangpinzj(String yangpinzj) {
		this.yangpinzj = yangpinzj;
	}

	public Map getYangpinsx() {
		return yangpinsx;
	}

	public void setYangpinsx(Map yangpinsx) {
		this.yangpinsx = yangpinsx;
	}

	@Length(min=0, max=200, message="登记人长度必须介于 0 和 200 之间")
	public String getDengjir() {
		return dengjir;
	}

	public void setDengjir(String dengjir) {
		this.dengjir = dengjir;
	}
	
	@Length(min=0, max=200, message="类型: 0:入库,1:出库,2:返库长度必须介于 0 和 200 之间")
	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}
	
	@Length(min=0, max=200, message="出库关联任务ID长度必须介于 0 和 200 之间")
	public String getChukuglrwid() {
		return chukuglrwid;
	}

	public void setChukuglrwid(String chukuglrwid) {
		this.chukuglrwid = chukuglrwid;
	}
	
	@Length(min=0, max=200, message="入库/出库/返库数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="样品组ID长度必须介于 0 和 200 之间")
	public String getYangpinzid() {
		return yangpinzid;
	}

	public void setYangpinzid(String yangpinzid) {
		this.yangpinzid = yangpinzid;
	}

	public String getDengjirid() {
		return dengjirid;
	}

	public void setDengjirid(String dengjirid) {
		this.dengjirid = dengjirid;
	}

	public String getYangpinrid() {
		return yangpinrid;
	}

	public void setYangpinrid(String yangpinrid) {
		this.yangpinrid = yangpinrid;
	}



	public Attachment[] getShujulist() {
		return shujulist;
	}

	public void setShujulist(Attachment[] shujulist) {
		this.shujulist = shujulist;
	}

	public String getYangpinbh() {
		return yangpinbh;
	}

	public void setYangpinbh(String yangpinbh) {
		this.yangpinbh = yangpinbh;
	}

	public String getYangpinmc() {
		return yangpinmc;
	}

	public void setYangpinmc(String yangpinmc) {
		this.yangpinmc = yangpinmc;
	}

	public String getYewudh() {
		return yewudh;
	}

	public void setYewudh(String yewudh) {
		this.yewudh = yewudh;
	}

	public String getDanweimc() {
		return danweimc;
	}

	public void setDanweimc(String danweimc) {
		this.danweimc = danweimc;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getGuihuanr() {
		return guihuanr;
	}

	public void setGuihuanr(String guihuanr) {
		this.guihuanr = guihuanr;
	}

	public String getGuihuanrid() {
		return guihuanrid;
	}

	public void setGuihuanrid(String guihuanrid) {
		this.guihuanrid = guihuanrid;
	}

	public String getDanweiid() {
		return danweiid;
	}

	public void setDanweiid(String danweiid) {
		this.danweiid = danweiid;
	}

	public String getFiled() {
		return filed;
	}

	public void setFiled(String filed) {
		this.filed = filed;
	}

	public String getAddSqlParam() {
		return addSqlParam;
	}

	public void setAddSqlParam(String addSqlParam) {
		this.addSqlParam = addSqlParam;
	}

	public String getFiledNames() {
		return filedNames;
	}

	public void setFiledNames(String filedNames) {
		this.filedNames = filedNames;
	}

	public Attachment[] getTupianlist() {
		return tupianlist;
	}

	public void setTupianlist(Attachment[] tupianlist) {
		this.tupianlist = tupianlist;
	}

	public String getCkdid() {
		return ckdid;
	}

	public void setCkdid(String ckdid) {
		this.ckdid = ckdid;
	}

	public YangpinSx[] getShuxing() {
		return shuxing;
	}

	public void setShuxing(YangpinSx[] shuxing) {
		this.shuxing = shuxing;
	}

	public String getShujubq() {
		return shujubq;
	}

	public void setShujubq(String shujubq) {
		this.shujubq = shujubq;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}