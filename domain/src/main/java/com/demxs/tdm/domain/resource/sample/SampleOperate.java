package com.demxs.tdm.domain.resource.sample;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

/**
 * 样品各种操作
 * 入库 反库 归还等
 */
public class SampleOperate extends DataEntity<SampleOperate> {

    private String entrustId;//申请单id
    private String entrustCode;//申请单code
    private String entrustUserId;//申请人id
    private String entrustUserName;//申请人名称
    private String entrustOfficeId;//申请部门
    private String entrustOfficeName;//申请部门名称
    private String sampleId;//样品id
    private String sampleCode;//样品编码
    private String sampleNo;//样品流水号
    private String sampleName;//样品名称
    private String operaterId;//操作人 比如送样人 归还人等
    private String operaterName;//操作人 比如送样人 归还人名称等
    private String  locationId;//存放位置  入库的存放位置  反库的存放位置
    private String locationName;//位置名称
    private String taskCode;//任务编号
    private String  checkUserId;//试验工程师
    private String checkUserName;//试验工程师名称
    private String type;//操作类型  0:入库 1:出库试验  2:检毕入库 3:归还  4:试验室处理  数据字典
    private String tuHao;//图号
    private String miaoShu;//描述

    private String dateRange;//时间范围 页面传值
    private Date startDate; //开始时间  用户数据库查询
    private Date endDate;   //结束时间  用户数据库查询

    public String getEntrustId() {
        return entrustId;
    }

    public void setEntrustId(String entrustId) {
        this.entrustId = entrustId;
    }

    public String getEntrustCode() {
        return entrustCode;
    }

    public void setEntrustCode(String entrustCode) {
        this.entrustCode = entrustCode;
    }

    public String getEntrustUserId() {
        return entrustUserId;
    }

    public void setEntrustUserId(String entrustUserId) {
        this.entrustUserId = entrustUserId;
    }

    public String getEntrustUserName() {
        return entrustUserName;
    }

    public void setEntrustUserName(String entrustUserName) {
        this.entrustUserName = entrustUserName;
    }

    public String getEntrustOfficeId() {
        return entrustOfficeId;
    }

    public void setEntrustOfficeId(String entrustOfficeId) {
        this.entrustOfficeId = entrustOfficeId;
    }

    public String getEntrustOfficeName() {
        return entrustOfficeName;
    }

    public void setEntrustOfficeName(String entrustOfficeName) {
        this.entrustOfficeName = entrustOfficeName;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getSampleName() {
        return sampleName;
    }

    public void setSampleName(String sampleName) {
        this.sampleName = sampleName;
    }

    public String getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(String operaterId) {
        this.operaterId = operaterId;
    }

    public String getOperaterName() {
        return operaterName;
    }

    public void setOperaterName(String operaterName) {
        this.operaterName = operaterName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @JsonIgnore
    public String getDateRange() {
        return dateRange;
    }

    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }

    @JsonIgnore
    public Date getStartDate() {
        if(StringUtils.isNotBlank(dateRange)){
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[0]);
        }else{
            return null;
        }

    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonIgnore
    public Date getEndDate() {
        if(StringUtils.isNotBlank(dateRange)){
            String[] dateArr = dateRange.split(" - ");
            return DateUtils.parseDate(dateArr[1]);
        }else{
            return null;
        }
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getTuHao() {
        return tuHao;
    }

    public void setTuHao(String tuHao) {
        this.tuHao = tuHao;
    }

    public String getMiaoShu() {
        return miaoShu;
    }

    public void setMiaoShu(String miaoShu) {
        this.miaoShu = miaoShu;
    }

    public static SampleOperate copySampleOperate(String sampleId, String sampleCode, String sampleNo, String samlpleName,
                                                  String operaterId, String operaterName, String entrustId,
                                                  String entrustCode, String entrustOfficeId, String entrustOfficeName,
                                                  String entrustUserId, String entrustUserName, String type,
                                                  String checkUserId, String checkUserName,
                                                  String taskCode, String locationId, String locationName, String labInfoId, String labInfoName){

        SampleOperate sampleOperate = new SampleOperate();
        sampleOperate.setSampleId(sampleId);
        sampleOperate.setSampleCode(sampleCode);
        sampleOperate.setSampleName(samlpleName);
        sampleOperate.setOperaterId(operaterId);
        sampleOperate.setOperaterName(operaterName);
        sampleOperate.setEntrustId(entrustId);
        sampleOperate.setEntrustCode(entrustCode);
        sampleOperate.setEntrustOfficeId(entrustOfficeId);
        sampleOperate.setEntrustOfficeName(entrustOfficeName);
        sampleOperate.setEntrustUserId(entrustUserId);
        sampleOperate.setEntrustUserName(entrustUserName);
        sampleOperate.setType(type);
        sampleOperate.setCheckUserId(checkUserId);
        sampleOperate.setCheckUserName(checkUserName);
        sampleOperate.setTaskCode(taskCode);
        sampleOperate.setLocationId(locationId);
        sampleOperate.setLocationName(locationName);
        sampleOperate.setSampleNo(sampleNo);
        sampleOperate.setLabInfoId(labInfoId);
        sampleOperate.setLabInfoName(labInfoName);
        return sampleOperate;
    }
}
