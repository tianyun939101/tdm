package com.demxs.tdm.domain.lab;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.configure.Baogaomb;

import java.util.ArrayList;
import java.util.List;

/**
 * 试验室信息Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class LabInfo extends DataEntity<LabInfo> {
	
	private static final long serialVersionUID = 1L;

	private String name;    //中文名称
	private String sname;   //简称
	private String ename;   //英文名称
	private String code;    //代码
	private Office office;  //所属部门
	private User leader;    //负责人
	private String contact; //联系方式
	private String address; //地址
	private String status;  //有效性
	private List<LabTestItem> testItemList;
	private List<LabTestSequence> testSequenceList;
	private TestItem preHandleItem;//预处理
	private TestItem preItem;//前置试验
	private Baogaomb reportTemplate; //报告模板
	private String enable;// 1:正式试验室  2:非正式

	private String num;
    /**
     * 所属中心id
     */
    private String center;
    private SubCenter subCenter;
    /**
     * 是否忽略（0：否，1：是）
     */
    private String ignore;

	private Role role;	// 根据角色查询试验室条件
	private User user;	// 根据角色用户查询试验室条件
    /**
     * 试验室关联的视频设备集合
     */
    private List<LabVideoEquipment> videoEquipmentList;
    /**
     * 试验室人员集合
     */
    private List<LabUser> userList;
    /**
     * 视图传递
     */
    private List<User> copyUserList;

	/**
	 * 前置试验室
	 * @return
	 */
	private String parentLabId;
	private LabInfo parentLabInfo;
	/**
	 * 是否为新型试验室
	 */
	private String newLab;
	private String newLabName;

	private String source;

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public LabInfo() {
		super();
	}

	public LabInfo(String id){
		super(id);
	}

	public LabInfo(Role role){
		super();
		this.role = role;
	}

	public LabInfo(Role role,User user){
		super();
		this.role = role;
		this.user= user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public User getLeader() {
		return leader;
	}

	public void setLeader(User leader) {
		this.leader = leader;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<LabTestItem> getTestItemList() {
		return testItemList;
	}

	public void setTestItemList(List<LabTestItem> testItemList) {
		this.testItemList = testItemList;
	}

	public List<LabTestSequence> getTestSequenceList() {
		return testSequenceList;
	}

	public void setTestSequenceList(List<LabTestSequence> testSequenceList) {
		this.testSequenceList = testSequenceList;
	}

	public TestItem getPreHandleItem() {
		return preHandleItem;
	}

	public void setPreHandleItem(TestItem preHandleItem) {
		this.preHandleItem = preHandleItem;
	}

	public TestItem getPreItem() {
		return preItem;
	}

	public void setPreItem(TestItem preItem) {
		this.preItem = preItem;
	}

	public Baogaomb getReportTemplate() {
		return reportTemplate;
	}

	public void setReportTemplate(Baogaomb reportTemplate) {
		this.reportTemplate = reportTemplate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

    public List<LabVideoEquipment> getVideoEquipmentList() {
        return videoEquipmentList;
    }

    public LabInfo setVideoEquipmentList(List<LabVideoEquipment> videoEquipmentList) {
        this.videoEquipmentList = videoEquipmentList;
        return this;
    }

    public String getCenter() {
        return center;
    }

    public LabInfo setCenter(String center) {
        this.center = center;
        return this;
    }

    public SubCenter getSubCenter() {
        return subCenter;
    }

    public LabInfo setSubCenter(SubCenter subCenter) {
        this.subCenter = subCenter;
        return this;
    }

    public List<LabUser> getUserList() {
        return userList;
    }

    public LabInfo setUserList(List<LabUser> userList) {
        this.userList = userList;
        this.setCopyUserList(userList);
        return this;
    }

    public List<User> getCopyUserList() {
        return copyUserList;
    }

    public LabInfo setCopyUserList(List<LabUser> copyUserList) {
        if(null != copyUserList){
            if(null == this.copyUserList){
                this.copyUserList = new ArrayList<>(copyUserList.size());
            }
            for (LabUser labUser : copyUserList) {
                this.copyUserList.add(labUser.getUser());
            }
        }
        return this;
    }

    public String getIgnore() {
        return ignore;
    }

    public LabInfo setIgnore(String ignore) {
        this.ignore = ignore;
        return this;
    }

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getParentLabId() {
		return parentLabId;
	}

	public void setParentLabId(String parentLabId) {
		this.parentLabId = parentLabId;
	}

	public LabInfo getParentLabInfo() {
		return parentLabInfo;
	}

	public void setParentLabInfo(LabInfo parentLabInfo) {
		this.parentLabInfo = parentLabInfo;
	}

	public String getNewLab() {
		return newLab;
	}

	public void setNewLab(String newLab) {
		if("1".equals(newLab)){
			this.newLabName = "十四五试验室";
		}else{
			this.newLabName = "十三五试验室";
		}
		this.newLab = newLab;
	}

	public String getNewLabName() {
		return newLabName;
	}

	public void setNewLabName(String newLabName) {
			this.newLabName = newLabName;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}