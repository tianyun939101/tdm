package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;


/**
 * 试验项Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestUnit extends DataEntity<TestUnit> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 中文名称
	private String sname;		// 简称
	private String ename;		// 英文名称
	private Version version;		// 版本号
	private Double duration;		// 试验时长
	private String status;		// 状态
	private String deviceCredentials;		// 设备资质
	private List<Aptitude> deviceCredentialsList;
	private String type;		// 类型
	private String source;		// 来源
	private String orgId;		// 所属组别
	private String cnasFlag;		// CNAS认证
	private String summary;		// 方法简介
	private String scope;		// 适用范围
	private String userCredentials;		// 人员资质
	private String devices;		// 使用设备
	private String paramerters;		// 试验参数
	private String conditions;		// 试验条件
	private String recordTemplate;		// 原始记录模版
	private String labId;		// 试验室id
	private String reportTemplate;		// 报告模版
    private String describes;//描述
	
	public TestUnit() {
		super();
	}

	public TestUnit(String id){
		super(id);
	}

	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=50, message="简称长度必须介于 0 和 50 之间")
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Length(min=0, max=200, message="英文名称长度必须介于 0 和 200 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=64, message="版本号长度必须介于 0 和 64 之间")
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	public Double getDuration() {
		return duration;
	}

	public void setDuration(Double duration) {
		this.duration = duration;
	}
	
	@Length(min=0, max=100, message="状态长度必须介于 0 和 100 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=2000, message="设备资质长度必须介于 0 和 2000 之间")
	public String getDeviceCredentials() {
		return deviceCredentials;
	}

	public void setDeviceCredentials(String deviceCredentials) {
		this.deviceCredentials = deviceCredentials;
	}
	
	@Length(min=0, max=64, message="类型长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="来源长度必须介于 0 和 100 之间")
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	@Length(min=0, max=64, message="所属组别长度必须介于 0 和 64 之间")
	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	@Length(min=0, max=1, message="CNAS认证长度必须介于 0 和 1 之间")
	public String getCnasFlag() {
		return cnasFlag;
	}

	public void setCnasFlag(String cnasFlag) {
		this.cnasFlag = cnasFlag;
	}
	
	@Length(min=0, max=500, message="方法简介长度必须介于 0 和 500 之间")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Length(min=0, max=500, message="适用范围长度必须介于 0 和 500 之间")
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Length(min=0, max=2000, message="人员资质长度必须介于 0 和 2000 之间")
	public String getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(String userCredentials) {
		this.userCredentials = userCredentials;
	}
	
	@Length(min=0, max=2000, message="使用设备长度必须介于 0 和 2000 之间")
	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
	}
	
	@Length(min=0, max=2000, message="试验参数长度必须介于 0 和 2000 之间")
	public String getParamerters() {
		return paramerters;
	}

	public void setParamerters(String paramerters) {
		this.paramerters = paramerters;
	}
	
	@Length(min=0, max=2000, message="试验条件长度必须介于 0 和 2000 之间")
	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}
	
	@Length(min=0, max=64, message="原始记录模版长度必须介于 0 和 64 之间")
	public String getRecordTemplate() {
		return recordTemplate;
	}

	public void setRecordTemplate(String recordTemplate) {
		this.recordTemplate = recordTemplate;
	}
	
	@Length(min=0, max=64, message="试验室id长度必须介于 0 和 64 之间")
	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}
	
	@Length(min=0, max=64, message="报告模版长度必须介于 0 和 64 之间")
	public String getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(String reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public List<Aptitude> getDeviceCredentialsList() {
		return deviceCredentialsList;
	}

	public void setDeviceCredentialsList(List<Aptitude> deviceCredentialsList) {
		this.deviceCredentialsList = deviceCredentialsList;
	}
	public void addDeviceCredentialsList(Aptitude deviceCredentialsList) {
		if(this.deviceCredentialsList == null){
			this.deviceCredentialsList = new ArrayList<>();
		}
		this.deviceCredentialsList.add(deviceCredentialsList);
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getDescribes() {
        return describes;
    }

    public void setDescribes(String describes) {
        this.describes = describes;
    }

    @Override
	public String toString() {
		return "TestUnit{" +
				"name='" + name + '\'' +
				", sname='" + sname + '\'' +
				", ename='" + ename + '\'' +
				", version=" + version +
				", duration=" + duration +
				", status='" + status + '\'' +
				", deviceCredentials='" + deviceCredentials + '\'' +
				", deviceCredentialsList=" + deviceCredentialsList +
				", type='" + type + '\'' +
				", source='" + source + '\'' +
				", orgId='" + orgId + '\'' +
				", cnasFlag='" + cnasFlag + '\'' +
				", summary='" + summary + '\'' +
				", scope='" + scope + '\'' +
				", userCredentials='" + userCredentials + '\'' +
				", devices='" + devices + '\'' +
				", paramerters='" + paramerters + '\'' +
				", conditions='" + conditions + '\'' +
				", recordTemplate='" + recordTemplate + '\'' +
				", labId='" + labId + '\'' +
				", reportTemplate='" + reportTemplate + '\'' +
				'}';
	}
}