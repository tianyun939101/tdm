package com.demxs.tdm.domain.sys;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 角色人员关系Entity
 * @author 詹小梅
 * @version 2017-06-26
 */
public class UserRole extends DataEntity<UserRole> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// user_id
	private String roleId;		// role_id
	private String username;   //用户名
	
	public UserRole() {
		super();
	}

	public UserRole(String id){
		super(id);
	}

	@NotNull(message="user_id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=1, max=64, message="role_id长度必须介于 1 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}