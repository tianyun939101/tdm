package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/25 15:12
 * @Description: 任务单
 */
public class Plan extends DataEntity<Plan> {

    private static final long serialVersionUID = 1L;
    //序号
    private String serialNumber;
    //任务项名称
    private String taskItemName;
    //责任人
    private String liableUserId;

    //责任主体
    private String liableBody;
    //计划开始时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requireStartTime;
    //应完时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date requireCompleteTime;
    //实际完成时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date actualCompleteTime;
    //确认完成时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date assessmentTime;
    //任务项性质
    private String taskItemNature;
    //完成形式
    private String completeForm;
    //进展状态
    private String state;
    //评价人
    private String evaluateUser;
    //协助部门
    private String assistCompany;
    //计划工时
    private String planWorkingHour;
    //实际工时
    private String praWorkingHour;
    //主任务项
    private String mainTask;
    //成熟度
    private String maturity;
    //输入
    private String inPut;
    //输出
    private String outPut;
    //准出条件
    private String allowCondtion;
    //专业
    private String major;
    //机型
    private String aircraftType;
    //架次
    private String sortie;
    //图号
    private String figuerNum;
    //来源
    private String source;
    //描述
    private String describe;
    //业务编号
    private String businessNum;
    //报送流程状态
    private String evoleExplain;

    private User liableUser;
    //计划类别
    private String category;
    //计划属性
    private String attribute;
    //责任单位
    private String liableCompany;
    //配合单位
    private String coorCompany;
    //是否允许调整
    private String allowAdjust;
    //是否允许分解
    private String allowDecompose;
    //关注人
    private String followUser;
    //任务项类型
    private String taskItemType;

    //任务单
    private String workId;
    //任务单名称
    private String  workOrderName;

    //时间区间
    private String dateRange;
    //要求开始时间Str
    private String actualCompleteTimeStr;
    //要求开始时间Str
    private String requireStartTimeStr;
    //应完时间Str
    private String requireCompleteTimeStr;

    //父级ID
    private String parentid;
    //子节点
    private List<Plan> children;
    //当前层级
    private String level;

    //任务单跳转
    private String flag;

    private String y2;
    //重名
    private List<Plan> reName;

    //
    private Office office;
    private String officeId;

    private String time;

