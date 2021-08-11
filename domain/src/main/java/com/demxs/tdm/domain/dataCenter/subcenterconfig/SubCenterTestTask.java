package com.demxs.tdm.domain.dataCenter.subcenterconfig;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 15:47
 * @Description:分中心大屏配置试验任务列表
 */
public class SubCenterTestTask extends DataEntity<SubCenterTestTask> {

    private static final long serialVersionUID = 1L;
    /**
     * 试验任务名称
     */
    private String name;
    /**
     * 试验室id
     */
    private String labId;
    /**
     * 当前执行人
     */
    private String opUser;
    /**
     * 试验信息
     */
    private TestInfo testInfo;
    /**
     * 工控机通道
     */
    private List<IPC> ipcList;
    /**
     * 流程
     */
    private String process;
    private String[] processList;

    public SubCenterTestTask() {
    }

    public SubCenterTestTask(String id) {
        super(id);
    }

    public String getName() {
        return name;
    }

    public SubCenterTestTask setName(String name) {
        this.name = name;
        return this;
    }

    public String getLabId() {
        return labId;
    }

    public SubCenterTestTask setLabId(String labId) {
        this.labId = labId;
        return this;
    }

    public String getOpUser() {
        return opUser;
    }

    public SubCenterTestTask setOpUser(String opUser) {
        this.opUser = opUser;
        return this;
    }

    public TestInfo getTestInfo() {
        return testInfo;
    }

    public SubCenterTestTask setTestInfo(TestInfo testInfo) {
        this.testInfo = testInfo;
        return this;
    }

    public List<IPC> getIpcList() {
        return ipcList;
    }

    public SubCenterTestTask setIpcList(List<IPC> ipcList) {
        this.ipcList = ipcList;
        return this;
    }

    public String getProcess() {
        return process;
    }

    public SubCenterTestTask setProcess(String process) {
        this.process = process;
        if(null != process){
            if(process.contains("，")){
                this.processList = process.split("，");
            }else{
                this.processList = process.split(",");
            }
        }
        return this;
    }

    public String[] getProcessList() {
        return processList;
    }

    public SubCenterTestTask setProcessList(String[] processList) {
        this.processList = processList;
        return this;
    }
}
