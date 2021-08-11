package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.comac.common.constant.BiaozhunwzConstans;
import org.hibernate.validator.constraints.Length;

/**
 * 标准物质Entity
 * @author zhangdengcai
 * @version 2017-06-16
 */
public class Biaozhunwz extends DataEntity<Biaozhunwz> {
	
	private static final long serialVersionUID = 1L;
	private String biaozhunwzbh;		// 标准物质编号
	private String biaozhunwzmc;		// 标准物质名称
	private String biaozhunwzxh;		// 标准物质型号
	private String zhengshuh;		// 证书号
	private String eipbh;		// EIP编号
	private String changjia;		// 厂家
	private String guobie;		// 国别
	private String gongyings;		// 供应商
	private String cunchuweiz;		// 存储位置
	private String jiliangdw;		// 计量单位
	private String youxiaoqz;		// 有效期至
	private String qijianhczq;		// 期间核查周期(t天)
	private String shifouxh;		// 是否消耗
	private String biaozhunwzzt;		// 标准物质状态：1空闲、2使用中、3已过期、4已消耗、5已处理
	private String dengjir;		// 登记人
	private String dengjirq;		// 登记日期
	private String shiyongsm;		// 使用说明
	private String ziliao;		// 资料
	private String anquankcsl;		// 安全库存数量：用户在文本框中输入，当库存数量少于该数量时，系统给出警告信息。
	private String biaozhunwzlbid;		// 标准物质类别ID
	private String shifouxyqjhc;		// 是否需要期间核查
	private String duiyingsb;		// 对应设备
	private String duiyingsbid;		// 对应设备id
	private String yanshour;		// 验收人
	private String yongjiuyx;		// 永久有效
	private String bianma;		// 编码
	private String cunchuweizmc;		//存储位置完整名称
	private String urlParam;		//请求参数

	private Boolean submit;		//是否提交
	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限
	private String kongxian = BiaozhunwzConstans.biaoZhunwzzt.KONGXIAN;//空闲状态
	private String yiguoq = BiaozhunwzConstans.biaoZhunwzzt.YIGUOQ;//已过期状态
	private String shiyongz = BiaozhunwzConstans.biaoZhunwzzt.SHIYONGZ;//使用中
	private String yixiaoh = BiaozhunwzConstans.biaoZhunwzzt.YIXIAOH;//已消耗

	public Biaozhunwz() {
		super();
	}

	public Biaozhunwz(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标准物质编号长度必须介于 0 和 200 之间")
	public String getBiaozhunwzbh() {
		return biaozhunwzbh;
	}

	public void setBiaozhunwzbh(String biaozhunwzbh) {
		this.biaozhunwzbh = biaozhunwzbh;
	}
	
	@Length(min=0, max=200, message="标准物质名称长度必须介于 0 和 200 之间")
	public String getBiaozhunwzmc() {
		return biaozhunwzmc;
	}

	public void setBiaozhunwzmc(String biaozhunwzmc) {
		this.biaozhunwzmc = biaozhunwzmc;
	}
	
	@Length(min=0, max=200, message="标准物质型号长度必须介于 0 和 200 之间")
	public String getBiaozhunwzxh() {
		return biaozhunwzxh;
	}

	public void setBiaozhunwzxh(String biaozhunwzxh) {
		this.biaozhunwzxh = biaozhunwzxh;
	}
	
	@Length(min=0, max=200, message="证书号长度必须介于 0 和 200 之间")
	public String getZhengshuh() {
		return zhengshuh;
	}

	public void setZhengshuh(String zhengshuh) {
		this.zhengshuh = zhengshuh;
	}
	
	@Length(min=0, max=200, message="EIP编号长度必须介于 0 和 200 之间")
	public String getEipbh() {
		return eipbh;
	}

	public void setEipbh(String eipbh) {
		this.eipbh = eipbh;
	}
	
	@Length(min=0, max=200, message="厂家长度必须介于 0 和 200 之间")
	public String getChangjia() {
		return changjia;
	}

	public void setChangjia(String changjia) {
		this.changjia = changjia;
	}
	
	@Length(min=0, max=200, message="国别长度必须介于 0 和 200 之间")
	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}
	
	@Length(min=0, max=200, message="供应商长度必须介于 0 和 200 之间")
	public String getGongyings() {
		return gongyings;
	}

	public void setGongyings(String gongyings) {
		this.gongyings = gongyings;
	}
	
	@Length(min=0, max=200, message="存储位置长度必须介于 0 和 200 之间")
	public String getCunchuweiz() {
		return cunchuweiz;
	}

	public void setCunchuweiz(String cunchuweiz) {
		this.cunchuweiz = cunchuweiz;
	}
	
