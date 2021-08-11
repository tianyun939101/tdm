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
public class YuangongCert extends DataEntity<YuangongCert> {

	private static final long serialVersionUID = 1L;

	//员工
	private Yuangong user;
	//资质名称
	private String name;
	//有效期
	private Date valid;
	//证书
	private String fileIds;

	private  Aptitude  testName;

	public Aptitude getTestName() {
		return testName;
	}

	public void setTestName(Aptitude testName) {
		this.testName = testName;
	}

	public Yuangong getUser() {
		return user;
	}

	public void setUser(Yuangong user) {
		this.user = user;
	}

	@JsonFormat(pattern="yyyy-MM-dd")
	public Date getValid() {
		return valid;
	}

	public void setValid(Date valid) {
		this.valid = valid;
	}

	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}