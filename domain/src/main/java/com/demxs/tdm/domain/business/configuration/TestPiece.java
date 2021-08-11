package com.demxs.tdm.domain.business.configuration;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.center.Department;

import java.util.List;

/**
 * 试验件Entity
 */
public class TestPiece extends DataEntity<TestPiece> {

    private static final long serialVersionUID = 1L;

    //试验件号
    private String code;
    //试验件名称
    private String name;
    //试验件英文名
    private String eName;
    //试验件图号
    private String tuHao;
    //试验件工作包
    private String workPackage;
    //供应商
    private String supplier;
    //装机件供应商件号
    private String snCode;
    //试验件版本
    private String version;
    //试验件数量
    private String num;

    //所属实验室Id
    private String labId;
    //序列号
    private String serialNum;
    //硬件版本
    private String hardwareVersion;
    /**
     * 视图传递，所属科室
     */
    private String officeId;

    private Department department;

    //所属实验室
    private LabInfo labInfo;

    private Office office;
    /**
     * 软件件号
     */
    private String softwareCode;
    /**
     * 软件名称
     */
    private String softwareName;
    /**
     * 软件名称
     */
    private String softwareVersion;

    private String handWareNo;

    private List<TestPiceSoftware> softwares;

    private List<TestPiceTest> testPiceTest;

    private String  deptId;

    private String parentId;

    private String nodeType;

    private String parentIds;

    private String labelingA;   //挂签件

    private String coc;

    private String atr;

    private String  airworthinessLabel; //适航标签

    private String userStat;    //使用状态



    private String subCenter;

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    private TestPieceALog testPieceALog;
    private List<TestPieceALog> testPieceALogList;


    public TestPieceALog getTestPieceALog() {
        return testPieceALog;
    }

    public void setTestPieceALog(TestPieceALog testPieceALog) {
        this.testPieceALog = testPieceALog;
    }

    public List<TestPieceALog> getTestPieceALogList() {
        return testPieceALogList;
    }

    public void setTestPieceALogList(List<TestPieceALog> testPieceALogList) {
        this.testPieceALogList = testPieceALogList;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }


    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }



    public List<TestPiceTest> getTestPiceTest() {
        return testPiceTest;
    }

    public void setTestPiceTest(List<TestPiceTest> testPiceTest) {
        this.testPiceTest = testPiceTest;
    }

    public String getHandWareNo() {
        return handWareNo;
    }

    public void setHandWareNo(String handWareNo) {
        this.handWareNo = handWareNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    public String getTuHao() {
        return tuHao;
    }

    public void setTuHao(String tuHao) {
        this.tuHao = tuHao;
    }

    public String getWorkPackage() {
        return workPackage;
    }

    public void setWorkPackage(String workPackage) {
        this.workPackage = workPackage;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSnCode() {
        return snCode;
    }

    public void setSnCode(String snCode) {
        this.snCode = snCode;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getSoftwareCode() {
        return softwareCode;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public void setSerialNum(String serialNum) {
        this.serialNum = serialNum;
    }

    public String getHardwareVersion() {
        return hardwareVersion;
    }

    public void setHardwareVersion(String hardwareVersion) {
        this.hardwareVersion = hardwareVersion;
    }

    public TestPiece setSoftwareCode(String softwareCode) {
        this.softwareCode = softwareCode;
        return this;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public TestPiece setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
        return this;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public TestPiece setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
        return this;
    }

    public String getOfficeId() {
        return officeId;
    }

    public TestPiece setOfficeId(String officeId) {
        this.officeId = officeId;
        return this;
    }

    public List<TestPiceSoftware> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(List<TestPiceSoftware> softwares) {
        this.softwares = softwares;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getLabelingA() {
        return labelingA;
    }

    public void setLabelingA(String labelingA) {
        this.labelingA = labelingA;
    }

    public String getCoc() {
        return coc;
    }

    public void setCoc(String coc) {
        this.coc = coc;
    }

    public String getAtr() {
        return atr;
    }

    public void setAtr(String atr) {
        this.atr = atr;
    }

    public String getAirworthinessLabel() {
        return airworthinessLabel;
    }

    public void setAirworthinessLabel(String airworthinessLabel) {
        this.airworthinessLabel = airworthinessLabel;
    }

    public String getUserStat() {
        return userStat;
    }

    public void setUserStat(String userStat) {
        this.userStat = userStat;
    }
}
