package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 试验组试验计划执行详情Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestPlanExecuteDetail extends DataEntity<TestPlanExecuteDetail> {
	
	private static final long serialVersionUID = 1L;
	private String entrustCode;		// 申请单号
	private String entrustId;		// 申请单ID
	private String tGroupId;		// 试验组ID
	private String planId;			//计划ID
	private String planDetailId;		// 计划详情ID
	private String sampleCode;		// 样品编号
	private Integer sampleType;
	private String deviceId;		// 设备ID
	private String deviceCode;		// 设备编号
	private Integer station;		// 工位
	private Date planStartDate;		// 计划开始时间
	private Date planEndDate;		// 计划结束时间
	
	public TestPlanExecuteDetail() {
		super();
	}

	public TestPlanExecuteDetail(String id){
		super(id);
	}

	@Length(min=0, max=100, message="申请单号长度必须介于 0 和 100 之间")
	public String getEntrustCode() {
		return entrustCode;
	}

	public void setEntrustCode(String entrustCode) {
		this.entrustCode = entrustCode;
	}
	
	@Length(min=0, max=64, message="申请单ID长度必须介于 0 和 64 之间")
	public String getEntrustId() {
		return entrustId;
	}

	public void setEntrustId(String entrustId) {
		this.entrustId = entrustId;
	}
	
	@Length(min=0, max=64, message="试验组ID长度必须介于 0 和 64 之间")
	public String getTGroupId() {
		return tGroupId;
	}

	public void setTGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	@Length(min=0, max=64, message="计划详情ID长度必须介于 0 和 64 之间")
	public String getPlanDetailId() {
		return planDetailId;
	}

	public void setPlanDetailId(String planDetailId) {
		this.planDetailId = planDetailId;
	}
	
	@Length(min=0, max=100, message="样品编号长度必须介于 0 和 100 之间")
	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	public String gettGroupId() {
		return tGroupId;
	}

	public void settGroupId(String tGroupId) {
		this.tGroupId = tGroupId;
	}

	public Integer getSampleType() {
		return sampleType;
	}

	public void setSampleType(Integer sampleType) {
		this.sampleType = sampleType;
	}

	@Length(min=0, max=64, message="设备ID长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	@Length(min=0, max=100, message="设备编号长度必须介于 0 和 100 之间")
	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	
	public Integer getStation() {
		return station;
	}

	public void setStation(Integer station) {
		this.station = station;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanStartDate() {
		return planStartDate;
	}

	public void setPlanStartDate(Date planStartDate) {
		this.planStartDate = planStartDate;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	
}