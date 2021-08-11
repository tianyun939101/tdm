package com.demxs.tdm.domain.resource.yangpin;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import org.hibernate.validator.constraints.Length;

/**
 * 样品信息操作Entity
 * @author 詹小梅
 * @version 2017-06-15
 */
public class Yangpin extends DataEntity<Yangpin> {
	
	private static final long serialVersionUID = 1L;
    private String shifoudgyp;		// 是否单个样品
	private String shunxu;		// 组样品/单样品顺序
	private String yangpinlb;		// 样品类别
	private String yangpinlbmc;		// 样品类别名称
	private String yangpinmc;		// 样品名称
	private String shuliang;		// 数量
	private String bianmaqz;		// 编码前缀
	private String chushibm;		// 初始编码
	private String liuyangsl;		// 留样数量
	private String liuyangqx;		// 留样期限
	private String shifouxysyszy;		// 是否需要试验室制样
	private String yangpinbz;		// 样品备注
	private String zhiyangsm;		// 制样说明
	private String weituodzj;		// 申请单主键（业务数据）
	private String fangfazj;		// 方法主键：为最初申请单下发时的方法关系主键，而非重新生成任务的方法主键
	private String chaiyangzj;		// 拆样主键
	private String shifoumy;		// 是否母样
	private String yangpinzt;		// 样品状态：待入库(样品组)，入库(样品组)，出库，在检，试验完成 , 返库(样品)
	private String rukusj;		// 入库时间：填写入库时更新
	private String cunfangwz;		// 存放位置
	private String qijianhcbzwzid;		// 期间核查标准物质ID(如果为期间核查标准物质，在样品库管理界面不显示)
	private String yangpinbh;		// 样品编号
	private String yangpinzh;		// 样品组号
	private String yangpinxh;		// 样品型号
	private String cunfangwzid;		// 存放位置id
	private String shifoubzwz;		// 是否标准物质 0：是 1：否
	private String songyangr;		// 送样人
	private String songyangrid;		//宋岩人id
	private String yewudh;          //业务单号(来自申请单)
	private String yangpinzid;      //样品组id
	private String yangpinxlh;      //样品编号序列号（样品组）
	private String chushibmxlh;     //初始编码序列号（样品组）
	private String shifouwtdypz;    //是否申请单样品组
	private Attachment[] yangpinzplist;
	private String yangpincz;    //样品材质----新增字段（20170808）

	//字典项begin
	private String jianhouypclfs;		// 检后样品处理方式
	private String shengyuypclfs;		// 剩余样品处理方式
	private String weixianxsm;		// 危险性说明
	private String baocunyq;		// 保存要求
	private String shifouly;		// 是否留样
	//字典项end
	private String yewudmc;     //业务单名称(来自申请单)
	private String yewufqr;//申请发起人
	private String yewufqrbm;    //
	private String yewufqrid;//
	private String chukuglrwid;//出库关联任务id
	private String yangpinrid;//领样/送样人
	private String yangpinr;//
	private String lingyangsysh;//入库，出库，返库数量
	private String lianxifs;
	private String yangpinid;
	private String faqirszbm;
	private String renwubh;//样品所属任务
	private String renwumc;//任务名称（该vo对应的数据库表中无此列）
	private Attachment[] shujulist;//数据文件
	private String type;// 0:临时样品库（存放位置为空） 1:样品库  2:出库单样品排序(如果是出库单样品 正序)
	private String faqidw; //发起人地址（单位地址）
	private String qiwangwcrq; //期望完成日期
	private String tongxundz;//通讯地址
	//////////样品标签打印///////////////////
	private String sno;	       //样品编号
	private String ino;		   //初始编码
	private String client;     //样品标签打印
	private String sn;		   //样品名称
	private String imagepath;  //二维码图片路径
	//////////样品标签打印///////////////////
	private String dsf;

	public String getTongxundz() {
		return tongxundz;
	}

