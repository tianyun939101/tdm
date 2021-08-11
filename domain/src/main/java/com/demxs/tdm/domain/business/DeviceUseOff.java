package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;


/**
 * 试验设备使用结束时间Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class DeviceUseOff extends DataEntity<DeviceUseOff> {
	
	private static final long serialVersionUID = 1L;
	private String deviceId;		// 设备ID
	private Integer useStation;		// 使用设备工位
	private Date planStartTime;		// 计划结束时间
	private Date planEndTime;		// 计划结束时间
	private Integer deviceStatus;		// 设备状态
	private Integer stationStatus;		// 工位状态
	private String sampleId;
	private Integer sampleType;
	private String planDetailId;
	
	public DeviceUseOff() {
		super();
	}

	public DeviceUseOff(String id){
		super(id);
	}

	public DeviceUseOff(String deviceId, Integer station, Date planEndTime) {
		this.deviceId = deviceId;
		this.useStation = station;
		this.planEndTime = planEndTime;
	}

	@Length(min=0, max=64, message="设备ID长度必须介于 0 和 64 之间")
	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public Integer getUseStation() {
		return useStation;
	}

	public void setUseStation(Integer useStation) {
		this.useStation = useStation;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPlanEndTime() {
		return planEndTime;
	}

	public void setPlanEndTime(Date planEndTime) {
		this.planEndTime = planEndTime;
	}
	
	public Integer getDeviceStatus() {
		return deviceStatus;
	}

	public void setDeviceStatus(Integer deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	
	public Integer getStationStatus() {
		return stationStatus;
	}

	public void setStationStatus(Integer stationStatus) {
		this.stationStatus = stationStatus;
	}
	
}