package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.lab.LabInfo;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 角色试验室关系Entity
 * @author 詹小梅
 * @version 2017-06-26
 */
public class LabRole extends DataEntity<LabRole> {

	private static final long serialVersionUID = 1L;
	private LabInfo lab;		// user_id
	private String roleId;		// role_id
	private String labName;   //名

	private User user;
	private String userId;

	public LabRole() {
		super();
	}

	public LabRole(String id){
		super(id);
	}

	public LabRole(String labInfoId, String roleId) {
		super();
		lab = new LabInfo(labInfoId);
		this.roleId = roleId;
	}

	public LabRole(String labInfoId, String roleId,String userId) {
		super();
		lab = new LabInfo(labInfoId);
		user = new User(userId);
		this.roleId = roleId;
	}

	@NotNull(message="user_id不能为空")
	public LabInfo getLab() {
		return lab;
	}

	public void setLab(LabInfo lab) {
		this.lab = lab;
	}
	
	@Length(min=1, max=64, message="role_id长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getLabname() {
		return labName;
	}

	public void setLabname(String labName) {
		this.labName = labName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}