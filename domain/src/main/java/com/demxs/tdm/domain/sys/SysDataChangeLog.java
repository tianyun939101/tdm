package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;

import java.util.Date;

/**
 * Created by chenjinfan on 2018/1/26.
 */
public class SysDataChangeLog extends DataEntity {
	private static final long serialVersionUID = 1L;

	private Integer serviceType;
	private String serviceId;
	private Object serviceObject;
	private String title;
	private String message;
	private byte[] oldJson;
	private byte[] newJson;

	private Date changeTime;
	private User changeUser;


	public Integer getServiceType() {
		return serviceType;
	}

	public void setServiceType(Integer serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public Object getServiceObject() {
		return serviceObject;
	}

	public void setServiceObject(Object serviceObject) {
		this.serviceObject = serviceObject;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public byte[] getOldJson() {
		return oldJson;
	}

	public void setOldJson(byte[] oldJson) {
		this.oldJson = oldJson;
	}

	public byte[] getNewJson() {
		return newJson;
	}

	public void setNewJson(byte[] newJson) {
		this.newJson = newJson;
	}

	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public User getChangeUser() {
		return changeUser;
	}

	public void setChangeUser(User changeUser) {
		this.changeUser = changeUser;
	}
}
