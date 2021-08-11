package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备保养计划Entity
 * @author zhangdengcai
 * @version 2017-07-15
 */
public class ShebeiByjh extends DataEntity<ShebeiByjh> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String baoyangyq;		// 保养要求
	private String jihuar;		// 计划人
	private String jihuarid;		// 计划人ID
	private String baoyangcs;		// 保养措施
	private String baoyangtxts;		// 保养提醒（天）
	private List<ShebeiByjl> baoyangjl = new ArrayList<ShebeiByjl>();

	//以下字段为仅用于装载和传输数据信息，数据库中并无该字段
	private String shebeimc;	//设备名称
	private String shebeibh;	//设备编号



	private String dateRange;//入库时间范围 页面传值
	private Date startDate;//入库开始时间
	private Date endDate;//入库结束时间
	
	public ShebeiByjh() {
		super();
	}

	public ShebeiByjh(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	
	@Length(min=0, max=2000, message="保养要求长度必须介于 0 和 2000 之间")
	public String getBaoyangyq() {
		return baoyangyq;
	}

	public void setBaoyangyq(String baoyangyq) {
		this.baoyangyq = baoyangyq;
	}
	
	@Length(min=0, max=200, message="计划人长度必须介于 0 和 200 之间")
	public String getJihuar() {
		return jihuar;
	}

	public void setJihuar(String jihuar) {
		this.jihuar = jihuar;
	}
	
	@Length(min=0, max=200, message="计划人ID长度必须介于 0 和 200 之间")
	public String getJihuarid() {
		return jihuarid;
	}

	public void setJihuarid(String jihuarid) {
		this.jihuarid = jihuarid;
	}
	
	@Length(min=0, max=2000, message="保养措施长度必须介于 0 和 2000 之间")
	public String getBaoyangcs() {
		return baoyangcs;
	}

	public void setBaoyangcs(String baoyangcs) {
		this.baoyangcs = baoyangcs;
	}
	
	@Length(min=0, max=200, message="保养提醒（天）长度必须介于 0 和 200 之间")
	public String getBaoyangtxts() {
		return baoyangtxts;
	}

	public void setBaoyangtxts(String baoyangtxts) {
		this.baoyangtxts = baoyangtxts;
	}

	public List<ShebeiByjl> getBaoyangjl() {
		return baoyangjl;
	}

	public void setBaoyangjl(List<ShebeiByjl> baoyangjl) {
		this.baoyangjl = baoyangjl;
	}

	public String getShebeimc() {
		return shebeimc;
	}

	public void setShebeimc(String shebeimc) {
		this.shebeimc = shebeimc;
	}

	public String getShebeibh() {
		return shebeibh;
	}

	public void setShebeibh(String shebeibh) {
		this.shebeibh = shebeibh;
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