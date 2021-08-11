package com.demxs.tdm.domain.resource.haocai;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.domain.resource.consumeables.HaocaiCfwz;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import org.hibernate.validator.constraints.Length;

/**
 * 耗材入库信息Entity
 * @author zhangdengcai
 * @version 2017-07-16
 */
public class HaocaiRkxx extends DataEntity<HaocaiRkxx> {
	
	private static final long serialVersionUID = 1L;
	private String haocaibh;		// 耗材编号(批)
	private String haocaimc;		// 耗材名称
	private String guigexh;		// 规格型号
	private String eipbianhao;		// EIP编号
	private Changshanggysxx haocaicj;		// 耗材厂家
	private Changshanggysxx gongyings;		// 供应商
	private HaocaiLx haocailx;		// 耗材类别
	private String yongtu;		// 用途
	private String rukusl;		// 入库数量
	private String anquankcsl;		// 安全库存数量
	private String jiliangdw;		// 计量单位
	private String rukurq;		// 入库日期
	private HaocaiCfwz cunfangwz;		// 存放位置
	private String danjia;		// 单价
	private String zonge;		// 总额
	private String youxiaoqz;		// 有效期至
	private String yangshoujl;		// 验收结论

	private String yongjiux;		// 永久性
	private String guobie;		// 国别
	private User yanshour;		// 验收人
	private String haocaiyszl;		//验收资料

	private String shujuzt;			//数据状态
	private String peicihao;		//批次号

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

	public HaocaiRkxx() {
		super();
	}

	public HaocaiRkxx(String id){
		super(id);
	}

	@Length(min=0, max=200, message="耗材编号(批)长度必须介于 0 和 200 之间")
	public String getHaocaibh() {
		return haocaibh;
	}

	public void setHaocaibh(String haocaibh) {
		this.haocaibh = haocaibh;
	}
	
	@Length(min=0, max=200, message="耗材名称长度必须介于 0 和 200 之间")
	public String getHaocaimc() {
		return haocaimc;
	}

	public void setHaocaimc(String haocaimc) {
		this.haocaimc = haocaimc;
	}
	
	@Length(min=0, max=200, message="规格型号长度必须介于 0 和 200 之间")
	public String getGuigexh() {
		return guigexh;
	}

	public void setGuigexh(String guigexh) {
		this.guigexh = guigexh;
	}
	
	@Length(min=0, max=200, message="EIP编号长度必须介于 0 和 200 之间")
	public String getEipbianhao() {
		return eipbianhao;
	}

	public void setEipbianhao(String eipbianhao) {
		this.eipbianhao = eipbianhao;
	}

	public Changshanggysxx getHaocaicj() {
		return haocaicj;
	}

	public void setHaocaicj(Changshanggysxx haocaicj) {
		this.haocaicj = haocaicj;
	}

	@Length(min=0, max=200, message="用途长度必须介于 0 和 200 之间")
	public String getYongtu() {
		return yongtu;
	}

	public void setYongtu(String yongtu) {
		this.yongtu = yongtu;
	}
	
	@Length(min=0, max=200, message="入库数量长度必须介于 0 和 200 之间")
	public String getRukusl() {
		return rukusl;
	}

	public void setRukusl(String rukusl) {
		this.rukusl = rukusl;
	}
	
	@Length(min=0, max=200, message="安全库存数量长度必须介于 0 和 200 之间")
	public String getAnquankcsl() {
		return anquankcsl;
	}

	public void setAnquankcsl(String anquankcsl) {
		this.anquankcsl = anquankcsl;
	}
	
	@Length(min=0, max=200, message="计量单位长度必须介于 0 和 200 之间")
	public String getJiliangdw() {
		return jiliangdw;
	}

	public void setJiliangdw(String jiliangdw) {
		this.jiliangdw = jiliangdw;
	}
	
	@Length(min=0, max=200, message="入库日期长度必须介于 0 和 200 之间")
	public String getRukurq() {
		return rukurq;
	}

	public void setRukurq(String rukurq) {
		this.rukurq = rukurq;
	}

	@Length(min=0, max=200, message="单价长度必须介于 0 和 200 之间")
	public String getDanjia() {
		return danjia;
	}

	public void setDanjia(String danjia) {
		this.danjia = danjia;
	}
	
	@Length(min=0, max=200, message="总额长度必须介于 0 和 200 之间")
	public String getZonge() {
		return zonge;
	}

	public void setZonge(String zonge) {
		this.zonge = zonge;
	}
	
	@Length(min=0, max=200, message="有效期至长度必须介于 0 和 200 之间")
	public String getYouxiaoqz() {
		return youxiaoqz;
	}

	public void setYouxiaoqz(String youxiaoqz) {
		this.youxiaoqz = youxiaoqz;
	}
	
	@Length(min=0, max=200, message="验收结论长度必须介于 0 和 200 之间")
	public String getYangshoujl() {
		return yangshoujl;
	}

	public void setYangshoujl(String yangshoujl) {
		this.yangshoujl = yangshoujl;
	}

	@Length(min=0, max=2, message="永久性长度必须介于 0 和 2 之间")
	public String getYongjiux() {
		return yongjiux;
	}

	public void setYongjiux(String yongjiux) {
		this.yongjiux = yongjiux;
	}
	
	@Length(min=0, max=2, message="永久性长度必须介于 0 和 2 之间")
	public String getGuobie() {
		return guobie;
	}

	public void setGuobie(String guobie) {
		this.guobie = guobie;
	}

	public String getHaocaiyszl() {
		return haocaiyszl;
	}

	public void setHaocaiyszl(String haocaiyszl) {
		this.haocaiyszl = haocaiyszl;
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
	public String getShujuzt() {
		return shujuzt;
	}

	public void setShujuzt(String shujuzt) {
		this.shujuzt = shujuzt;
	}


	public String getPeicihao() {
		return peicihao;
	}

	public void setPeicihao(String peicihao) {
		this.peicihao = peicihao;
	}

	public Changshanggysxx getGongyings() {
		return gongyings;
	}

	public void setGongyings(Changshanggysxx gongyings) {
		this.gongyings = gongyings;
	}

	public HaocaiLx getHaocailx() {
		return haocailx;
	}

	public void setHaocailx(HaocaiLx haocailx) {
		this.haocailx = haocailx;
	}

	public HaocaiCfwz getCunfangwz() {
		return cunfangwz;
	}

	public void setCunfangwz(HaocaiCfwz cunfangwz) {
		this.cunfangwz = cunfangwz;
	}

	public User getYanshour() {
		return yanshour;
	}

	public void setYanshour(User yanshour) {
		this.yanshour = yanshour;
	}
}