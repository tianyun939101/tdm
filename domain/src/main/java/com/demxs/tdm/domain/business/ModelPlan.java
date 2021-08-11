package com.demxs.tdm.domain.business;


import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModelPlan extends DataEntity<ModelPlan> {

    private  static final long serialVersionUID = 1L;
    private  static final SimpleDateFormat s2 = new SimpleDateFormat("yyyy-MM-dd");
    //层级型号
    private String serialNumber;
    //类型
    private String type;
    //WBS编号
    private String wbs;
    //WBS名称
    private String wbsName;
    //WBS账户控制人
    private String wbsACC;
    //状态
    private String status;
    //来源
    private String source;
    //名称
    private String name;
    //关注人
    private String follower;
    //责任团队
    private String liableTeam;
    //责任单位
    private String liableUnit;
    //责任人
    private String liableUser;
    //责任人工号
    private String liableUserNo;
    //责任人派出单位
    private String userOutunit;
    //责任人派出部门
    private String userOutorg;
    //开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;
    //应完时间
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date completeTime;
    //评价人
    private String evaluateUser;
    //评价人工号
    private String evaluateUserNo;
    //转包前责任人
    private String beforeLiableUser;
    //转包前责任人工号
    private String beforeLiableUserNo;
    //所属阶段
    private String subStage;
    //工时价值系数
    private String coeHour;
    //实际完成时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date actualCompleteTime;
    //确认完成时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date assessmentTime;
    //计划工时
    private String planWorkingHour;
    //劳动工时
    private String labourHour;
    //是否编制卡单
    private String isPrepareCard;
    //卡单编制人
    private String prepareCardUser;
    //卡单状态
    private String prepareCardState;
    //卡单发布时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date prepareCardTime;
    //TTGF
    private String ttgf;
    //上级关注
    private String superiorAttention;
    //项目0级管控计划
    private String projectControlZero;
    //项目1级管控计划
    private String projectControlOne;
    //单位部门管控计划
    private String unitControlPlan;
    //关键路径
    private String criticalPath;
    //主要节点
    private String masterNode;
    //重点任务
    private String importmentTask;
    //年度目标精品工程
    private String annualQualityPro;
    //三年行动计划
    private String TYearActionPlan;
    //高高原
    private String GGY;
    //重复性质量问题
    private String reQualityProblem;
    //好维修
    private String goodMaintenance;
    //备注1
    private String remarksOne;
    //备注2
    private String remarksTwo;
    //备注3
    private String remarksThir;
    //备注4
    private String remarksFour;
    //备注5
    private String remarksFif;
    //进展说明
    private String evolveState;




    //完成形式
    private String completeFrom;
    //公司程序
    private String companyPro;
    //项目程序
    private String projectPro;
    //公司标准规范
    private String companyBzgf;
    //项目标准规范
    private String projectBzgf;
    //关联外协名称
    private String relevanceOutsourceName;
    //出国主团名称
    private String goAbroad;
    //责任主体类型
    private String liableBodyType;



    //父级ID
    private String parentId;
    //子节点
    private List<ModelPlan> children;
    //深度
    private String deep;
    //任务单ID
    private String workId;
    private String workOrderName;

    private String flag;


    //
    private User liableUserMap;
    //预留字段
    private String y2;  //主表ID
    private String y3;

    //重名
    private List<ModelPlan> reName;
    //
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<ModelPlan> getReName() {
        return reName;
    }
    public void setReName(List<ModelPlan> reName) {
        this.reName = reName;
    }

    public User getLiableUserMap() {
        return liableUserMap;
    }

    public void setLiableUserMap(User liableUserMap) {
        this.liableUserMap = liableUserMap;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public List<ModelPlan> getChildren() {
        return children;
    }

    public void setChildren(List<ModelPlan> children) {
        this.children = children;
    }

    public String getLiableUnit() {
        return liableUnit;
    }

    public void setLiableUnit(String liableUnit) {
        this.liableUnit = liableUnit;
    }

    public String getWorkOrderName() {
        return workOrderName;
    }

    public void setWorkOrderName(String workOrderName) {
        this.workOrderName = workOrderName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLiableTeam() {
        return liableTeam;
    }

    public void setLiableTeam(String liableTeam) {
        this.liableTeam = liableTeam;
    }

    public String getLiableUser() {
        return liableUser;
    }

    public void setLiableUser(String liableUser) {
        this.liableUser = liableUser;
    }

    public String getUserOutunit() {
        return userOutunit;
    }

    public void setUserOutunit(String userOutunit) {
        this.userOutunit = userOutunit;
    }

    public String getUserOutorg() {
        return userOutorg;
    }

    public void setUserOutorg(String userOutorg) {
        this.userOutorg = userOutorg;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public String getEvaluateUser() {
        return evaluateUser;
    }

    public void setEvaluateUser(String evaluateUser) {
        this.evaluateUser = evaluateUser;
    }

    public String getBeforeLiableUser() {
        return beforeLiableUser;
    }

    public void setBeforeLiableUser(String beforeLiableUser) {
        this.beforeLiableUser = beforeLiableUser;
    }

    public Date getActualCompleteTime() {
        return actualCompleteTime;
    }

    public void setActualCompleteTime(Date actualCompleteTime) {
        this.actualCompleteTime = actualCompleteTime;
    }

    public Date getAssessmentTime() {
        return assessmentTime;
    }

    public void setAssessmentTime(Date assessmentTime) {
        this.assessmentTime = assessmentTime;
    }

    public String getPlanWorkingHour() {
        return planWorkingHour;
    }

    public void setPlanWorkingHour(String planWorkingHour) {
        this.planWorkingHour = planWorkingHour;
    }

    public String getLabourHour() {
        return labourHour;
    }

    public void setLabourHour(String labourHour) {
        this.labourHour = labourHour;
    }

    public String getCompleteFrom() {
        return completeFrom;
    }

    public void setCompleteFrom(String completeFrom) {
        this.completeFrom = completeFrom;
    }

    public String getCompanyPro() {
        return companyPro;
    }

    public void setCompanyPro(String companyPro) {
        this.companyPro = companyPro;
    }

    public String getProjectPro() {
        return projectPro;
    }

    public void setProjectPro(String projectPro) {
        this.projectPro = projectPro;
    }

    public String getCompanyBzgf() {
        return companyBzgf;
    }

    public void setCompanyBzgf(String companyBzgf) {
        this.companyBzgf = companyBzgf;
    }

    public String getProjectBzgf() {
        return projectBzgf;
    }

    public void setProjectBzgf(String projectBzgf) {
        this.projectBzgf = projectBzgf;
    }

    public String getRelevanceOutsourceName() {
        return relevanceOutsourceName;
    }

    public void setRelevanceOutsourceName(String relevanceOutsourceName) {
        this.relevanceOutsourceName = relevanceOutsourceName;
    }

    public String getGoAbroad() {
        return goAbroad;
    }

    public void setGoAbroad(String goAbroad) {
        this.goAbroad = goAbroad;
    }

    public String getLiableBodyType() {
        return liableBodyType;
    }

    public void setLiableBodyType(String liableBodyType) {
        this.liableBodyType = liableBodyType;
    }

    public String getTtgf() {
        return ttgf;
    }

    public void setTtgf(String ttgf) {
        this.ttgf = ttgf;
    }

    public String getSuperiorAttention() {
        return superiorAttention;
    }

    public void setSuperiorAttention(String superiorAttention) {
        this.superiorAttention = superiorAttention;
    }

    public String getProjectControlZero() {
        return projectControlZero;
    }

    public void setProjectControlZero(String projectControlZero) {
        this.projectControlZero = projectControlZero;
    }

    public String getProjectControlOne() {
        return projectControlOne;
    }

    public void setProjectControlOne(String projectControlOne) {
        this.projectControlOne = projectControlOne;
    }

    public String getMasterNode() {
        return masterNode;
    }

    public void setMasterNode(String masterNode) {
        this.masterNode = masterNode;
    }

    public String getImportmentTask() {
        return importmentTask;
    }

    public void setImportmentTask(String importmentTask) {
        this.importmentTask = importmentTask;
    }

    public String getEvolveState() {
        return evolveState;
    }

    public void setEvolveState(String evolveState) {
        this.evolveState = evolveState;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDeep() {
        return deep;
    }

    public void setDeep(String deep) {
        this.deep = deep;
    }

    public String getWorkId() {
        return workId;
    }

    public void setWorkId(String workId) {
        this.workId = workId;
    }


    public String getY2() {
        return y2;
    }

    public void setY2(String y2) {
        this.y2 = y2;
    }

    public String getY3() {
        return y3;
    }

    public void setY3(String y3) {
        this.y3 = y3;
    }


    public String getWbsName() {
        return wbsName;
    }

    public void setWbsName(String wbsName) {
        this.wbsName = wbsName;
    }

    public String getWbsACC() {
        return wbsACC;
    }

    public void setWbsACC(String wbsACC) {
        this.wbsACC = wbsACC;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public String getLiableUserNo() {
        return liableUserNo;
    }

    public void setLiableUserNo(String liableUserNo) {
        this.liableUserNo = liableUserNo;
    }

    public String getEvaluateUserNo() {
        return evaluateUserNo;
    }

    public void setEvaluateUserNo(String evaluateUserNo) {
        this.evaluateUserNo = evaluateUserNo;
    }

    public String getBeforeLiableUserNo() {
        return beforeLiableUserNo;
    }

    public void setBeforeLiableUserNo(String beforeLiableUserNo) {
        this.beforeLiableUserNo = beforeLiableUserNo;
    }

    public String getSubStage() {
        return subStage;
    }

    public void setSubStage(String subStage) {
        this.subStage = subStage;
    }

    public String getCoeHour() {
        return coeHour;
    }

    public void setCoeHour(String coeHour) {
        this.coeHour = coeHour;
    }

    public String getIsPrepareCard() {
        return isPrepareCard;
    }

    public void setIsPrepareCard(String isPrepareCard) {
        this.isPrepareCard = isPrepareCard;
    }

    public String getPrepareCardUser() {
        return prepareCardUser;
    }

    public void setPrepareCardUser(String prepareCardUser) {
        this.prepareCardUser = prepareCardUser;
    }

    public String getPrepareCardState() {
        return prepareCardState;
    }

    public void setPrepareCardState(String prepareCardState) {
        this.prepareCardState = prepareCardState;
    }

    public Date getPrepareCardTime() {
        return prepareCardTime;
    }

    public void setPrepareCardTime(Date prepareCardTime) {
        this.prepareCardTime = prepareCardTime;
    }

    public String getUnitControlPlan() {
        return unitControlPlan;
    }

    public void setUnitControlPlan(String unitControlPlan) {
        this.unitControlPlan = unitControlPlan;
    }

    public String getCriticalPath() {
        return criticalPath;
    }

    public void setCriticalPath(String criticalPath) {
        this.criticalPath = criticalPath;
    }

    public String getAnnualQualityPro() {
        return annualQualityPro;
    }

    public void setAnnualQualityPro(String annualQualityPro) {
        this.annualQualityPro = annualQualityPro;
    }

    public String getTYearActionPlan() {
        return TYearActionPlan;
    }

    public void setTYearActionPlan(String TYearActionPlan) {
        this.TYearActionPlan = TYearActionPlan;
    }

    public String getGGY() {
        return GGY;
    }

    public void setGGY(String GGY) {
        this.GGY = GGY;
    }

    public String getReQualityProblem() {
        return reQualityProblem;
    }

    public void setReQualityProblem(String reQualityProblem) {
        this.reQualityProblem = reQualityProblem;
    }

    public String getGoodMaintenance() {
        return goodMaintenance;
    }

    public void setGoodMaintenance(String goodMaintenance) {
        this.goodMaintenance = goodMaintenance;
    }

    public String getRemarksOne() {
        return remarksOne;
    }

    public void setRemarksOne(String remarksOne) {
        this.remarksOne = remarksOne;
    }

    public String getRemarksTwo() {
        return remarksTwo;
    }

    public void setRemarksTwo(String remarksTwo) {
        this.remarksTwo = remarksTwo;
    }

    public String getRemarksThir() {
        return remarksThir;
    }

    public void setRemarksThir(String remarksThir) {
        this.remarksThir = remarksThir;
    }

    public String getRemarksFour() {
        return remarksFour;
    }

    public void setRemarksFour(String remarksFour) {
        this.remarksFour = remarksFour;
    }

    public String getRemarksFif() {
        return remarksFif;
    }

    public void setRemarksFif(String remarksFif) {
        this.remarksFif = remarksFif;
    }
    public ModelPlan(){}

    public ModelPlan(String serialNumber, String type, String wbs, String wbsName, String wbsACC, String status, String source, String name,
                     String follower, String liableTeam, String liableUnit, String liableUser, String liableUserNo, String userOutunit, String userOutorg,
                     String startTime, String completeTime, String evaluateUser, String evaluateUserNo, String beforeLiableUser, String beforeLiableUserNo, String subStage, String coeHour,
                     String actualCompleteTime, String assessmentTime, String planWorkingHour, String labourHour, String isPrepareCard, String prepareCardUser, String prepareCardState,
                     String prepareCardTime, String ttgf, String superiorAttention, String projectControlZero, String projectControlOne, String unitControlPlan, String criticalPath,
                     String masterNode, String importmentTask, String annualQualityPro, String TYearActionPlan, String GGY, String reQualityProblem, String goodMaintenance, String remarksOne,
                     String remarksTwo, String remarksThir, String remarksFour, String remarksFif, String evolveState) {
        this.serialNumber = serialNumber;
        this.type = type;
        this.wbs = wbs;
        this.wbsName = wbsName;
        this.wbsACC = wbsACC;
        this.status = status;
        this.source = source;
        this.name = name;
        this.follower = follower;
        this.liableTeam = liableTeam;
        this.liableUnit = liableUnit;
        this.liableUser = liableUser;
        this.liableUserNo = liableUserNo;
        this.userOutunit = userOutunit;
        this.userOutorg = userOutorg;
        if(StringUtils.isNotBlank(startTime)){
            try {
                this.startTime = s2.parse(startTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(completeTime)){
            try {
                this.completeTime = s2.parse(completeTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.evaluateUser = evaluateUser;
        this.evaluateUserNo = evaluateUserNo;
        this.beforeLiableUser = beforeLiableUser;
        this.beforeLiableUserNo = beforeLiableUserNo;
        this.subStage = subStage;
        this.coeHour = coeHour;
        if(StringUtils.isNotBlank(actualCompleteTime)){
            try {
                this.actualCompleteTime = s2.parse(actualCompleteTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(StringUtils.isNotBlank(assessmentTime)){
            try {
                this.assessmentTime = s2.parse(assessmentTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.planWorkingHour = planWorkingHour;
        this.labourHour = labourHour;
        this.isPrepareCard = isPrepareCard;
        this.prepareCardUser = prepareCardUser;
        this.prepareCardState = prepareCardState;
        if(StringUtils.isNotBlank(prepareCardTime)){
            try {
                this.prepareCardTime = s2.parse(prepareCardTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.ttgf = ttgf;
        this.superiorAttention = superiorAttention;
        this.projectControlZero = projectControlZero;
        this.projectControlOne = projectControlOne;
        this.unitControlPlan = unitControlPlan;
        this.criticalPath = criticalPath;
        this.masterNode = masterNode;
        this.importmentTask = importmentTask;
        this.annualQualityPro = annualQualityPro;
        this.TYearActionPlan = TYearActionPlan;
        this.GGY = GGY;
        this.reQualityProblem = reQualityProblem;
        this.goodMaintenance = goodMaintenance;
        this.remarksOne = remarksOne;
        this.remarksTwo = remarksTwo;
        this.remarksThir = remarksThir;
        this.remarksFour = remarksFour;
        this.remarksFif = remarksFif;
        this.evolveState = evolveState;
    }
}
