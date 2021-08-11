package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * 数据中心试验项目实体类
 */
public class DataTestInfo extends DataEntity<DataTestInfo>{

    private static final long serialVersionUID = 1L;

    private String baseId;//基础信息ID

    private String testItemId;//试验项目ID

    private String testItemName;//试验项目名称

    private String other;//其他信息

    private String labLeader;//试验室负责人

    private String testLeader;//试验负责人
    private String tester;//试验人
    private User testUser; //试验人员
    private String testAddress;//试验地点
    private Date testDate;    //试验日期

    private String timeRange;

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;//开始时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;//结束时间

    private String reviewer;//审核人

    private String reviewerResult;//审核结果

    private String logId;//试验日志ID

    private String originalRecord;//原始记录单

    private String testData;//试验数据（处理后）

    private String testLog;//试验日志

    private String videoData;//视频数据

    private String audioData;//音频数据

    private String imgData;//图片数据

    private List<DataInfo> datalist;//试验数据信息

    private TestItem testItem;

    private List<AttachmentInfo> imgList;//图片对象

    private List<AttachmentInfo> videoList;//图片对象

    private List<AttachmentInfo> audioList;//图片对象

    private List<TestInfoAttrValue> fields;//扩展字段

    public DataTestInfo() {
    }

    public DataTestInfo(DataReportXml.Task task) {
        this.testItemId = task.getTestItem();
        this.tester = task.getTester();
        this.testAddress = task.getTestAddress();
       // this.testDate = task.getTestDate();
        this.timeRange=task.getTimeRange();
        this.remarks = task.getRemarks();
        this.testItemName=task.getTestItemName();
        this.other=task.getOther();
    }

    public DataTestInfo(DataServerXml.Task task) {
        this.testItemId = task.getTestItem();
        this.tester = task.getTester();
        this.testAddress = task.getTestAddress();
      //  this.testDate = task.getTestDate();
        this.timeRange=task.getTimeRange();
        this.remarks = task.getRemarks();
    }

    public String getBaseId() {
        return baseId;
    }

    public void setBaseId(String baseId) {
        this.baseId = baseId;
    }

    public String getTestItemId() {
        return testItemId;
    }

    public void setTestItemId(String testItemId) {
        this.testItemId = testItemId;
    }

    public String getTestItemName() {
        return testItemName;
    }

    public void setTestItemName(String testItemName) {
        this.testItemName = testItemName;
    }

    public String getLabLeader() {
        return labLeader;
    }

    public void setLabLeader(String labLeader) {
        this.labLeader = labLeader;
    }

    public String getTestLeader() {
        return testLeader;
    }

    public void setTestLeader(String testLeader) {
        this.testLeader = testLeader;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewerResult() {
        return reviewerResult;
    }

    public void setReviewerResult(String reviewerResult) {
        this.reviewerResult = reviewerResult;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public List<DataInfo> getDatalist() {
        return datalist;
    }

    public void setDatalist(List<DataInfo> datalist) {
        this.datalist = datalist;
    }

    public String getOriginalRecord() {
        return originalRecord;
    }

    public void setOriginalRecord(String originalRecord) {
        this.originalRecord = originalRecord;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getTestLog() {
        return testLog;
    }

    public void setTestLog(String testLog) {
        this.testLog = testLog;
    }

    public String getVideoData() {
        return videoData;
    }

    public void setVideoData(String videoData) {
        this.videoData = videoData;
    }

    public String getAudioData() {
        return audioData;
    }

    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }

    public String getImgData() {
        return imgData;
    }

    public void setImgData(String imgData) {
        this.imgData = imgData;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public void setTestItem(TestItem testItem) {
        this.testItem = testItem;
    }

    public List<AttachmentInfo> getImgList() {
        return imgList;
    }

    public void setImgList(List<AttachmentInfo> imgList) {
        this.imgList = imgList;
    }

    public List<AttachmentInfo> getVideoList() {
        return videoList;
    }

    public void setVideoList(List<AttachmentInfo> videoList) {
        this.videoList = videoList;
    }

    public List<AttachmentInfo> getAudioList() {
        return audioList;
    }

    public void setAudioList(List<AttachmentInfo> audioList) {
        this.audioList = audioList;
    }

    public String getTestAddress() {
        return testAddress;
    }

    public void setTestAddress(String testAddress) {
        this.testAddress = testAddress;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }


    public List<TestInfoAttrValue> getFields() {
        return fields;
    }

    public void setFields(List<TestInfoAttrValue> fields) {
        this.fields = fields;
    }

    public User getTestUser() {
        return testUser;
    }

    public void setTestUser(User testUser) {
        this.testUser = testUser;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}