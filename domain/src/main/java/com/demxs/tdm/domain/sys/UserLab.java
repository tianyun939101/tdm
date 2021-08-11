package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 角色试验室关系Entity
 * @author 詹小梅
 * @version 2017-06-26
 */
public class UserLab extends DataEntity<UserLab> {

	private static final long serialVersionUID = 1L;
	private String labId;		// lab_id
	private String roleId;		// role_id

	public UserLab() {
		super();
	}

	public UserLab(String id){
		super(id);
	}

	public UserLab(String labInfoId, String roleId) {
		super();
		this.labId = labInfoId;
		this.roleId = roleId;
	}

	@Length(min=1, max=64, message="lab_id长度必须介于 1 和 64 之间")
	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}
	
	@Length(min=1, max=64, message="role_id长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}