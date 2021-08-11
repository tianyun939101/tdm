package com.demxs.tdm.dao.resource.dto;

import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 设备信息Entity
 * @author zhangdengcai
 * @version 2017-06-14
 */
public class ShebeiDto {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String shebeibh;		// 设备编号
	private String shebeimc;		// 设备名称
	private String eipbianhao;		// EIP编号
	private String shebeixh;		// 设备型号
	private String chuchangbh;		// 出厂编号
	private String shengchancj;		// 生产厂家
	private String gongyings;		// 供应商
	private String gouzhirq;		// 购置日期
	private String touyongrq;		// 投用日期
	private String goumaijg;		// 购买价格
	private String shiyongdwid;		// 使用单位ID
	private String shiyongdw;		// 使用单位
	private String shebeissbmid;		// 设备所属部门ID
	private String shebeissbm;		// 设备所属部门
	private String cunfangdd;		// 存放地点
	private String cunfangddmc;		//存放地点名称
	private String shebeidyfzr;		// 设备第一负责人
	private String shebeidyfzrid;		// 设备第一负责人ID
	private String diyifzrlxfs;		// 第一负责人 联系方式
	private String shebeidefzr;		// 设备第二负责人
	private String shebeidefzrid;		// 设备第二负责人ID
	private String dierfzrlxfs;		// 第二负责人联系方式
	private String shebeifl;		// 设备分类
	private String shebeilxes;		//所属设备分类及其所有父级id
	private String shebeiflmc;		// 设备分类名称(数据库中无此列)
	private String yongtufl;		// 用途分类
	private String shebeijj;		// 设备简介
	private String shebeizt;		// 设备状态：空闲、占用、维修中、保养中、检定中、期间核查中、已报废。其中空闲和占用两种状态由系统根据该设备当前执行的任务状态进行判断，其他几种状态由设备管理员手动修改。新增时，此字段不显示在页面上，默认状态为空闲。
	private String jiliangfs;		// 计量方式
	private String jiliangzq;		// 计量周期
	private String shifouqjhc;		// 是否期间核查
	private String qijianhczq;		// 期间核查周期
	private String wendusx;		// 温度上限
	private String wenduxx;		// 温度下限
	private String shidusx;		// 湿度上限
	private String shiduxx;		// 湿度下限
	private String zhendongsx;		// 振动上限
	private String zhendongxx;		// 振动下限
	private String zaoshengsx;		// 噪声上限
	private String zaoshengxx;		// 噪声下限
	private String baoyangzq;		// 保养周期（天）
	private String shebeitp;		// 设备图片
	private String shifoukfyy;		// 是否开放预约：单选（是或否），如果选是，则在预约使用业务中，此设备可被选到。如果选择否，则在预约使用业务中不显示此设备。
	private String shebeizl;		// 设备资料
	private String shebeindjf;		// 设备年度积分:年底清零
	private String shebeigl;		// 设备功率（KW）
	private String shebeiaztj;		// 设备安装条件
	private String shebeily;		// 设备来源
	private String shebeizb;		// 设备组别
	private String shifouzwbmhxz;		// 是否在外部门户显示
	private String zizhiry;		// 资质人员
	private String guobie;		// 国别
	private String keshiysj;	//可使用时间
	private String bukesykssj;	//不可使用（开始）时间
	private String bukesyjssj;	//不可使用（结束）时间
	private List<Map<String, Object>> zizhiryInfo = new ArrayList<Map<String, Object>>();
	private String[] shebeifls;//设备分类id数组
	private String urlParam;//请求参数

	private Boolean submit;		//是否提交

	/*-------以下设备板块使用--------*/
	private String shebeibk;//设备板块字典值
	private String shebeifzr;//设备负责人
	private String cunfangwz;//存放位置
	private String dangqianrw;//当前任务
	private String jiancegcs;//试验工程师
	private String renwuzt;//任务状态
	private String shebeiid; //设备id

	private String isShowNull;//是否显示空节点 1是 0否
	private String canSelect;// 只有待验证 和 已审核的 方法允许选择

