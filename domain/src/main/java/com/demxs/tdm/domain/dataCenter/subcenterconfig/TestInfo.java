package com.demxs.tdm.domain.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabVideoEquipment;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 15:47
 * @Description:分中心大屏配置试验信息
 */
public class TestInfo extends DataEntity<TestInfo> {

    private static final long serialVersionUID = 1L;

    //大屏标题
    private String title;
    /**
     * 简介
     */
    private String summary;
    /**
     * 试验室id
     */
    private String labId;
    /**
     * 试验任务id
     */
    private String testTaskId;
    /**
     * 执行人
     */
    private String opUser;
    /**
     * 参试人员
     */
    private String takers;
    private String[] testTakers;

    //大屏显示
    private String show;
    //序号
    private Integer seq;

    //远程服务器
    private List<IPC> ipcs;
    //视频监控
    private List<LabVideoEquipment> videos;

    public TestInfo() {
    }

    public TestInfo(String id) {
        super(id);
    }

    public String getSummary() {
        return summary;
    }

    public TestInfo setSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public TestInfo setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getOpUser() {
        return opUser;
    }

    public TestInfo setOpUser(String opUser) {
        this.opUser = opUser;
        return this;
    }

    public String getTakers() {
        return takers;
    }

    public TestInfo setTakers(String takers) {
        this.takers = takers;
        if(null != takers){
            if(takers.contains(",")){
                this.testTakers = takers.split(",");
            }else if(takers.contains("，")){
                this.testTakers = takers.split("，");
            }else{
                this.testTakers = takers.split(",");
            }
        }
        return this;
    }

    public String getTestTaskId() {
        return testTaskId;
    }

    public TestInfo setTestTaskId(String testTaskId) {
        this.testTaskId = testTaskId;
        return this;
    }

    public String[] getTestTakers() {
        return testTakers;
    }

    public TestInfo setTestTakers(String[] testTakers) {
        this.testTakers = testTakers;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public List<IPC> getIpcs() {
        return ipcs;
    }

    public void setIpcs(List<IPC> ipcs) {
        this.ipcs = ipcs;
    }

    public List<LabVideoEquipment> getVideos() {
        return videos;
    }

    public void setVideos(List<LabVideoEquipment> videos) {
        this.videos = videos;
    }
}
