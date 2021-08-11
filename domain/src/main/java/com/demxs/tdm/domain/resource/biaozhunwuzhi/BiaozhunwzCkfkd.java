package com.demxs.tdm.domain.resource.biaozhunwuzhi;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 标准物质出库、返库Entity
 * @author zhangdengcai
 * @version 2017-06-17
 */
public class BiaozhunwzCkfkd extends DataEntity<BiaozhunwzCkfkd> {
	
	private static final long serialVersionUID = 1L;
	private String biaozhunzj;		// 标准主键
	private String biaozhunlx;		// 样品流向：出库；返库
	private String biaozhunr;		// 领取人/归还人
	private String chukufkrq;		// 出库日期/返库日期
	private String dengjirid;		// 登记人ID
	private String dengjir;		// 登记人
	private String leixing;		// 类型: 返库，出库
	private String chukuglrwid;		// 出库关联任务ID
	private String yongtu;		// 试验、设备核查、标准物质核查、自行处理
	private String cunfangwzid;		// 存放位置ID（返库）
	private String[] ids;//主键数组
	
	public BiaozhunwzCkfkd() {
		super();
	}

	public BiaozhunwzCkfkd(String id){
		super(id);
	}

	@Length(min=0, max=200, message="标准主键长度必须介于 0 和 200 之间")
	public String getBiaozhunzj() {
		return biaozhunzj;
	}

	public void setBiaozhunzj(String biaozhunzj) {
		this.biaozhunzj = biaozhunzj;
	}
	
	@Length(min=0, max=200, message="样品流向：出库；返库长度必须介于 0 和 200 之间")
	public String getBiaozhunlx() {
		return biaozhunlx;
	}

	public void setBiaozhunlx(String biaozhunlx) {
		this.biaozhunlx = biaozhunlx;
	}
	
	@Length(min=0, max=200, message="领取人/归还人长度必须介于 0 和 200 之间")
	public String getBiaozhunr() {
		return biaozhunr;
	}

	public void setBiaozhunr(String biaozhunr) {
		this.biaozhunr = biaozhunr;
	}
	
	@Length(min=0, max=200, message="出库日期/返库日期长度必须介于 0 和 200 之间")
	public String getChukufkrq() {
		return chukufkrq;
	}

	public void setChukufkrq(String chukufkrq) {
		this.chukufkrq = chukufkrq;
	}
	
	@Length(min=0, max=200, message="登记人ID长度必须介于 0 和 200 之间")
	public String getDengjirid() {
		return dengjirid;
	}

	public void setDengjirid(String dengjirid) {
		this.dengjirid = dengjirid;
	}
	
	@Length(min=0, max=200, message="登记人长度必须介于 0 和 200 之间")
	public String getDengjir() {
		return dengjir;
	}

	public void setDengjir(String dengjir) {
		this.dengjir = dengjir;
	}
	
	@Length(min=0, max=200, message="类型: 返库，出库长度必须介于 0 和 200 之间")
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
	
	@Length(min=0, max=200, message="试验、设备核查、标准物质核查、自行处理长度必须介于 0 和 200 之间")
	public String getYongtu() {
		return yongtu;
	}

	public void setYongtu(String yongtu) {
		this.yongtu = yongtu;
	}
	
	@Length(min=0, max=200, message="存放位置ID（返库）长度必须介于 0 和 200 之间")
	public String getCunfangwzid() {
		return cunfangwzid;
	}

	public void setCunfangwzid(String cunfangwzid) {
		this.cunfangwzid = cunfangwzid;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
}