package com.demxs.tdm.domain.ability;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import org.hibernate.validator.constraints.Length;

import java.util.List;


/**
 * 试验项目Entity
 * @author sunjunhui
 * @version 2017-10-30
 */
public class TestItem extends DataEntity<TestItem> {
	
	private static final long serialVersionUID = 1L;
	private String summary;		// 简介
	private String ename;		// 英文名称
	private String sname;		// 简称
	private Version version;		// 版本号
	private String name;		// 中文名称
	private String scope;		// 使用范围
	private TestCategory type;		// 类型
	private String userCredentials;		// 人员资质
	private List<Aptitude> userCredentialsList;
	private String deviceCredentials;		// 设备资质
	private List<TestItemCodition> conditionsList;
	private String labId;		// 试验室id
	private String status;      //有效性
	private String standardFile; //标准文件
	private List<ZhishiXx> standardFileList;

	private List<TestItemUnit> testUnitsList;//试验项
	private String otherFile;//其他附件

    private String requirement;//测试要求
    private String objective;//试验目的
    private String validationClause;//验证条款
    private String liuChengWenJianId;//流程文件id
    private List<ZhishiXx> liuChengWenJian;//流程文件

	public TestItem() {
		super();
	}

	public TestItem(String id){
		super(id);
	}

	@Length(min=0, max=500, message="简介长度必须介于 0 和 500 之间")
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	@Length(min=0, max=100, message="英文名称长度必须介于 0 和 100 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}
	
	@Length(min=0, max=100, message="简称长度必须介于 0 和 100 之间")
	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
	
	@Length(min=0, max=64, message="版本号长度必须介于 0 和 64 之间")
	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}
	
	@Length(min=0, max=100, message="中文名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=500, message="使用范围长度必须介于 0 和 500 之间")
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Length(min=0, max=64, message="类型长度必须介于 0 和 64 之间")
	public TestCategory getType() {
		return type;
	}

	public void setType(TestCategory type) {
		this.type = type;
	}
	
	@Length(min=0, max=2000, message="人员资质长度必须介于 0 和 2000 之间")
	public String getUserCredentials() {
		return userCredentials;
	}

	public void setUserCredentials(String userCredentials) {
		this.userCredentials = userCredentials;
	}
	
	@Length(min=0, max=2000, message="设备资质长度必须介于 0 和 2000 之间")
	public String getDeviceCredentials() {
		return deviceCredentials;
	}

	public void setDeviceCredentials(String deviceCredentials) {
		this.deviceCredentials = deviceCredentials;
	}

	@Length(min=0, max=64, message="试验室id长度必须介于 0 和 64 之间")
	public String getLabId() {
		return labId;
	}

	public void setLabId(String labId) {
		this.labId = labId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getStandardFile() {
		return standardFile;
	}

	public void setStandardFile(String standardFile) {
		this.standardFile = standardFile;
	}

	public List<Aptitude> getUserCredentialsList() {
		return userCredentialsList;
	}

	public void setUserCredentialsList(List<Aptitude> userCredentialsList) {
		this.userCredentialsList = userCredentialsList;
	}

	public List<TestItemCodition> getConditionsList() {
		return conditionsList;
	}

	public void setConditionsList(List<TestItemCodition> conditionsList) {
		this.conditionsList = conditionsList;
	}

	public List<ZhishiXx> getStandardFileList() {
		return standardFileList;
	}

	public void setStandardFileList(List<ZhishiXx> standardFileList) {
		this.standardFileList = standardFileList;
	}

	public List<TestItemUnit> getTestUnitsList() {
		return testUnitsList;
	}

	public void setTestUnitsList(List<TestItemUnit> testUnitsList) {
		this.testUnitsList = testUnitsList;
	}

	public String getOtherFile() {
		return otherFile;
	}

	public void setOtherFile(String otherFile) {
		this.otherFile = otherFile;
	}

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getValidationClause() {
        return validationClause;
    }

    public void setValidationClause(String validationClause) {
        this.validationClause = validationClause;
    }

    public List<ZhishiXx> getLiuChengWenJian() {
        return liuChengWenJian;
    }

    public TestItem setLiuChengWenJian(List<ZhishiXx> liuChengWenJian) {
        this.liuChengWenJian = liuChengWenJian;
        return this;
    }

    public String getLiuChengWenJianId() {
        return liuChengWenJianId;
    }

    public TestItem setLiuChengWenJianId(String liuChengWenJianId) {
        this.liuChengWenJianId = liuChengWenJianId;
        return this;
    }
}