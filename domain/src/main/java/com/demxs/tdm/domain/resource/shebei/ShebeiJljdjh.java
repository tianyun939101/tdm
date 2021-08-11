package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 设备计量检定计划Entity
 * @author zhangdengcai
 * @version 2017-07-13
 */
public class ShebeiJljdjh extends DataEntity<ShebeiJljdjh> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;		// 设备ID
	private String jiliangjdzq;		// 计量检定周期(天)
	private String jiliangjddw;		// 计量检定单位
	private User jiliangjdlxr;		// 计量检定联系人
	private String jiliangjdlxfs;		// 计量检定联系方式
	private String jiliangjdnr;		// 计量检定内容
	private String jihuar;		// 计划人
	private String jihuarid;		// 计划人ID
	private String jihuakssj;		//计划开始时间
	private String jihuajssj;		//计划结束时间
	private String jiliangfs;		//计量方式
	private String jihuajljdrq;		// 如果设备没有计量检定记录，则计划计量检定日期默认带出登记入库的日期+计量检定周期；计划计量检定日期：如果该设备有计量检定记录，则计划计量检定日期为上次计量检定日期+计量检定周期；计划计量检定日期可手动修改。
	private String jiliangjdtxts;		// 计量检定提醒天数
	private String jilinagjdjhzt;		// 计量检定计划状态：新增时，系统默认为&ldquo;未执行&rdquo;，生成计量检定执行任务后，系统自动更新成&ldquo;执行中&rdquo;；执行完成并生成&ldquo;计量检定记录&rdquo;后，系统自动更改状态为&ldquo;已执行&rdquo;。
	private String xiacijdrq;		// 下次检定日期。系统根据计量检定结束日期+计量检定周期自动计算得出
	private List<ShebeiJljdjl> shebeiJljdjl = new ArrayList<ShebeiJljdjl>();		//设备定检记录

	private String jiliangjdlxrId;


	private String dateRange;//入库时间范围 页面传值
	private Date startDate;//入库开始时间
	private Date endDate;//入库结束时间


	public ShebeiJljdjh() {
		super();
	}

	public ShebeiJljdjh(String id){
		super(id);
	}

	@Length(min=0, max=200, message="设备ID长度必须介于 0 和 200 之间")
	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}
	

	public String getJiliangjdzq() {
		return jiliangjdzq;
	}

	public void setJiliangjdzq(String jiliangjdzq) {
		this.jiliangjdzq = jiliangjdzq;
	}
	

	public String getJiliangjddw() {
		return jiliangjddw;
	}

	public void setJiliangjddw(String jiliangjddw) {
		this.jiliangjddw = jiliangjddw;
	}

	public User getJiliangjdlxr() {
		return jiliangjdlxr;
	}

	public void setJiliangjdlxr(User jiliangjdlxr) {
		this.jiliangjdlxr = jiliangjdlxr;
	}

	@Length(min=0, max=200, message="计量检定联系方式长度必须介于 0 和 200 之间")
	public String getJiliangjdlxfs() {
		return jiliangjdlxfs;
	}

	public void setJiliangjdlxfs(String jiliangjdlxfs) {
		this.jiliangjdlxfs = jiliangjdlxfs;
	}
	

	public String getJiliangjdnr() {
		return jiliangjdnr;
	}

	public void setJiliangjdnr(String jiliangjdnr) {
		this.jiliangjdnr = jiliangjdnr;
	}
	

	public String getJihuar() {
		return jihuar;
	}

	public void setJihuar(String jihuar) {
		this.jihuar = jihuar;
	}
	

	public String getJihuarid() {
		return jihuarid;
	}

	public void setJihuarid(String jihuarid) {
		this.jihuarid = jihuarid;
	}
	
	public String getJihuajljdrq() {
		return jihuajljdrq;
	}

	public void setJihuajljdrq(String jihuajljdrq) {
		this.jihuajljdrq = jihuajljdrq;
	}
	
	public String getJiliangjdtxts() {
		return jiliangjdtxts;
	}

	public void setJiliangjdtxts(String jiliangjdtxts) {
		this.jiliangjdtxts = jiliangjdtxts;
	}
	
	public String getJilinagjdjhzt() {
		return jilinagjdjhzt;
	}

	public void setJilinagjdjhzt(String jilinagjdjhzt) {
		this.jilinagjdjhzt = jilinagjdjhzt;
	}
	
	public String getXiacijdrq() {
		return xiacijdrq;
	}

	public void setXiacijdrq(String xiacijdrq) {
		this.xiacijdrq = xiacijdrq;
	}

	public List<ShebeiJljdjl> getShebeiJljdjl() {
		return shebeiJljdjl;
	}

	public void setShebeiJljdjl(List<ShebeiJljdjl> shebeiJljdjl) {
		this.shebeiJljdjl = shebeiJljdjl;
	}

	public String getJiliangfs() {
		return jiliangfs;
	}

	public void setJiliangfs(String jiliangfs) {
		this.jiliangfs = jiliangfs;
	}

	public String getJihuakssj() {
		return jihuakssj;
	}

	public void setJihuakssj(String jihuakssj) {
		this.jihuakssj = jihuakssj;
	}

	public String getJihuajssj() {
		return jihuajssj;
	}

	public void setJihuajssj(String jihuajssj) {
		this.jihuajssj = jihuajssj;
	}

	public String getJiliangjdlxrId() {
		return jiliangjdlxrId;
	}

	public void setJiliangjdlxrId(String jiliangjdlxrId) {
		this.jiliangjdlxrId = jiliangjdlxrId;
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