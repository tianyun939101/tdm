package com.demxs.tdm.domain.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.domain.lab.LabVideoEquipment;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 15:40
 * @Description: 分中心大屏配置试验室列表
 */
public class Lab extends DataEntity<Lab> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验室名称
     */
    private String name;
    /**
     * 试验任务列表
     */
    private List<SubCenterTestTask> testTaskList;
    /**
     * 视频通道
     */
    private List<LabVideoEquipment> videoEquipmentList;

    public Lab() {
    }

    public Lab(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public Lab setName(String name) {
        this.name = name;
        return this;
    }

    public List<SubCenterTestTask> getTestTaskList() {
        return testTaskList;
    }

    public Lab setTestTaskList(List<SubCenterTestTask> testTaskList) {
        this.testTaskList = testTaskList;
        return this;
    }

    public List<LabVideoEquipment> getVideoEquipmentList() {
        return videoEquipmentList;
    }

    public Lab setVideoEquipmentList(List<LabVideoEquipment> videoEquipmentList) {
        this.videoEquipmentList = videoEquipmentList;
        return this;
    }
}
