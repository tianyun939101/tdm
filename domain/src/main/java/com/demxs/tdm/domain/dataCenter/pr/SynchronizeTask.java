package com.demxs.tdm.domain.dataCenter.pr;

import com.demxs.tdm.common.persistence.DataEntity;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/8 13:47
 * @Description:同步任务实体类
 */
public class SynchronizeTask extends DataEntity<SynchronizeTask> {

    private static final long serialVersionUID = 1L;

    /**
     * 命名空间：0：试飞中心，1、2、3：阎良，4：东营，5：上海
     */
    private String nameSpace;
    /**
     * 名称
     */
    private String name;
    /**
     * 全路径
     */
    private String path;
    /**
     * 类型 0：文件 1：文件夹
     */
    private String type;
    public final static String DIR = "Directory";
    public final static String FILE = "File";
    /**
     * 关联定时任务集合
     */
    private List<SynchronizeTaskRecord> taskRecordList;
    /**
     * 视图传输字段
     */
    private String dataId;

    public SynchronizeTask() {
    }

    public SynchronizeTask(String id) {
        super(id);
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public SynchronizeTask setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
        return this;
    }

    public String getName() {
        return name;
    }

    public SynchronizeTask setName(String name) {
        this.name = name;
        return this;
    }

    public String getPath() {
        return path;
    }

    public SynchronizeTask setPath(String path) {
        this.path = path;
        return this;
    }

    public String getType() {
        return type;
    }

    public SynchronizeTask setType(String type) {
        this.type = type;
        return this;
    }

    public List<SynchronizeTaskRecord> getTaskRecordList() {
        return taskRecordList;
    }

    public SynchronizeTask setTaskRecordList(List<SynchronizeTaskRecord> taskRecordList) {
        this.taskRecordList = taskRecordList;
        return this;
    }

    public String getDataId() {
        return dataId;
    }

    public SynchronizeTask setDataId(String dataId) {
        this.dataId = dataId;
        return this;
    }

    public boolean isDirectory(){
        return DIR.equals(type);
    }

    public boolean isFile(){
        return FILE.equals(type);
    }

    public static String getDIR() {
        return DIR;
    }

    public static String getFILE() {
        return FILE;
    }
}
