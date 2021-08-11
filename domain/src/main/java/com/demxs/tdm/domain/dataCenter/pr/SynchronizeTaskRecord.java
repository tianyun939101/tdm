package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.DataEntity;

/**
 * @author: Jason
 * @Date: 2020/7/8 13:48
 * @Description:同步任务记录
 */
public class SynchronizeTaskRecord extends DataEntity<SynchronizeTaskRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 同步任务id
     */
    private String taskId;
    /**
     * 同步信息
     */
    private String info;
    /**
     * 同步信息：0失败 1：成功 2：跳过
     */
    private String status;
    public final static String FAILED = "0";
    public final static String SUCCESS = "1";
    public final static String SKIP = "2";

    public SynchronizeTaskRecord() {
    }

    public SynchronizeTaskRecord(String id) {
        super(id);
    }

    public String getTaskId() {
        return taskId;
    }

    public SynchronizeTaskRecord setTaskId(String taskId) {
        this.taskId = taskId;
        return this;
    }

    public String getInfo() {
        return info;
    }

    public SynchronizeTaskRecord setInfo(String info) {
        this.info = info;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SynchronizeTaskRecord setStatus(String status) {
        this.status = status;
        return this;
    }
}
