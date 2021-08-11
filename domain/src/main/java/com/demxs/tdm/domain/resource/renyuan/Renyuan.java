package com.demxs.tdm.domain.resource.renyuan;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.fujian.Attachment;

/**
 * 人员（员工）Entity
 * @author 詹小梅
 * @version 2017-06-26
 */
public class Renyuan extends DataEntity<Renyuan> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户id
	private String  userName;		// 用户名
	private Attachment[] zhengshulist; //证书
	private String 	birthDate;
	private String  nativePlace;
	private String  officeId;
	private String officeName;
	private String sex;
	private String userNo;
	private RenyuanZz[] renyuanzz;
	private Attachment[] touxianglist	; //图像
	//-----数据查询-----//
	private String renwumc;
	private String renwusjkssj;
	private String renwusjjssj;
	private String roleid;
	private String zizhimc;
	private User[] userlist;
	private String dsf;
	private Attachment[] dianziqmlist	; //电子签名

	private User dangqianR;//当前人
	private String caozuoqx;//操作权限 1 可编辑 ，0 可查看 ,空 无任何权限

	public Attachment[] getDianziqmlist() {
		return dianziqmlist;
	}

	public void setDianziqmlist(Attachment[] dianziqmlist) {
		this.dianziqmlist = dianziqmlist;
	}

	public User getDangqianR() {
		return dangqianR;
	}

	public void setDangqianR(User dangqianR) {
		this.dangqianR = dangqianR;
	}

	public String getCaozuoqx() {
		return caozuoqx;
	}

	public void setCaozuoqx(String caozuoqx) {
		this.caozuoqx = caozuoqx;
	}
	public Renyuan() {
		super();
	}

	public Renyuan(String id){
		super(id);
	}

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

	public Attachment[] getZhengshulist() {
		return zhengshulist;
	}

	public void setZhengshulist(Attachment[] zhengshulist) {
		this.zhengshulist = zhengshulist;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public RenyuanZz[] getRenyuanzz() {
		return renyuanzz;
	}

	public void setRenyuanzz(RenyuanZz[] renyuanzz) {
		this.renyuanzz = renyuanzz;
	}

	public String getRenwusjkssj() {
		return renwusjkssj;
	}

	public void setRenwusjkssj(String renwusjkssj) {
		this.renwusjkssj = renwusjkssj;
	}

	public String getRenwusjjssj() {
		return renwusjjssj;
	}

	public void setRenwusjjssj(String renwusjjssj) {
		this.renwusjjssj = renwusjjssj;
	}

	public String getRenwumc() {
		return renwumc;
	}

	public void setRenwumc(String renwumc) {
		this.renwumc = renwumc;
	}

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

	public Attachment[] getTouxianglist() {
		return touxianglist;
	}

	public void setTouxianglist(Attachment[] touxianglist) {
		this.touxianglist = touxianglist;
	}

	public String getZizhimc() {
		return zizhimc;
	}

	public void setZizhimc(String zizhimc) {
		this.zizhimc = zizhimc;
	}

	public User[] getUserlist() {
		return userlist;
	}

	public void setUserlist(User[] userlist) {
		this.userlist = userlist;
	}

	public String getDsf() {
		return dsf;
	}

	public void setDsf(String dsf) {
		this.dsf = dsf;
	}
}