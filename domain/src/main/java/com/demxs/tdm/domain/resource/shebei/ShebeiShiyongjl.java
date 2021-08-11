package com.demxs.tdm.domain.resource.shebei;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.TestTask;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 设备使用记录Entity
 * @author sunjunhui
 * @version 2017-11-03
 */
public class ShebeiShiyongjl extends DataEntity<ShebeiShiyongjl> {
	
	private static final long serialVersionUID = 1L;
	private String shebeiid;    //设备id
	private TestTask task;		// 任务
	private String itemId;		// 试验项目id
	private String source;		// 来源方式 0表示手动新增的 1表示非手动新增
	private String itemName;    //试验项目名称
	private String entrustCode;//申请单编号



	private String dateRange;//入库时间范围 页面传值
	private Date startDate;//入库开始时间
	private Date endDate;//入库结束时间

	public ShebeiShiyongjl() {
		super();
	}

	public ShebeiShiyongjl(String id){
		super(id);
	}

	public TestTask getTask() {
		return task;
	}

	public void setTask(TestTask task) {
		this.task = task;
	}

	@Length(min=0, max=64, message="试验项目id长度必须介于 0 和 64 之间")
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	
	@Length(min=0, max=10, message="来源方式 0表示手动新增的 1表示非手动新增长度必须介于 0 和 10 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}


	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getShebeiid() {
		return shebeiid;
	}

	public void setShebeiid(String shebeiid) {
		this.shebeiid = shebeiid;
	}

	public String getEntrustCode() {
		return entrustCode;
	}

	public void setEntrustCode(String entrustCode) {
		this.entrustCode = entrustCode;
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


	public static final String SHOUDONG = "0";

	public static final String NO_SHOUDONG = "1";
}