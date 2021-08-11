package com.demxs.tdm.domain.resource.yuangong;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.center.Department;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 员工Entity
 * @author sunjunhui
 * @version 2017-11-08
 */
public class Yuangong extends DataEntity<Yuangong> {
	
	private static final long serialVersionUID = 1L;
	private User user;		// user_id
	private String zzIds;		// 资质ids
	private String zsIds;		// 证书ids
	private AttachmentInfo touxiang;//头像

	private String userIds;//用户ids

	private List<Aptitude> zzList = Lists.newArrayList();
	private List<AttachmentInfo> zsList = Lists.newArrayList();


	private String zzAllName;//资质名称 方便传值 用于页面展示

	private LabInfo labInfo;

	private Department dept;		//科室
	private Department deptBM;		//部门


	private Office office;

	private String deptIds;

	private String posIds;

	private String nodeType;

	private String deptId;

	private String name;

	private String department;

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Yuangong() {
		super();
	}

	public Yuangong(String id){
		super(id);
	}

	public Yuangong(String id,String userId){
		super(id);
		this.user = new User(userId);
	}

    public Yuangong(String id,String userId,String deptId){
        super(id);
        this.user = new User(userId);
        this.dept = new Department(deptId);
    }

	public User getUser() {
		return user;
	}

	public String getUserName(){
	    return user == null ? "" : user.getName();
    }

	public void setUser(User user) {
		this.user = user;
	}
	
	@Length(min=0, max=2000, message="资质ids长度必须介于 0 和 2000 之间")
	public String getZzIds() {
		return zzIds;
	}

	public void setZzIds(String zzIds) {
		this.zzIds = zzIds;
	}
	
	@Length(min=0, max=2000, message="证书ids长度必须介于 0 和 2000 之间")
	public String getZsIds() {
		return zsIds;
	}

	public void setZsIds(String zsIds) {
		this.zsIds = zsIds;
	}

	public List<Aptitude> getZzList() {
		return zzList;
	}

	public void setZzList(List<Aptitude> zzList) {
		this.zzList = zzList;
	}

	public List<AttachmentInfo> getZsList() {
		return zsList;
	}

	public void setZsList(List<AttachmentInfo> zsList) {
		this.zsList = zsList;
	}

	public String getZzAllName() {
		StringBuilder stringBuilder = new StringBuilder();
		if(!Collections3.isEmpty(zzList)){
			/*for(Aptitude aptitude:zzList){
				stringBuilder.append(aptitude.getName()+";");
			}*/
			for(int i = 0;i<zzList.size();i++){

				if(i == zzList.size()-1){
					stringBuilder.append(zzList.get(i).getName());
				}else{
					stringBuilder.append(zzList.get(i).getName()+";");
				}
			}
		}
		return stringBuilder.toString();
	}

	public void setZzAllName(String zzAllName) {
		this.zzAllName = zzAllName;
	}

	public AttachmentInfo getTouxiang() {
		return touxiang;
	}

	public void setTouxiang(AttachmentInfo touxiang) {
		this.touxiang = touxiang;
	}

	@JsonIgnore
	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public LabInfo getLabInfo() {
		return labInfo;
	}

	public void setLabInfo(LabInfo labInfo) {
		this.labInfo = labInfo;
	}

	public String getPosIds() {
		return posIds;
	}

	public void setPosIds(String posIds) {
		this.posIds = posIds;
	}

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptId() {
		return deptId;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public Department getDeptBM() {
		return deptBM;
	}

	public void setDeptBM(Department deptBM) {
		this.deptBM = deptBM;
	}
}