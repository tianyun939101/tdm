package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;

import java.util.List;

public class PlanConnect  extends DataEntity<PlanConnect> {

    private static final long serialVersionUID = 1L;
    //状态
    //已完成
    private String finished;
    //执行中
    private String inExecution;
    //已响应
    private String response;
    //计划总量
    private String count;
    //责任人
    private String liableUserId;
    private User liableUser;
    //责任主体
    private String liableBody;
    //计划总量
    private String planGross;
    //完成率
    private double percentageComplete;
    //计划工时
    private String planWorkingHour;
    //实际工时
    private String praWorkingHour;

    public String getPlanWorkingHour() {
        return planWorkingHour;
    }

    public void setPlanWorkingHour(String planWorkingHour) {
        this.planWorkingHour = planWorkingHour;
    }

    public String getPraWorkingHour() {
        return praWorkingHour;
    }

    public void setPraWorkingHour(String praWorkingHour) {
        this.praWorkingHour = praWorkingHour;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getInExecution() {
        return inExecution;
    }

    public void setInExecution(String inExecution) {
        this.inExecution = inExecution;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getLiableUserId() {
        return liableUserId;
    }

    public void setLiableUserId(String liableUserId) {
        this.liableUserId = liableUserId;
    }

    public User getLiableUser() {
        return liableUser;
    }

    public void setLiableUser(User liableUser) {
        this.liableUser = liableUser;
    }

    public String getLiableBody() {
        return liableBody;
    }

    public void setLiableBody(String liableBody) {
        this.liableBody = liableBody;
    }

    public String getPlanGross() {
        return planGross;
    }

    public void setPlanGross(String planGross) {
        this.planGross = planGross;
    }

    public double getPercentageComplete() {
        return percentageComplete;
    }

    public void setPercentageComplete(double percentageComplete) {
        this.percentageComplete = percentageComplete;
    }

    public PlanConnect() {
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
