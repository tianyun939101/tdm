/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 构型基础信息Entity
 * @author neo
 * @version 2020-02-18
 */
public class BaseConfiguration extends DataEntity<BaseConfiguration> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 构型名称
	private String labId;		// 所属试验室
	private String labName;
	private String localId;		// 存放位置
	private String localName;
	private String photoId;		// 照片
	private String defVersion;		// 默认使用版本
	private String defVersionName;		// 默认使用版本名称
	private String auditVersion;		// 最新审核通过版本
	private String auditVersionName;		// 最新审核通过版本名称
	private String editVersion;		// 正在编辑版本名称
	private String editVersionName;		// 正在编辑版本名称
	//当前生效版本
	private ConfigurationVO curVersion;

	//非标分配资源保存时的版本
    private ConfigurationVO saveVersion;

	private String subCenter;

	public String getSubCenter() {
		return subCenter;
	}

	public void setSubCenter(String subCenter) {
		this.subCenter = subCenter;
	}

	public BaseConfiguration() {
		super();
	}

	public BaseConfiguration(String id){
		super(id);
	}

	@Length(min=0, max=128, message="构型名称长度必须介于 0 和 128 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="所属试验室长度必须介于 0 和 64 之间")
	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}
	
	@Length(min=0, max=64, message="存放位置长度必须介于 0 和 64 之间")
	public String getLocalId() {
		return localId;
	}

	public void setLocalId(String localId) {
		this.localId = localId;
	}
	
	@Length(min=0, max=64, message="照片长度必须介于 0 和 64 之间")
	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
	
	@Length(min=0, max=128, message="默认使用版本长度必须介于 0 和 128 之间")
	public String getDefVersion() {
		return defVersion;
	}

	public void setDefVersion(String defVersion) {
		this.defVersion = defVersion;
	}
	
	@Length(min=0, max=128, message="最新审核通过版本长度必须介于 0 和 128 之间")
	public String getAuditVersion() {
		return auditVersion;
	}

	public void setAuditVersion(String auditVersion) {
		this.auditVersion = auditVersion;
	}
	
	@Length(min=0, max=128, message="正在编辑版本长度必须介于 0 和 128 之间")
	public String getEditVersion() {
		return editVersion;
	}

	public void setEditVersion(String editVersion) {
		this.editVersion = editVersion;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLocalName() {
		return localName;
	}

	public void setLocalName(String localName) {
		this.localName = localName;
	}

	public String getDefVersionName() {
		return defVersionName;
	}

	public void setDefVersionName(String defVersionName) {
		this.defVersionName = defVersionName;
	}

	public String getAuditVersionName() {
		return auditVersionName;
	}

	public void setAuditVersionName(String auditVersionName) {
		this.auditVersionName = auditVersionName;
	}

	public String getEditVersionName() {
		return editVersionName;
	}

	public void setEditVersionName(String editVersionName) {
		this.editVersionName = editVersionName;
	}

	public ConfigurationVO getCurVersion() {
		return curVersion;
	}

	public void setCurVersion(ConfigurationVO curVersion) {
		this.curVersion = curVersion;
	}

    public ConfigurationVO getSaveVersion() {
        return saveVersion;
    }

    public BaseConfiguration setSaveVersion(ConfigurationVO saveVersion) {
        this.saveVersion = saveVersion;
        return this;
    }

}