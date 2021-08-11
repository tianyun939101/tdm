package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;

/**
 * 试验组试验计划Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestPlan extends DataEntity<TestPlan> {
	
	private static final long serialVersionUID = 1L;
	private String entrustId;		// 申请单ID
	private String entrustCode;		// 申请单号
	private String tGroupId;		// 试验组ID
	private String code;		// 计划编号
	private Date startDate;		// 计划开始时间
	private Date endDate;		// 计划结束时间
	private List<TestPlanDetail> testPlanDetailList;	//计划详情
	private String labId;       //试验室ID
	public TestPlan() {
		super();
	}

	public TestPlan(String id){
		super(id);
	}

	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=100, message="申请单号长度必须介于 0 和 100 之间")
	public String getEntrustCode() {
		return entrustCode;
	}

	public void setEntrustCode(String entrustCode) {
		this.entrustCode = entrustCode;
	}
	
	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}
	
	@Length(min=0, max=100, message="计划编号长度必须介于 0 和 100 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public List<TestPlanDetail> getTestPlanDetailList() {
		return testPlanDetailList;
	}

	public void setTestPlanDetailList(List<TestPlanDetail> testPlanDetailList) {
		this.testPlanDetailList = testPlanDetailList;
	}

	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}
}