	public String getIsShowNull() {
		return isShowNull;
	}

	public void setIsShowNull(String isShowNull) {
		this.isShowNull = isShowNull;
	}

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

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

	public static String  getBankuaiType(){//板块字典type
		return "shebeikb";
	}
	/*-------以上设备板块使用--------*/


	public String getShebeifzr() {
		return shebeifzr;
	}

	public void setShebeifzr(String shebeifzr) {
		this.shebeifzr = shebeifzr;
	}

	public String getCunfangwz() {
		return cunfangwz;
	}

	public void setCunfangwz(String cunfangwz) {
		this.cunfangwz = cunfangwz;
	}

	public String getDangqianrw() {
		return dangqianrw;
	}

	public void setDangqianrw(String dangqianrw) {
		this.dangqianrw = dangqianrw;
	}

	@Length(min=0, max=200, message="设备编号长度必须介于 0 和 200 之间")
	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
	}
	
	@Length(min=0, max=200, message="设备名称长度必须介于 0 和 200 之间")
	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}
	
	@Length(min=0, max=200, message="EIP编号长度必须介于 0 和 200 之间")
	public String getEipbianhao() {
		return eipbianhao;
	}

	public void setEipbianhao(String eipbianhao) {
		this.eipbianhao = eipbianhao;
	}
	
	@Length(min=0, max=200, message="设备型号长度必须介于 0 和 200 之间")
	public String getShebeixh() {
		return shebeixh;
	}

	public void setShebeixh(String shebeixh) {
		this.shebeixh = shebeixh;
	}
	
	@Length(min=0, max=200, message="出厂编号长度必须介于 0 和 200 之间")
	public String getChuchangbh() {
		return chuchangbh;
	}

	public void setChuchangbh(String chuchangbh) {
		this.chuchangbh = chuchangbh;
	}
	
	@Length(min=0, max=200, message="生产厂家长度必须介于 0 和 200 之间")
	public String getShengchancj() {
		return shengchancj;
	}

	public void setShengchancj(String shengchancj) {
		this.shengchancj = shengchancj;
	}
	
	@Length(min=0, max=200, message="供应商长度必须介于 0 和 200 之间")
	public String getGongyings() {
		return gongyings;
	}

	public void setGongyings(String gongyings) {
		this.gongyings = gongyings;
	}
	
	@Length(min=0, max=200, message="购置日期长度必须介于 0 和 200 之间")
	public String getGouzhirq() {
		return gouzhirq;
	}

	public void setGouzhirq(String gouzhirq) {
		this.gouzhirq = gouzhirq;
	}
	
	@Length(min=0, max=200, message="投用日期长度必须介于 0 和 200 之间")
	public String getTouyongrq() {
		return touyongrq;
	}

	public void setTouyongrq(String touyongrq) {
		this.touyongrq = touyongrq;
	}
	
	@Length(min=0, max=200, message="购买价格长度必须介于 0 和 200 之间")
	public String getGoumaijg() {
		return goumaijg;
	}

	public void setGoumaijg(String goumaijg) {
		this.goumaijg = goumaijg;
	}
	
	@Length(min=0, max=200, message="使用单位ID长度必须介于 0 和 200 之间")
	public String getShiyongdwid() {
		return shiyongdwid;
	}

	public void setShiyongdwid(String shiyongdwid) {
		this.shiyongdwid = shiyongdwid;
	}
	
	@Length(min=0, max=200, message="使用单位长度必须介于 0 和 200 之间")
	public String getShiyongdw() {
		return shiyongdw;
	}

	public void setShiyongdw(String shiyongdw) {
		this.shiyongdw = shiyongdw;
	}
	
	@Length(min=0, max=200, message="设备所属部门ID长度必须介于 0 和 200 之间")
	public String getShebeissbmid() {
		return shebeissbmid;
	}

	public void setShebeissbmid(String shebeissbmid) {
		this.shebeissbmid = shebeissbmid;
	}
	
	@Length(min=0, max=200, message="设备所属部门长度必须介于 0 和 200 之间")
	public String getShebeissbm() {
		return shebeissbm;
	}

	public void setShebeissbm(String shebeissbm) {
		this.shebeissbm = shebeissbm;
	}
	
	@Length(min=0, max=200, message="存放地点长度必须介于 0 和 200 之间")
	public String getCunfangdd() {
		return cunfangdd;
	}

	public void setCunfangdd(String cunfangdd) {
		this.cunfangdd = cunfangdd;
	}

	public String getCunfangddmc() {
		return cunfangddmc;
	}

	public void setCunfangddmc(String cunfangddmc) {
		this.cunfangddmc = cunfangddmc;
	}
	
	@Length(min=0, max=200, message="设备第一负责人长度必须介于 0 和 200 之间")
	public String getShebeidyfzr() {
		return shebeidyfzr;
	}

	public void setShebeidyfzr(String shebeidyfzr) {
		this.shebeidyfzr = shebeidyfzr;
	}
	
	@Length(min=0, max=200, message="设备第一负责人ID长度必须介于 0 和 200 之间")
	public String getShebeidyfzrid() {
		return shebeidyfzrid;
	}

	public void setShebeidyfzrid(String shebeidyfzrid) {
		this.shebeidyfzrid = shebeidyfzrid;
	}
	
	@Length(min=0, max=200, message="第一负责人 联系方式长度必须介于 0 和 200 之间")
	public String getDiyifzrlxfs() {
		return diyifzrlxfs;
	}

	public void setDiyifzrlxfs(String diyifzrlxfs) {
		this.diyifzrlxfs = diyifzrlxfs;
	}
	
	@Length(min=0, max=200, message="设备第二负责人长度必须介于 0 和 200 之间")
	public String getShebeidefzr() {
		return shebeidefzr;
	}

	public void setShebeidefzr(String shebeidefzr) {
		this.shebeidefzr = shebeidefzr;
	}
	
	@Length(min=0, max=200, message="设备第二负责人ID长度必须介于 0 和 200 之间")
	public String getShebeidefzrid() {
		return shebeidefzrid;
	}

	public void setShebeidefzrid(String shebeidefzrid) {
		this.shebeidefzrid = shebeidefzrid;
	}
	
	@Length(min=0, max=200, message="第二负责人联系方式长度必须介于 0 和 200 之间")
	public String getDierfzrlxfs() {
		return dierfzrlxfs;
	}

	public void setDierfzrlxfs(String dierfzrlxfs) {
		this.dierfzrlxfs = dierfzrlxfs;
	}
	
	@Length(min=0, max=200, message="设备分类长度必须介于 0 和 200 之间")
	public String getShebeifl() {
		return shebeifl;
	}

	public void setShebeifl(String shebeifl) {
		this.shebeifl = shebeifl;
	}
	
	@Length(min=0, max=200, message="用途分类长度必须介于 0 和 200 之间")
	public String getYongtufl() {
		return yongtufl;
	}

	public void setYongtufl(String yongtufl) {
		this.yongtufl = yongtufl;
	}
	
	@Length(min=0, max=2000, message="设备简介长度必须介于 0 和 2000 之间")
	public String getShebeijj() {
		return shebeijj;
	}

	public void setShebeijj(String shebeijj) {
		this.shebeijj = shebeijj;
	}
	
	@Length(min=0, max=200, message="设备状态：空闲、占用、维修中、保养中、检定中、期间核查中、已报废。其中空闲和占用两种状态由系统根据该设备当前执行的任务状态进行判断，其他几种状态由设备管理员手动修改。新增时，此字段不显示在页面上，默认状态为空闲。长度必须介于 0 和 200 之间")
	public String getShebeizt() {
		return shebeizt;
	}

	public void setShebeizt(String shebeizt) {
		this.shebeizt = shebeizt;
	}
	
	@Length(min=0, max=200, message="计量方式长度必须介于 0 和 200 之间")
	public String getJiliangfs() {
		return jiliangfs;
	}

	public void setJiliangfs(String jiliangfs) {
		this.jiliangfs = jiliangfs;
	}
	
	@Length(min=0, max=200, message="计量周期长度必须介于 0 和 200 之间")
	public String getJiliangzq() {
		return jiliangzq;
	}

	public void setJiliangzq(String jiliangzq) {
		this.jiliangzq = jiliangzq;
	}
	
	@Length(min=0, max=200, message="是否期间核查长度必须介于 0 和 200 之间")
	public String getShifouqjhc() {
		return shifouqjhc;
	}

	public void setShifouqjhc(String shifouqjhc) {
		this.shifouqjhc = shifouqjhc;
	}
	
	@Length(min=0, max=200, message="期间核查周期长度必须介于 0 和 200 之间")
	public String getQijianhczq() {
		return qijianhczq;
	}

	public void setQijianhczq(String qijianhczq) {
		this.qijianhczq = qijianhczq;
	}
	
	@Length(min=0, max=200, message="温度上限长度必须介于 0 和 200 之间")
	public String getWendusx() {
		return wendusx;
	}

	public void setWendusx(String wendusx) {
		this.wendusx = wendusx;
	}
	
	@Length(min=0, max=200, message="温度下限长度必须介于 0 和 200 之间")
	public String getWenduxx() {
		return wenduxx;
	}

	public void setWenduxx(String wenduxx) {
		this.wenduxx = wenduxx;
	}
	
	@Length(min=0, max=200, message="湿度上限长度必须介于 0 和 200 之间")
	public String getShidusx() {
		return shidusx;
	}

	public void setShidusx(String shidusx) {
		this.shidusx = shidusx;
	}
	
	@Length(min=0, max=200, message="湿度下限长度必须介于 0 和 200 之间")
	public String getShiduxx() {
		return shiduxx;
	}

	public void setShiduxx(String shiduxx) {
		this.shiduxx = shiduxx;
	}
	
	@Length(min=0, max=200, message="振动上限长度必须介于 0 和 200 之间")
	public String getZhendongsx() {
		return zhendongsx;
	}

	public void setZhendongsx(String zhendongsx) {
		this.zhendongsx = zhendongsx;
	}
	
	@Length(min=0, max=200, message="振动下限长度必须介于 0 和 200 之间")
	public String getZhendongxx() {
		return zhendongxx;
	}

	public void setZhendongxx(String zhendongxx) {
		this.zhendongxx = zhendongxx;
	}
	
	@Length(min=0, max=200, message="噪声上限长度必须介于 0 和 200 之间")
	public String getZaoshengsx() {
		return zaoshengsx;
	}

	public void setZaoshengsx(String zaoshengsx) {
		this.zaoshengsx = zaoshengsx;
	}
	
	@Length(min=0, max=200, message="噪声下限长度必须介于 0 和 200 之间")
	public String getZaoshengxx() {
		return zaoshengxx;
	}

	public void setZaoshengxx(String zaoshengxx) {
		this.zaoshengxx = zaoshengxx;
	}
	
	@Length(min=0, max=200, message="保养周期（天）长度必须介于 0 和 200 之间")
	public String getBaoyangzq() {
		return baoyangzq;
	}

	public void setBaoyangzq(String baoyangzq) {
		this.baoyangzq = baoyangzq;
	}
	
	public String getShebeitp() {
		return shebeitp;
	}

	public void setShebeitp(String shebeitp) {
		this.shebeitp = shebeitp;
	}
	
	@Length(min=0, max=200, message="是否开放预约：单选（是或否），如果选是，则在预约使用业务中，此设备可被选到。如果选择否，则在预约使用业务中不显示此设备。长度必须介于 0 和 200 之间")
	public String getShifoukfyy() {
		return shifoukfyy;
	}

	public void setShifoukfyy(String shifoukfyy) {
		this.shifoukfyy = shifoukfyy;
	}
	
	public String getShebeizl() {
		return shebeizl;
	}

	public void setShebeizl(String shebeizl) {
		this.shebeizl = shebeizl;
	}
	
	@Length(min=0, max=200, message="设备年度积分:年底清零长度必须介于 0 和 200 之间")
	public String getShebeindjf() {
		return shebeindjf;
	}

	public void setShebeindjf(String shebeindjf) {
		this.shebeindjf = shebeindjf;
	}
	
	@Length(min=0, max=200, message="设备功率（KW）长度必须介于 0 和 200 之间")
	public String getShebeigl() {
		return shebeigl;
	}

	public void setShebeigl(String shebeigl) {
		this.shebeigl = shebeigl;
	}
	
	@Length(min=0, max=2000, message="设备安装条件长度必须介于 0 和 2000 之间")
	public String getShebeiaztj() {
		return shebeiaztj;
	}

	public void setShebeiaztj(String shebeiaztj) {
		this.shebeiaztj = shebeiaztj;
	}
	
	@Length(min=0, max=200, message="设备来源长度必须介于 0 和 200 之间")
	public String getShebeily() {
		return shebeily;
	}

	public void setShebeily(String shebeily) {
		this.shebeily = shebeily;
	}
	
	@Length(min=0, max=200, message="设备组别长度必须介于 0 和 200 之间")
	public String getShebeizb() {
		return shebeizb;
	}

	public void setShebeizb(String shebeizb) {
		this.shebeizb = shebeizb;
	}
	
	@Length(min=0, max=2, message="是否在外部门户显示长度必须介于 0 和 2 之间")
	public String getShifouzwbmhxz() {
		return shifouzwbmhxz;
	}

	public void setShifouzwbmhxz(String shifouzwbmhxz) {
		this.shifouzwbmhxz = shifouzwbmhxz;
	}
	
	@Length(min=0, max=300, message="资质人员长度必须介于 0 和 300 之间")
	public String getZizhiry() {
		return zizhiry;
	}

	public void setZizhiry(String zizhiry) {
		this.zizhiry = zizhiry;
	}
	
	@Length(min=0, max=100, message="国别长度必须介于 0 和 100 之间")
	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}

	public String getShebeiflmc() {
		return shebeiflmc;
	}

	public void setShebeiflmc(String shebeiflmc) {
		this.shebeiflmc = shebeiflmc;
	}

	public List<Map<String, Object>> getZizhiryInfo() {
		return zizhiryInfo;
	}

	public void setZizhiryInfo(List<Map<String, Object>> zizhiryInfo) {
		this.zizhiryInfo = zizhiryInfo;
	}

	public String[] getShebeifls() {
		return shebeifls;
	}

	public void setShebeifls(String[] shebeifls) {
		this.shebeifls = shebeifls;
	}

	public String getShebeibk() {
		return shebeibk;
	}

	public void setShebeibk(String shebeibk) {
		this.shebeibk = shebeibk;
	}


	public String getRenwuzt() {
		return renwuzt;
	}

	public void setRenwuzt(String renwuzt) {
		this.renwuzt = renwuzt;
	}

	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}


	public String getJiancegcs() {
		return jiancegcs;
	}

	public void setJiancegcs(String jiancegcs) {
		this.jiancegcs = jiancegcs;
	}

	public String getKeshiysj() {
		return keshiysj;
	}

	public void setKeshiysj(String keshiysj) {
		this.keshiysj = keshiysj;
	}

	public String getShebeilxes() {
		return shebeilxes;
	}

	public void setShebeilxes(String shebeilxes) {
		this.shebeilxes = shebeilxes;
	}

	public String getBukesykssj() {
		return bukesykssj;
	}

	public void setBukesykssj(String bukesykssj) {
		this.bukesykssj = bukesykssj;
	}

	public String getBukesyjssj() {
		return bukesyjssj;
	}

	public void setBukesyjssj(String bukesyjssj) {
		this.bukesyjssj = bukesyjssj;
	}

	public String getUrlParam() {
		return urlParam;
	}

	public void setUrlParam(String urlParam) {
		this.urlParam = urlParam;
	}

	public Boolean getSubmit() {
		return submit;
	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}