package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import org.hibernate.validator.constraints.Length;

/**
 * 试验项目用户Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class ProjectUser extends DataEntity<ProjectUser> {
	
	private static final long serialVersionUID = 1L;
	private String projectId;		// 项目ID
	private User user;		// 用户ID
	private Integer coreFlag;		// 0普通用户 1核心用户
	
	public ProjectUser() {
		super();
	}

	public ProjectUser(String id){
		super(id);
	}
	public ProjectUser(String id,String projectId){
		super(id);
		this.projectId = projectId;
	}

	public ProjectUser(String id,String projectId,String userId,Integer coreFlag){
		super(id);
		this.projectId = projectId;
		this.user = new User(userId);
		this.coreFlag = coreFlag;
	}

	@Length(min=0, max=64, message="项目ID长度必须介于 0 和 64 之间")
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getCoreFlag() {
		return coreFlag;
	}

	public void setCoreFlag(Integer coreFlag) {
		this.coreFlag = coreFlag;
	}


	public static final Integer MAINUSER = 1;//核心成员

	public static  final Integer NORMALUSER = 0;//普通成员
	
}