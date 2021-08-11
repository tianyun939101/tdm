package com.demxs.tdm.domain.external;

import com.demxs.tdm.common.utils.DateUtils;

import java.util.Date;

/**
 * 待办任务参数
 * User: wuliepeng
 * Date: 2017-11-30
 * Time: 上午9:50
 */
public class SendTodoParam extends DeleteTodoParam{
//    /**
//     * 待办来源,标识待办来源的系统
//     */
//    private String appName;
//
//    /**
//     * 模块名,标识待办来源的模块
//     */
//    private String modelName;
//
//    /**
//     * 待办唯一标识,标识待办在原系统唯一标识
//     */
//    private String modelId;

    /**
     * 标题,待办标题
     */
    private String subject;

    /**
     * 链接,对应待办的链接地址(全路径)
     */
    private String link;

    /**
     * 待办类型,1:表示审批类待办 2:表示为通知类待办
     */
//    private Integer type;

    /**
     * 关键字,待办关键字，用于区分同一文档下不同类型待办， 如:会议文档的抄送待办和与会人参加待办属于同一文档的不同类型的待办。
     */
//    private String key;


    /**
     * 创建时间,格式为:yyyy-MM-ddHH:mm:ss
     */
    private String createTime;

    /**
     * 待办创建者
     */
    private String docCreator;

    /**
     * 待办优先级,待办优先级。如：按紧急（1）、急（2）、一般（3）。
     */
    private String level;

    /**
     * 消息内容扩展,数据格式为JSON
     */
    private String extendContent;

    /**
     * 扩展参数
     */
//    private String others;

    /**
     * 扩展参数1
     */
//    protected String param1;

    /**
     * 扩展参数2
     */
//    protected String param2;



    public SendTodoParam(){
        super();
//        this.appName = "lims";
        this.level = "1";
//        this.type = 1;
//        this.modelName = "lims";
    }


//    public String getAppName() {
//        return appName;
//    }
//
//    public void setAppName(String appName) {
//        this.appName = appName;
//    }
//
//    public String getModelName() {
//        return modelName;
//    }
//
//    public void setModelName(String modelName) {
//        this.modelName = modelName;
//    }
//
//    public String getModelId() {
//        return modelId;
//    }
//
//    public void setModelId(String modelId) {
//        this.modelId = modelId;
//    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

//    public Integer getType() {
//        return type;
//    }
//
//    public void setType(Integer type) {
//        this.type = type;
//    }

//    public String getKey() {
//        return key;
//    }
//
//    public void setKey(String key) {
//        this.key = key;
//    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public void setCreateTime(Date createTime) {
        if (createTime == null) {
            this.createTime = "";
        }
        this.createTime = DateUtils.formatDate(createTime,"yyyy-MM-dd HH:mm:ss");
    }

    public String getDocCreator() {
        return docCreator;
    }

    public void setDocCreator(String docCreator) {
        this.docCreator = docCreator;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getExtendContent() {
        return extendContent;
    }

    public void setExtendContent(String extendContent) {
        this.extendContent = extendContent;
    }

//    public String getOthers() {
//        return others;
//    }
//
//    public void setOthers(String others) {
//        this.others = others;
//    }

//    public String getParam1() {
//        return param1;
//    }
//
//    public void setParam1(String param1) {
//        this.param1 = param1;
//    }
//
//    public String getParam2() {
//        return param2;
//    }
//
//    public void setParam2(String param2) {
//        this.param2 = param2;
//    }


}
