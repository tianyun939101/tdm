package com.demxs.tdm.domain.resource.yuangong;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.center.Department;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.Date;
import java.util.List;


/**
 * 员工Entity
 * @author sunjunhui
 * @version 2017-11-08
 */
public class YuangongCertRecord extends DataEntity<YuangongCertRecord> {

	private static final long serialVersionUID = 1L;

	//人员资质
	private YuangongCert cert;
	//培训名称
	private String name;
	//培训记录
	private String record;
	//培训时间
	private Date trainDate;
	//培训文件
	private String fileIds;

	public YuangongCert getCert() {
		return cert;
	}

	public void setCert(YuangongCert cert) {
		this.cert = cert;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getTrainDate() {
		return trainDate;
	}

	public void setTrainDate(Date trainDate) {
		this.trainDate = trainDate;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}
}