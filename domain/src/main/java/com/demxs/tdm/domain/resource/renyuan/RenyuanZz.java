package com.demxs.tdm.domain.resource.renyuan;

import com.demxs.tdm.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 人员资质(方法,设备)Entity
 * @author 詹小梅
 * @version 2017-06-20
 */
public class RenyuanZz extends DataEntity<RenyuanZz> {
	
	private static final long serialVersionUID = 1L;
	private String renyuanid;		// 人员ID
	private String fangfasbid;		// 方法/设备ID
	private String leixing;		// 类型:资质方法、资质设备
	private int  status ; //	状态（删除1 或 新增2）
	private String zizhiid;     //资质id
	private String zizhimc;		// 资质名称


	/**以下资质人员查询使用**/
	private String[] zizhiids;//资质ids
	private String userId;		// 用户id
	private String  userName;		// 用户名

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

	public String[] getZizhiids() {
		return zizhiids;
	}

	public void setZizhiids(String[] zizhiids) {
		this.zizhiids = zizhiids;
	}

	public RenyuanZz() {
		super();
	}

	public RenyuanZz(String id){
		super(id);
	}

	@Length(min=0, max=200, message="人员ID长度必须介于 0 和 200 之间")
	public String getRenyuanid() {
		return renyuanid;
	}

	public void setRenyuanid(String renyuanid) {
		this.renyuanid = renyuanid;
	}
	
	@Length(min=0, max=200, message="方法/设备ID长度必须介于 0 和 200 之间")
	public String getFangfasbid() {
		return fangfasbid;
	}

	public void setFangfasbid(String fangfasbid) {
		this.fangfasbid = fangfasbid;
	}
	
	@Length(min=0, max=200, message="类型:资质方法、资质设备长度必须介于 0 和 200 之间")
	public String getLeixing() {
		return leixing;
	}

	public void setLeixing(String leixing) {
		this.leixing = leixing;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getZizhiid() {
		return zizhiid;
	}

	public void setZizhiid(String zizhiid) {
		this.zizhiid = zizhiid;
	}

	public String getZizhimc() {
		return zizhimc;
	}

	public void setZizhimc(String zizhimc) {
		this.zizhimc = zizhimc;
	}
}