package com.demxs.tdm.domain.resource.yangpin;


import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 调度关联样品组Entity
 * @author 詹小梅
 * @version 2017-06-22
 */
public class DiaoduYp extends DataEntity<DiaoduYp> {
	
	private static final long serialVersionUID = 1L;
	private String shunxu;		// 组样品/单样品顺序
	private String yangpinlb;		// 样品类别
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
	private String  yangpinid;	//样品ID
	private String yangpinzid;		// 样品组id(申请样品主键zy_weituo_yp)
	private String yangpinxlh;		// 样品编号序列号（样品组）
	private String chushibmxlh;		// 初始编码序列号（样品组）
	private String diaoduzj;		// 调度主键
	private String yangpinsysl;     //样品组剩余数量
	private String yangpincz;    //样品材质----新增字段（20170808）
	
	public DiaoduYp() {
		super();
	}

	public DiaoduYp(String id){
		super(id);
	}

	@Length(min=0, max=200, message="组样品/单样品顺序长度必须介于 0 和 200 之间")
	public String getShunxu() {
		return shunxu;
	}

	public void setShunxu(String shunxu) {
		this.shunxu = shunxu;
	}
	
	@Length(min=0, max=200, message="样品类别长度必须介于 0 和 200 之间")
	public String getYangpinlb() {
		return yangpinlb;
	}

	public void setYangpinlb(String yangpinlb) {
		this.yangpinlb = yangpinlb;
	}
	
	@Length(min=0, max=200, message="样品名称长度必须介于 0 和 200 之间")
	public String getYangpinmc() {
		return yangpinmc;
	}

	public void setYangpinmc(String yangpinmc) {
		this.yangpinmc = yangpinmc;
	}
	
