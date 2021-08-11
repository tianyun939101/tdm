package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 角色组织关系Entity
 * @author 詹小梅
 * @version 2017-06-26
 */
public class RoleOffice extends DataEntity<RoleOffice> {
	
	private static final long serialVersionUID = 1L;
	private String roleId;		// role_id
	private Office office;		// office_id
	private String rolename;    //角色名称
	private String officeid;
	
	public RoleOffice() {
		super();
	}

	public RoleOffice(String id){
		super(id);
	}

	@Length(min=1, max=64, message="role_id长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	@NotNull(message="office_id不能为空")
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getOfficeid() {
		return officeid;
	}

	public void setOfficeid(String officeid) {
		this.officeid = officeid;
	}
}