package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.excel.anno.ExcelField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 *非标试验问题
 */
public class NoStandardProblem extends DataEntity<NoStandardProblem> {

    private static final long serialVersionUID = 1L;

    private String executionId;
    //受理平台
    @ExcelField(title = "受理问题平台",sort = 0,dictType = "no_standard_execution_problem_acceptance")
    private String acceptance;
    //提报人
    @ExcelField(title = "提报人",sort = 1)
    private String commitUser;
    //提报时间
    @ExcelField(title = "提报时间",sort = 2)
    private Date commitDate;
    //问题名称
    @ExcelField(title = "问题名称",sort = 3)
    private String problemName;
    //详细描述
    @ExcelField(title = "详细描述",sort = 4)
    private String information;
    //问题链接
    @ExcelField(title = "问题链接",sort = 5)
    private String problemPath;

    public String getProblemPath() {
        return problemPath;
    }

    public void setProblemPath(String problemPath) {
        this.problemPath = problemPath;
    }

    public NoStandardProblem(){
        super();
    }

    public NoStandardProblem(String id){
        super(id);
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public String getCommitUser() {
        return commitUser;
    }

    public void setCommitUser(String commitUser) {
        this.commitUser = commitUser;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

}
