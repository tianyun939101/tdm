package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.excel.annotation.ExcelField;
import com.demxs.tdm.domain.lab.LabInfo;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PeopleQualification extends DataEntity<PeopleQualification> {

    private static final long serialVersionUID = 1L;
    private String seiNum;
    /**
     * 姓名
     */
    private String name;
    /**
     *工号
     */
    private String no;
    /**
     *性别
     */
    private String sex;
    /**
     *身份证号
     */
    private String idCard;
    /**
     *证件编号
     */
    private String papersCode;
    /**
     *作业项目代号
     */
    private String wordCode;
    /**
     *科室
     */
    private LabInfo labInfo;
    private String labInfoId;
    /**
     *第一次取证批准日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date firstRatify;
    /**
     *当前有效日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date periodValidity;
    /**
     *复审记录
     */
    private String reviewRecords;

    private String subCenter;



    public String getSeiNum() {
        return seiNum;
    }
    public void setSeiNum(String seiNum) {
        this.seiNum = seiNum;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdCard() {
        return idCard;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPapersCode() {
        return papersCode;
    }
    public void setPapersCode(String papersCode) {
        this.papersCode = papersCode;
    }

    public String getWordCode() {
        return wordCode;
    }
    public void setWordCode(String wordCode) {
        this.wordCode = wordCode;
    }

    public LabInfo getLabInfo() {
        return labInfo;
    }

    public void setLabInfo(LabInfo labInfo) {
        this.labInfo = labInfo;
    }

    @Override
    public String getLabInfoId() {
        return labInfoId;
    }
    @Override
    public void setLabInfoId(String labInfoId) {
        this.labInfoId = labInfoId;
    }

    public Date getFirstRatify() {
        return firstRatify;
    }
    public void setFirstRatify(Date firstRatify) {
        this.firstRatify = firstRatify;
    }
    public Date getPeriodValidity() {
        return periodValidity;
    }
    public void setPeriodValidity(Date periodValidity) {
        this.periodValidity = periodValidity;
    }
    public String getReviewRecords() {
        return reviewRecords;
    }
    public void setReviewRecords(String reviewRecords) {
        this.reviewRecords = reviewRecords;
    }

    public String getSubCenter() {
        return subCenter;
    }

    public void setSubCenter(String subCenter) {
        this.subCenter = subCenter;
    }
}