	public void setTongxundz(String tongxundz) {
		this.tongxundz = tongxundz;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYewufqrbm() {
		return yewufqrbm;
	}

	public void setYewufqrbm(String yewufqrbm) {
		this.yewufqrbm = yewufqrbm;
	}

	public String getYangpinlbmc() {
		return yangpinlbmc;
	}

	public void setYangpinlbmc(String yangpinlbmc) {
		this.yangpinlbmc = yangpinlbmc;
	}

	public String getYewufqrid() {
		return yewufqrid;
	}

	public void setYewufqrid(String yewufqrid) {
		this.yewufqrid = yewufqrid;
	}

	public String getYewufqr() {
		return yewufqr;
	}

	public void setYewufqr(String yewufqr) {
		this.yewufqr = yewufqr;
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

	
	public Yangpin() {
		super();
	}

	public Yangpin(String id){
		super(id);
	}

	@Length(min=0, max=200, message="是否单个样品长度必须介于 0 和 200 之间")
	public String getShifoudgyp() {
		return shifoudgyp;
	}

	public void setShifoudgyp(String shifoudgyp) {
		this.shifoudgyp = shifoudgyp;
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
	
	@Length(min=0, max=200, message="样品状态：待入库(样品组)，入库(样品组)，出库，在检，试验完成 , 返库(样品)长度必须介于 0 和 200 之间")
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

	public String getYangpinzid() {
		return yangpinzid;
	}

	public void setYangpinzid(String yangpinzid) {
		this.yangpinzid = yangpinzid;
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

	public String getChukuglrwid() {
		return chukuglrwid;
	}

	public void setChukuglrwid(String chukuglrwid) {
		this.chukuglrwid = chukuglrwid;
	}

	/*public String getYangpinrid() {
		return yangpinrid;
	}

	public void setYangpinrid(String yangpinrid) {
		this.yangpinrid = yangpinrid;
	}*/

	public String getYangpinr() {
		return yangpinr;
	}

	public void setYangpinr(String yangpinr) {
		this.yangpinr = yangpinr;
	}

	public String getLingyangsysh() {
		return lingyangsysh;
	}

	public void setLingyangsysh(String lingyangsysh) {
		this.lingyangsysh = lingyangsysh;
	}

	public String getShifouwtdypz() {
		return shifouwtdypz;
	}

	public void setShifouwtdypz(String shifouwtdypz) {
		this.shifouwtdypz = shifouwtdypz;
	}

	public String getSongyangrid() {
		return songyangrid;
	}

	public void setSongyangrid(String songyangrid) {
		this.songyangrid = songyangrid;
	}

	public String getYangpinrid() {
		return yangpinrid;
	}

	public void setYangpinrid(String yangpinrid) {
		this.yangpinrid = yangpinrid;
	}

	public String getLianxifs() {
		return lianxifs;
	}

	public void setLianxifs(String lianxifs) {
		this.lianxifs = lianxifs;
	}

	public String getYangpinid() {
		return yangpinid;
	}

	public void setYangpinid(String yangpinid) {
		this.yangpinid = yangpinid;
	}

	public String getFaqirszbm() {
		return faqirszbm;
	}

	public Attachment[] getYangpinzplist() {
		return yangpinzplist;
	}

	public void setYangpinzplist(Attachment[] yangpinzplist) {
		this.yangpinzplist = yangpinzplist;
	}

	public void setFaqirszbm(String faqirszbm) {
		this.faqirszbm = faqirszbm;
	}

	public String getRenwubh() {
		return renwubh;
	}

	public void setRenwubh(String renwubh) {
		this.renwubh = renwubh;
	}

	public Attachment[] getShujulist() {
		return shujulist;
	}

	public void setShujulist(Attachment[] shujulist) {
		this.shujulist = shujulist;
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

	public String getWeixianxsm() {
		return weixianxsm;
	}

	public void setWeixianxsm(String weixianxsm) {
		this.weixianxsm = weixianxsm;
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

	public String getFaqidw() {
		return faqidw;
	}

	public void setFaqidw(String faqidw) {
		this.faqidw = faqidw;
	}

	public String getQiwangwcrq() {
		return qiwangwcrq;
	}

	public void setQiwangwcrq(String qiwangwcrq) {
		this.qiwangwcrq = qiwangwcrq;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getIno() {
		return ino;
	}

	public void setIno(String ino) {
		this.ino = ino;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getImagepath() {
		return imagepath;
	}

	public void setImagepath(String imagepath) {
		this.imagepath = imagepath;
	}

	public String getYangpincz() {
		return yangpincz;
	}

	public void setYangpincz(String yangpincz) {
		this.yangpincz = yangpincz;
	}

	public String getRenwumc() {
		return renwumc;
	}

	public void setRenwumc(String renwumc) {
		this.renwumc = renwumc;
	}
}