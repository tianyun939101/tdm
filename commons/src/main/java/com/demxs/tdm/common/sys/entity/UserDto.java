package com.demxs.tdm.common.sys.entity;

import com.demxs.tdm.common.persistence.Page;

import java.util.Date;

/**
 * 用户信息
 * @author zhangdengcai
 * @version 2017-07-04
 */
public class UserDto {
	private String userId;
	private String renyuanid; //人员id
	private String userName;// 姓名
	private String userEmail;	// 邮箱
	private String userPhone;	// 电话
	private String userMobile;	// 手机
	private String userPhoto;	// 头像
	private String officeId;	// 归属部门id
	private String officeName;	// 归属部门id

	private String loginName;// 登录名
	private String no;		// 工号
	private String userType;// 用户类型
	private String loginIp;	// 最后登陆IP
	private Date loginDate;	// 最后登陆日期
	private String loginFlag;	// 是否允许登陆
	private String sex;     //性别
	private String birthDate;//出生日期
	private String nativePlace;//籍贯

	public String getRenyuanid() {
		return renyuanid;
	}

	public void setRenyuanid(String renyuanid) {
		this.renyuanid = renyuanid;
	}

	/**
	 * 当前实体分页对象
	 */
	protected Page<UserDto> page;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserPhoto() {
		return userPhoto;
	}

	public void setUserPhoto(String userPhoto) {
		this.userPhoto = userPhoto;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public Page<UserDto> getPage() {
		return page;
	}

	public void setPage(Page<UserDto> page) {
		this.page = page;
	}
}