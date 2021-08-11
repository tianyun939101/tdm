/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 构型版本信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class ConfigurationVersion extends DataEntity<ConfigurationVersion> {
	
	private static final long serialVersionUID = 1L;
	private String baseId;		// 构型ID
	private String version;		// 版本号
	private String status;		// 构型状态
	private String curVersion;		// 是否当前使用版本
	private String localReport;		// 审核前报告
	private String auditReport;		// 已审核报告
	private Date reportTime;		// 报告生成时间
	private String revisionRecord;		// 修订记录
    /**
     * 上传报告
     */
    private String uploadReport;
	
	public ConfigurationVersion() {
		super();
	}

	public ConfigurationVersion(String id){
		super(id);
	}

	@Length(min=0, max=64, message="构型ID长度必须介于 0 和 64 之间")
	public String getBaseId() {
		return baseId;
	}

	public void setBaseId(String baseId) {
		this.baseId = baseId;
	}
	
	@Length(min=0, max=128, message="版本号长度必须介于 0 和 128 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=1, message="构型状态长度必须介于 0 和 1 之间")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	@Length(min=0, max=1, message="是否当前使用版本长度必须介于 0 和 1 之间")
	public String getCurVersion() {
		return curVersion;
	}

	public void setCurVersion(String curVersion) {
		this.curVersion = curVersion;
	}
	
	@Length(min=0, max=64, message="审核前报告长度必须介于 0 和 64 之间")
	public String getLocalReport() {
		return localReport;
	}

	public void setLocalReport(String localReport) {
		this.localReport = localReport;
	}
	
	@Length(min=0, max=64, message="已审核报告长度必须介于 0 和 64 之间")
	public String getAuditReport() {
		return auditReport;
	}

	public void setAuditReport(String auditReport) {
		this.auditReport = auditReport;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	@Length(min=0, max=3072, message="修订记录长度必须介于 0 和 3072 之间")
	public String getRevisionRecord() {
		return revisionRecord;
	}

	public void setRevisionRecord(String revisionRecord) {
		this.revisionRecord = revisionRecord;
	}

    public String getUploadReport() {
        return uploadReport;
    }

    public ConfigurationVersion setUploadReport(String uploadReport) {
        this.uploadReport = uploadReport;
        return this;
    }
}