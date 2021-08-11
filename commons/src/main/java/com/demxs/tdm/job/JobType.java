package com.demxs.tdm.job;

/**
 * 任务类型
 * User: wuliepeng
 * Date: 2017-08-06
 * Time: 下午3:21
 */
public enum JobType {
    DocConvert("doc-convert","doc-tasktracker"),  //文档转换

    SendMessage("send_message","doc-tasktracker"),  //发送消息

    HandleMessage("handle-message","doc-tasktracker"),//处理信息

    SyncOffice("sync_office","doc-tasktracker"),//同步组织

    SyncUser("sync_user","doc-tasktracker");//同步用户
    private String type;
    private String taskTracker;

    JobType(String type,String taskTracker){
        this.type = type;
        this.taskTracker = taskTracker;
    }

    public String getType() {
        return type;
    }

    public String getTaskTracker() {
        return taskTracker;
    }
}