    public void setSource(String source) {
        this.source = source;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public List<Plan> getReName() {
        return reName;
    }

    public void setReName(List<Plan> reName) {
        this.reName = reName;
    }

    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public List<Plan> getChildren() {
        return children;
    }

    public void setChildren(List<Plan> children) {
        this.children = children;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public Plan() {
    }

    public Plan(String id){
        super(id);
    }

    /*@ExcelField(title = "任务项名称",sort = 0,type = 1)
    public String getTaskItemName() {
        return taskItemName;
    }

    @ExcelField(title = "任务项名称",sort = 0,type = 2)
    public Plan setTaskItemName(String taskItemName) {
        this.taskItemName = taskItemName;
        return this;
    }*/
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @ExcelField(title = "序号",sort = 0,type = 1)
    public String getSerialNumber() {
        return serialNumber;
    }

    @ExcelField(title = "序号",sort = 0,type = 2)
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @ExcelField(title = "名称",sort = 1,type = 1)
    public String getTaskItemName() {
        return taskItemName;
    }

    @ExcelField(title = "=名称",sort = 1,type = 2)
    public void setTaskItemName(String taskItemName) {
        this.taskItemName = taskItemName;
    }

    @ExcelField(title = "责任人",sort = 2,type = 1)
    public String getLiableUserId() {
        return liableUserId;
    }

    @ExcelField(title = "责任人",sort = 2,type = 2)
    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
    }

    @ExcelField(title = "责任主体",sort = 3,type = 1)
    public String getLiableBody() {
        return liableBody;
    }

    @ExcelField(title = "责任主体",sort = 3,type = 2)
    public void setLiableBody(String liableBody) {
        this.liableBody = liableBody;
    }
    @ExcelField(title = "计划开始时间",sort = 4,type = 1)
    public Date getRequireStartTime() {
        return requireStartTime;
    }

    @ExcelField(title = "计划开始时间",sort = 4,type = 2)
    public void setRequireStartTime(Date requireStartTime) {
        this.requireStartTime = requireStartTime;
    }

    @ExcelField(title = "应完时间",sort = 5,type = 1)
    public Date getRequireCompleteTime() {
        return requireCompleteTime;
    }

    @ExcelField(title = "应完时间",sort = 5,type = 2)
    public void setRequireCompleteTime(Date requireCompleteTime) {
        this.requireCompleteTime = requireCompleteTime;
    }

    @ExcelField(title = "实际完成时间",sort = 6,type = 1)
    public Date getActualCompleteTime() {
        return actualCompleteTime;
    }

    @ExcelField(title = "实际完成时间",sort = 6,type = 2)
    public void setActualCompleteTime(Date actualCompleteTime) {
        this.actualCompleteTime = actualCompleteTime;
    }

    @ExcelField(title = "确认完成时间",sort = 7,type = 1)
    public Date getAssessmentTime() {
        return assessmentTime;
    }

    @ExcelField(title = "确认完成时间",sort = 7,type = 2)
    public void setAssessmentTime(Date assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    @ExcelField(title = "任务项性质",sort = 8,type = 1)
    public String getTaskItemNature() {
        return taskItemNature;
    }

    @ExcelField(title = "任务项性质",sort = 8,type = 2)
    public void setTaskItemNature(String taskItemNature) {
        this.taskItemNature = taskItemNature;
    }

    @ExcelField(title = "完成形式",sort = 9,type = 1)
    public String getCompleteForm() {
        return completeForm;
    }

    @ExcelField(title = "完成形式",sort = 9,type = 2)
    public void setCompleteForm(String completeForm) {
        this.completeForm = completeForm;
    }

    @ExcelField(title = "状态",sort = 10,type = 1)
    public String getState() {
        return state;
    }

    @ExcelField(title = "状态",sort = 10,type = 2)
    public void setState(String state) {
        this.state = state;
    }

    @ExcelField(title = "评价人",sort = 11,type = 1)
    public String getEvaluateUser() {
        return evaluateUser;
    }

    @ExcelField(title = "评价人",sort = 11,type = 2)
    public void setEvaluateUser(String evaluateUser) {
        this.evaluateUser = evaluateUser;
    }

    @ExcelField(title = "协助部门",sort = 12,type = 1)
    public String getAssistCompany() {
        return assistCompany;
    }

    @ExcelField(title = "协助部门",sort = 12,type = 2)
    public void setAssistCompany(String assistCompany) {
        this.assistCompany = assistCompany;
    }

    @ExcelField(title = "计划工时",sort = 13,type = 1)
    public String getPlanWorkingHour() {
        return planWorkingHour;
    }

    @ExcelField(title = "计划工时",sort = 13,type = 2)
    public void setPlanWorkingHour(String planWorkingHour) {
        this.planWorkingHour = planWorkingHour;
    }

    @ExcelField(title = "实际工时",sort = 14,type = 1)
    public String getPraWorkingHour() {
        return praWorkingHour;
    }

    @ExcelField(title = "实际工时",sort = 14,type = 2)
    public void setPraWorkingHour(String praWorkingHour) {
        this.praWorkingHour = praWorkingHour;
    }

    @ExcelField(title = "是否主任务项",sort = 15,type = 1)
    public String getMainTask() {
        return mainTask;
    }

    @ExcelField(title = "是否主任务项",sort = 15,type = 2)
    public void setMainTask(String mainTask) {
        this.mainTask = mainTask;
    }

    @ExcelField(title = "成熟度",sort = 16,type = 1)
    public String getMaturity() {
        return maturity;
    }

    @ExcelField(title = "成熟度",sort = 16,type = 2)
    public void setMaturity(String maturity) {
        this.maturity = maturity;
    }

    @ExcelField(title = "输入",sort = 17,type = 1)
    public String getInPut() {
        return inPut;
    }

    @ExcelField(title = "输入",sort = 17,type = 2)
    public void setInPut(String inPut) {
        this.inPut = inPut;
    }

    @ExcelField(title = "输出",sort = 18,type = 1)
    public String getOutPut() {
        return outPut;
    }

    @ExcelField(title = "输出",sort = 18,type = 2)
    public void setOutPut(String outPut) {
        this.outPut = outPut;
    }

    @ExcelField(title = "准出条件",sort = 19,type = 1)
    public String getAllowCondtion() {
        return allowCondtion;
    }

    @ExcelField(title = "准出条件",sort = 19,type = 2)
    public void setAllowCondtion(String allowCondtion) {
        this.allowCondtion = allowCondtion;
    }

    @ExcelField(title = "专业",sort = 20,type = 1)
    public String getMajor() {
        return major;
    }

    @ExcelField(title = "专业",sort = 20,type = 2)
    public void setMajor(String major) {
        this.major = major;
    }

    @ExcelField(title = "机型",sort = 21,type = 1)
    public String getAircraftType() {
        return aircraftType;
    }

    @ExcelField(title = "机型",sort = 21,type = 2)
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    @ExcelField(title = "架次",sort = 22,type = 1)
    public String getSortie() {
        return sortie;
    }

    @ExcelField(title = "架次",sort = 22,type = 2)
    public void setSortie(String sortie) {
        this.sortie = sortie;
    }

    @ExcelField(title = "图号",sort = 23,type = 1)
    public String getFiguerNum() {
        return figuerNum;
    }

    @ExcelField(title = "图号",sort = 23,type = 2)
    public void setFiguerNum(String figuerNum) {
        this.figuerNum = figuerNum;
    }

    @ExcelField(title = "来源",sort = 24,type = 1)
    public String getSource() {
        return source;
    }

    @ExcelField(title = "来源",sort = 24,type = 2)
    public void getSource(String source) {
        this.source = source;
    }

    @ExcelField(title = "描述",sort = 25,type = 1)
    public String getDescribe() {
        return describe;
    }

    @ExcelField(title = "描述",sort = 25,type = 2)
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @ExcelField(title = "业务编号",sort = 26,type = 1)
    public String getBusinessNum() {
        return businessNum;
    }

    @ExcelField(title = "业务编号",sort = 26,type = 2)
    public void setBusinessNum(String businessNum) {
        this.businessNum = businessNum;
    }

    @ExcelField(title = "报送流程状态",sort = 27,type = 1)
    public String getEvoleExplain() {
        return evoleExplain;
    }

    @ExcelField(title = "报送流程状态",sort = 27,type = 2)
    public void setEvoleExplain(String evoleExplain) {
        this.evoleExplain = evoleExplain;
    }

    public User getLiableUser() {
        return liableUser;
    }

    public void setLiableUser(User liableUser) {
        this.liableUser = liableUser;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getLiableCompany() {
        return liableCompany;
    }

    public void setLiableCompany(String liableCompany) {
        this.liableCompany = liableCompany;
    }

    public String getCoorCompany() {
        return coorCompany;
    }

    public void setCoorCompany(String coorCompany) {
        this.coorCompany = coorCompany;
    }

    public String getAllowAdjust() {
        return allowAdjust;
    }

    public void setAllowAdjust(String allowAdjust) {
        this.allowAdjust = allowAdjust;
    }

    public String getAllowDecompose() {
        return allowDecompose;
    }

    public void setAllowDecompose(String allowDecompose) {
        this.allowDecompose = allowDecompose;
    }

    public String getFollowUser() {
        return followUser;
    }

    public void setFollowUser(String followUser) {
        this.followUser = followUser;
    }

    public String getTaskItemType() {
        return taskItemType;
    }

    public void setTaskItemType(String taskItemType) {
        this.taskItemType = taskItemType;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }

    public String getDateRange() {
        return dateRange;
    }

    public String getActualCompleteTimeStr() {
        return actualCompleteTimeStr;
    }

    public String getRequireStartTimeStr() {
        return requireStartTimeStr;
    }

    public void setRequireStartTimeStr(String requireStartTimeStr) {
        this.requireStartTimeStr = requireStartTimeStr;
    }

    public String getRequireCompleteTimeStr() {
        return requireCompleteTimeStr;
    }

    public void setRequireCompleteTimeStr(String requireCompleteTimeStr) {
        this.requireCompleteTimeStr = requireCompleteTimeStr;
    }

    public Plan setDateRange(String dateRange) {
        this.dateRange = dateRange;
        if(StringUtils.isNotBlank(dateRange)){
            String[] split = dateRange.split(" - ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                this.requireStartTime = dateFormat.parse(split[0]);
                this.requireCompleteTime = dateFormat.parse(split[1]);
                this.requireStartTimeStr = split[0];
                this.requireCompleteTimeStr = split[1];
            }catch (Exception e){
                this.requireStartTime = null;
                this.requireCompleteTime = null;
                this.dateRange = null;
            }
        }
        return this;
    }
    public Plan setActualCompleteTimeStr(String actualCompleteTimeStr) {
        this.actualCompleteTimeStr = actualCompleteTimeStr;
        if(StringUtils.isNotBlank(actualCompleteTimeStr)){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                actualCompleteTime = dateFormat.parse(actualCompleteTimeStr);
            }catch (Exception e){
                this.actualCompleteTime = null;
                this.actualCompleteTimeStr = null;
            }
        }
        return this;
    }

}
