package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 设备维修记录Entity
 * @author zhangdengcai
 * @version 2017-07-14
 */
public class ShebeiWxjl extends DataEntity<ShebeiWxjl> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String guzhangms;		// 故障描述
	private String guzhangyy;		// 故障原因
	private String guzhangfxrq;		// 故障发现日期
	private String weixiudw;		// 维修单位
	private String weixiur;		// 维修人
	private String weixiuksrq;		// 维修开始日期
	private String weixiujsrq;		// 维修结束日期
	private String weixiuff;		// 维修方法
	private String weixiuhzt;		// 维修后状态
	private String weixiuzl;		// 维修资料
	private String dengjirid;		// 登记人ID
	private String dengjir;		// 登记人
	private String dengjirq;		// 登记日期


	private String guzhangsc;//故障时长
	private String weixiufy;//维修费用
	private String status;//状态 0待提交 1待审核  2已审核

	private Boolean submit;		  //是否提交
	private Integer type;//0提交 1保存

	private String from;//laiyuan

	private String yijian;


	private String dateRange;//入库时间范围 页面传值
	private Date startDate;//入库开始时间
	private Date endDate;//入库结束时间

	public ShebeiWxjl() {
		super();
	}

	public ShebeiWxjl(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=3000, message="故障描述长度必须介于 0 和 3000 之间")
	public String getGuzhangms() {
		return guzhangms;
	}

	public void setGuzhangms(String guzhangms) {
		this.guzhangms = guzhangms;
	}
	
	@Length(min=0, max=3000, message="故障原因长度必须介于 0 和 3000 之间")
	public String getGuzhangyy() {
		return guzhangyy;
	}

	public void setGuzhangyy(String guzhangyy) {
		this.guzhangyy = guzhangyy;
	}
	
	@Length(min=0, max=200, message="故障发现日期长度必须介于 0 和 200 之间")
	public String getGuzhangfxrq() {
		return guzhangfxrq;
	}

	public void setGuzhangfxrq(String guzhangfxrq) {
		this.guzhangfxrq = guzhangfxrq;
	}
	
	@Length(min=0, max=200, message="维修单位长度必须介于 0 和 200 之间")
	public String getWeixiudw() {
		return weixiudw;
	}

	public void setWeixiudw(String weixiudw) {
		this.weixiudw = weixiudw;
	}
	
	@Length(min=0, max=200, message="维修人长度必须介于 0 和 200 之间")
	public String getWeixiur() {
		return weixiur;
	}

	public void setWeixiur(String weixiur) {
		this.weixiur = weixiur;
	}
	
	@Length(min=0, max=200, message="维修开始日期长度必须介于 0 和 200 之间")
	public String getWeixiuksrq() {
		return weixiuksrq;
	}

	public void setWeixiuksrq(String weixiuksrq) {
		this.weixiuksrq = weixiuksrq;
	}
	
	@Length(min=0, max=200, message="维修结束日期长度必须介于 0 和 200 之间")
	public String getWeixiujsrq() {
		return weixiujsrq;
	}

	public void setWeixiujsrq(String weixiujsrq) {
		this.weixiujsrq = weixiujsrq;
	}
	
	@Length(min=0, max=200, message="维修方法长度必须介于 0 和 200 之间")
	public String getWeixiuff() {
		return weixiuff;
	}

	public void setWeixiuff(String weixiuff) {
		this.weixiuff = weixiuff;
	}
	
	@Length(min=0, max=200, message="维修后状态长度必须介于 0 和 200 之间")
	public String getWeixiuhzt() {
		return weixiuhzt;
	}

	public void setWeixiuhzt(String weixiuhzt) {
		this.weixiuhzt = weixiuhzt;
	}
	
	@Length(min=0, max=200, message="维修资料长度必须介于 0 和 200 之间")
	public String getWeixiuzl() {
		return weixiuzl;
	}

	public void setWeixiuzl(String weixiuzl) {
		this.weixiuzl = weixiuzl;
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
	
	@Length(min=0, max=200, message="登记日期长度必须介于 0 和 200 之间")
	public String getDengjirq() {
		return dengjirq;
	}

	public void setDengjirq(String dengjirq) {
		this.dengjirq = dengjirq;
	}

	public String getWeixiufy() {
		return weixiufy;
	}

	public void setWeixiufy(String weixiufy) {
		this.weixiufy = weixiufy;
	}
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Boolean getSubmit() {
		if(type==null){
			return null;
		}else{
			return type==0?true:false;
		}

	}

	public void setSubmit(Boolean submit) {
		this.submit = submit;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getGuzhangsc() {
		return guzhangsc;
	}

	public void setGuzhangsc(String guzhangsc) {
		this.guzhangsc = guzhangsc;
	}

	public String getYijian() {
		return yijian;
	}

	public void setYijian(String yijian) {
		this.yijian = yijian;
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

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
}