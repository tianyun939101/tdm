package com.demxs.tdm.domain.business.nostandard;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.excel.anno.ExcelField;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 14:05
 * @Description:
 */
public class NoStandardExecutionItem extends DataEntity<NoStandardExecutionItem> {

    private static final long serialVersionUID = 1L;
    //申请单id
    private String entrustId;
    //试验项目
    private String testItemId;
    //执行单id
    private String executionId;
    //排序
    private String sort;
    //原始记录单
    private String report;
    //图片数据
    private String img;
    //试验数据（处理后）
    private String file;
    /**
     * 视频数据
     */
    private String videoData;
    /**
     * 音频数据
     */
    private String audioData;
    //状态
    @ExcelField(title = "试验状态",sort = 0,dictType = "no_standard_execution_item_status")
    private String status;
    //当前操作人
    @ExcelField(title = "执行人",sort = 2)
    private String opUser;

    //试验项目
    @ExcelField(title = "试验名称",sort = 1,call = "name")
    private TestItem testItem;
    //审核信息
    private List<AuditInfo> auditInfoList;

    //视图传递对象
    private AuditInfo auditInfo;

    public NoStandardExecutionItem(){
        super();
    }

    public NoStandardExecutionItem(String id){
        super(id);
    }

    public String getEntrustId() {
        return entrustId;
    }

    public NoStandardExecutionItem setEntrustId(String entrustId) {
        this.entrustId = entrustId;
        return this;
    }

    public String getTestItemId() {
        return testItemId;
    }

    public NoStandardExecutionItem setTestItemId(String testItemId) {
        this.testItemId = testItemId;
        return this;
    }

    public String getExecutionId() {
        return executionId;
    }

    public NoStandardExecutionItem setExecutionId(String executionId) {
        this.executionId = executionId;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public NoStandardExecutionItem setSort(String sort) {
        this.sort = sort;
        return this;
    }

    public String getReport() {
        return report;
    }

    public NoStandardExecutionItem setReport(String report) {
        this.report = report;
        return this;
    }

    public String getImg() {
        return img;
    }

    public NoStandardExecutionItem setImg(String img) {
        this.img = img;
        return this;
    }

    public String getFile() {
        return file;
    }

    public NoStandardExecutionItem setFile(String file) {
        this.file = file;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public NoStandardExecutionItem setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getOpUser() {
        return opUser;
    }

    public NoStandardExecutionItem setOpUser(String opUser) {
        this.opUser = opUser;
        return this;
    }

    public TestItem getTestItem() {
        return testItem;
    }

    public NoStandardExecutionItem setTestItem(TestItem testItem) {
        this.testItem = testItem;
        return this;
    }

    public List<AuditInfo> getAuditInfoList() {
        return auditInfoList;
    }

    public NoStandardExecutionItem setAuditInfoList(List<AuditInfo> auditInfoList) {
        this.auditInfoList = auditInfoList;
        return this;
    }

    public AuditInfo getAuditInfo() {
        return auditInfo;
    }

    public NoStandardExecutionItem setAuditInfo(AuditInfo auditInfo) {
        this.auditInfo = auditInfo;
        return this;
    }

    public String getVideoData() {
        return videoData;
    }

    public NoStandardExecutionItem setVideoData(String videoData) {
        this.videoData = videoData;
        return this;
    }

    public String getAudioData() {
        return audioData;
    }

    public NoStandardExecutionItem setAudioData(String audioData) {
        this.audioData = audioData;
        return this;
    }
}
