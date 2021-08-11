package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 试验资质Entity
 * @author sunjunhui
 * @version 2017-10-31
 */
public class Aptitude extends DataEntity<Aptitude> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 中文名称
	private String ename;		// 英文名称
	private String type;		// 类型 1人员 2设备


	private String userName;

	private String userNo;

	private List<Aptitude> user;


	public List<Aptitude> getUser() {
		return user;
	}

	public void setUser(List<Aptitude> user) {
		this.user = user;
	}


	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Aptitude() {
		super();
	}

	public Aptitude(String id){
		super(id);
	}

	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=64, message="类型长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}