	@Length(min=0, max=200, message="数量长度必须介于 0 和 200 之间")
	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}
	
	@Length(min=0, max=200, message="编码前缀长度必须介于 0 和 200 之间")
	public String getBianmaqz() {
		return bianmaqz;
	}

	public void setBianmaqz(String bianmaqz) {
		this.bianmaqz = bianmaqz;
	}
	
	@Length(min=0, max=200, message="初始编码长度必须介于 0 和 200 之间")
	public String getChushibm() {
		return chushibm;
	}

	public void setChushibm(String chushibm) {
		this.chushibm = chushibm;
	}
	
	@Length(min=0, max=200, message="保存要求长度必须介于 0 和 200 之间")
	public String getBaocunyq() {
		return baocunyq;
	}

	public void setBaocunyq(String baocunyq) {
		this.baocunyq = baocunyq;
	}
	
	@Length(min=0, max=200, message="是否留样长度必须介于 0 和 200 之间")
	public String getShifouly() {
		return shifouly;
	}

	public void setShifouly(String shifouly) {
		this.shifouly = shifouly;
	}
	
	@Length(min=0, max=200, message="留样数量长度必须介于 0 和 200 之间")
	public String getLiuyangsl() {
		return liuyangsl;
	}

	public void setLiuyangsl(String liuyangsl) {
		this.liuyangsl = liuyangsl;
	}
	
	@Length(min=0, max=200, message="留样期限长度必须介于 0 和 200 之间")
	public String getLiuyangqx() {
		return liuyangqx;
	}

	public void setLiuyangqx(String liuyangqx) {
		this.liuyangqx = liuyangqx;
	}
	
	@Length(min=0, max=200, message="是否需要试验室制样长度必须介于 0 和 200 之间")
	public String getShifouxysyszy() {
		return shifouxysyszy;
	}

	public void setShifouxysyszy(String shifouxysyszy) {
		this.shifouxysyszy = shifouxysyszy;
	}
	
	@Length(min=0, max=200, message="样品备注长度必须介于 0 和 200 之间")
	public String getYangpinbz() {
		return yangpinbz;
	}

	public void setYangpinbz(String yangpinbz) {
		this.yangpinbz = yangpinbz;
	}
	
	@Length(min=0, max=200, message="危险性说明长度必须介于 0 和 200 之间")
	public String getWeixianxsm() {
		return weixianxsm;
	}

	public void setWeixianxsm(String weixianxsm) {
		this.weixianxsm = weixianxsm;
	}
	
	@Length(min=0, max=200, message="制样说明长度必须介于 0 和 200 之间")
	public String getZhiyangsm() {
		return zhiyangsm;
	}

	public void setZhiyangsm(String zhiyangsm) {
		this.zhiyangsm = zhiyangsm;
	}
	
	@Length(min=0, max=200, message="申请单主键（业务数据）长度必须介于 0 和 200 之间")
	public String getWeituodzj() {
		return weituodzj;
	}

	public void setWeituodzj(String weituodzj) {
		this.weituodzj = weituodzj;
	}
	
	@Length(min=0, max=200, message="方法主键：为最初申请单下发时的方法关系主键，而非重新生成任务的方法主键长度必须介于 0 和 200 之间")
	public String getFangfazj() {
		return fangfazj;
	}

	public void setFangfazj(String fangfazj) {
		this.fangfazj = fangfazj;
	}
	
	@Length(min=0, max=200, message="拆样主键长度必须介于 0 和 200 之间")
	public String getChaiyangzj() {
		return chaiyangzj;
	}

	public void setChaiyangzj(String chaiyangzj) {
		this.chaiyangzj = chaiyangzj;
	}
	
	@Length(min=0, max=200, message="是否母样长度必须介于 0 和 200 之间")
	public String getShifoumy() {
		return shifoumy;
	}

	public void setShifoumy(String shifoumy) {
		this.shifoumy = shifoumy;
	}
	
	@Length(min=0, max=200, message="样品状态：0:待入库(样品组)，1:入库(样品组)，2:出库，3:在检，4:试验完成 , 5:返库(样品)长度必须介于 0 和 200 之间")
	public String getYangpinzt() {
		return yangpinzt;
	}

	public void setYangpinzt(String yangpinzt) {
		this.yangpinzt = yangpinzt;
	}
	
	@Length(min=0, max=200, message="入库时间：填写入库时更新长度必须介于 0 和 200 之间")
	public String getRukusj() {
		return rukusj;
	}

	public void setRukusj(String rukusj) {
		this.rukusj = rukusj;
	}
	
	@Length(min=0, max=2000, message="存放位置长度必须介于 0 和 2000 之间")
	public String getCunfangwz() {
		return cunfangwz;
	}

	public void setCunfangwz(String cunfangwz) {
		this.cunfangwz = cunfangwz;
	}
	
	@Length(min=0, max=200, message="送样人ID长度必须介于 0 和 200 之间")
	public String getSongyangrid() {
		return songyangrid;
	}

	public void setSongyangrid(String songyangrid) {
		this.songyangrid = songyangrid;
	}
	
	@Length(min=0, max=200, message="期间核查标准物质ID(如果为期间核查标准物质，在样品库管理界面不显示)长度必须介于 0 和 200 之间")
	public String getQijianhcbzwzid() {
		return qijianhcbzwzid;
	}

	public void setQijianhcbzwzid(String qijianhcbzwzid) {
		this.qijianhcbzwzid = qijianhcbzwzid;
	}
	
	@Length(min=0, max=200, message="样品编号长度必须介于 0 和 200 之间")
	public String getYangpinbh() {
		return yangpinbh;
	}

	public void setYangpinbh(String yangpinbh) {
		this.yangpinbh = yangpinbh;
	}

	@Length(min=0, max=200, message="样品组号长度必须介于 0 和 200 之间")
	public String getYangpinzh() {
		return yangpinzh;
	}

	public void setYangpinzh(String yangpinzh) {
		this.yangpinzh = yangpinzh;
	}
	
	@Length(min=0, max=200, message="样品型号长度必须介于 0 和 200 之间")
	public String getYangpinxh() {
		return yangpinxh;
	}

	public void setYangpinxh(String yangpinxh) {
		this.yangpinxh = yangpinxh;
	}
	
	@Length(min=0, max=200, message="检后样品处理方式长度必须介于 0 和 200 之间")
	public String getJianhouypclfs() {
		return jianhouypclfs;
	}

	public void setJianhouypclfs(String jianhouypclfs) {
		this.jianhouypclfs = jianhouypclfs;
	}
	
	@Length(min=0, max=200, message="剩余样品处理方式长度必须介于 0 和 200 之间")
	public String getShengyuypclfs() {
		return shengyuypclfs;
	}

	public void setShengyuypclfs(String shengyuypclfs) {
		this.shengyuypclfs = shengyuypclfs;
	}
	
	@Length(min=0, max=2000, message="存放位置id长度必须介于 0 和 2000 之间")
	public String getCunfangwzid() {
		return cunfangwzid;
	}

	public void setCunfangwzid(String cunfangwzid) {
		this.cunfangwzid = cunfangwzid;
	}
	
	@Length(min=0, max=200, message="是否标准物质 0：是 1：否长度必须介于 0 和 200 之间")
	public String getShifoubzwz() {
		return shifoubzwz;
	}

	public void setShifoubzwz(String shifoubzwz) {
		this.shifoubzwz = shifoubzwz;
	}
	
	@Length(min=0, max=200, message="送样人长度必须介于 0 和 200 之间")
	public String getSongyangr() {
		return songyangr;
	}

	public void setSongyangr(String songyangr) {
		this.songyangr = songyangr;
	}
	
	@Length(min=0, max=200, message="业务单号(来自申请单)长度必须介于 0 和 200 之间")
	public String getYewudh() {
		return yewudh;
	}

	public void setYewudh(String yewudh) {
		this.yewudh = yewudh;
	}
	
	@Length(min=0, max=200, message="业务单名称长度必须介于 0 和 200 之间")
	public String getYewudmc() {
		return yewudmc;
	}

	public void setYewudmc(String yewudmc) {
		this.yewudmc = yewudmc;
	}
	
	@Length(min=0, max=200, message="样品组id长度必须介于 0 和 200 之间")
	public String getYangpinzid() {
		return yangpinzid;
	}

	public void setYangpinzid(String yangpinzid) {
		this.yangpinzid = yangpinzid;
	}
	
	@Length(min=0, max=200, message="样品编号序列号（样品组）长度必须介于 0 和 200 之间")
	public String getYangpinxlh() {
		return yangpinxlh;
	}

	public void setYangpinxlh(String yangpinxlh) {
		this.yangpinxlh = yangpinxlh;
	}
	
	@Length(min=0, max=200, message="初始编码序列号（样品组）长度必须介于 0 和 200 之间")
	public String getChushibmxlh() {
		return chushibmxlh;
	}

	public void setChushibmxlh(String chushibmxlh) {
		this.chushibmxlh = chushibmxlh;
	}
	
	@Length(min=0, max=200, message="调度主键长度必须介于 0 和 200 之间")
	public String getDiaoduzj() {
		return diaoduzj;
	}

	public void setDiaoduzj(String diaoduzj) {
		this.diaoduzj = diaoduzj;
	}

	public String getYangpinsysl() {
		return yangpinsysl;
	}

	public void setYangpinsysl(String yangpinsysl) {
		this.yangpinsysl = yangpinsysl;
	}

	public String getYangpincz() {
		return yangpincz;
	}

	public void setYangpincz(String yangpincz) {
		this.yangpincz = yangpincz;
	}

	public String getYangpinid() {
		return yangpinid;
	}

	public void setYangpinid(String yangpinid) {
		this.yangpinid = yangpinid;
	}
}