	@Length(min=0, max=200, message="计量单位长度必须介于 0 和 200 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}
	
	@Length(min=0, max=200, message="有效期至长度必须介于 0 和 200 之间")
	public String getYouxiaoqz() {
		return youxiaoqz;
	}

	public void setYouxiaoqz(String youxiaoqz) {
		this.youxiaoqz = youxiaoqz;
	}
	
	@Length(min=0, max=200, message="期间核查周期(t天)长度必须介于 0 和 200 之间")
	public String getQijianhczq() {
		return qijianhczq;
	}

	public void setQijianhczq(String qijianhczq) {
		this.qijianhczq = qijianhczq;
	}
	
	@Length(min=0, max=200, message="是否消耗长度必须介于 0 和 200 之间")
	public String getShifouxh() {
		return shifouxh;
	}

	public void setShifouxh(String shifouxh) {
		this.shifouxh = shifouxh;
	}
	
	@Length(min=0, max=200, message="标准物质状态：1空闲、2使用中、3已过期、4已消耗、5已处理长度必须介于 0 和 200 之间")
	public String getBiaozhunwzzt() {
		return biaozhunwzzt;
	}

	public void setBiaozhunwzzt(String biaozhunwzzt) {
		this.biaozhunwzzt = biaozhunwzzt;
	}
	
	@Length(min=0, max=200, message="登记人长度必须介于 0 和 200 之间")
	public String getDengjir() {
		return dengjir;
	}

	public void setDengjir(String dengjir) {
		this.dengjir = dengjir;
	}
	
	@Length(min=0, max=200, message="登记日期长度必须介于 0 和 200 之间")
	public String getDengjirq() {
		return dengjirq;
	}

	public void setDengjirq(String dengjirq) {
		this.dengjirq = dengjirq;
	}
	
	@Length(min=0, max=2000, message="使用说明长度必须介于 0 和 2000 之间")
	public String getShiyongsm() {
		return shiyongsm;
	}

	public void setShiyongsm(String shiyongsm) {
		this.shiyongsm = shiyongsm;
	}
	
	public String getZiliao() {
		return ziliao;
	}

	public void setZiliao(String ziliao) {
		this.ziliao = ziliao;
	}
	
	@Length(min=0, max=200, message="安全库存数量：用户在文本框中输入，当库存数量少于该数量时，系统给出警告信息。长度必须介于 0 和 200 之间")
	public String getAnquankcsl() {
		return anquankcsl;
	}

	public void setAnquankcsl(String anquankcsl) {
		this.anquankcsl = anquankcsl;
	}
	
	@Length(min=0, max=200, message="标准物质类别ID长度必须介于 0 和 200 之间")
	public String getBiaozhunwzlbid() {
		return biaozhunwzlbid;
	}

	public void setBiaozhunwzlbid(String biaozhunwzlbid) {
		this.biaozhunwzlbid = biaozhunwzlbid;
	}
	
	@Length(min=0, max=200, message="是否需要期间核查长度必须介于 0 和 200 之间")
	public String getShifouxyqjhc() {
		return shifouxyqjhc;
	}

	public void setShifouxyqjhc(String shifouxyqjhc) {
		this.shifouxyqjhc = shifouxyqjhc;
	}
	
	@Length(min=0, max=200, message="对应设备长度必须介于 0 和 200 之间")
	public String getDuiyingsb() {
		return duiyingsb;
	}

	public void setDuiyingsb(String duiyingsb) {
		this.duiyingsb = duiyingsb;
	}
	
	@Length(min=0, max=200, message="对应设备id长度必须介于 0 和 200 之间")
	public String getDuiyingsbid() {
		return duiyingsbid;
	}

	public void setDuiyingsbid(String duiyingsbid) {
		this.duiyingsbid = duiyingsbid;
	}
	
	@Length(min=0, max=200, message="验收人长度必须介于 0 和 200 之间")
	public String getYanshour() {
		return yanshour;
	}

	public void setYanshour(String yanshour) {
		this.yanshour = yanshour;
	}
	
	@Length(min=0, max=2, message="永久有效长度必须介于 0 和 2 之间")
	public String getYongjiuyx() {
		return yongjiuyx;
	}

	public void setYongjiuyx(String yongjiuyx) {
		this.yongjiuyx = yongjiuyx;
	}
	
	@Length(min=0, max=200, message="编号长度必须介于 0 和 200 之间")
	public String getBianma() {
		return bianma;
	}

	public void setBianma(String bianma) {
		this.bianma = bianma;
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

	public String getCunchuweizmc() {
		return cunchuweizmc;
	}

	public void setCunchuweizmc(String cunchuweizmc) {
		this.cunchuweizmc = cunchuweizmc;
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

	public String getKongxian() {
		return kongxian;
	}

	public void setKongxian(String kongxian) {
		this.kongxian = kongxian;
	}

	public String getYiguoq() {
		return yiguoq;
	}

	public void setYiguoq(String yiguoq) {
		this.yiguoq = yiguoq;
	}

	public String getShiyongz() {
		return shiyongz;
	}

	public void setShiyongz(String shiyongz) {
		this.shiyongz = shiyongz;
	}

	public String getYixiaoh() {
		return yixiaoh;
	}

	public void setYixiaoh(String yixiaoh) {
		this.yixiaoh = yixiaoh;
	}
}