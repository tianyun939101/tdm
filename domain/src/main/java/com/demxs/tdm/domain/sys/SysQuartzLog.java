package com.demxs.tdm.domain.sys;

import java.util.Date;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;


/**
 * 定时任务日志Entity
 * @author sunjunhui
 * @version 2018-01-31
 */
public class SysQuartzLog extends DataEntity<SysQuartzLog> {
	
	private static final long serialVersionUID = 1L;
	private Date startDate;		// start_date
	private Date endDate;		// end_date
	private String result;		// result
	private String excepts;		// excepts
	private String type;		// type
	
	public SysQuartzLog() {
		super();
	}

	public SysQuartzLog(String id){
		super(id);
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Length(min=0, max=1, message="result长度必须介于 0 和 1 之间")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	@Length(min=0, max=2000, message="excepts长度必须介于 0 和 2000 之间")
	public String getExcepts() {
		return excepts;
	}

	public void setExcepts(String excepts) {
		this.excepts = excepts;
	}
	
	@Length(min=0, max=1, message="type长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}