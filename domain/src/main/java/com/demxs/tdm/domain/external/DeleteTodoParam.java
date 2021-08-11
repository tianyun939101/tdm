package com.demxs.tdm.domain.external;

import com.github.ltsopensource.core.json.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 删除待办任务参数
 * User: wuliepeng
 * Date: 2017-11-30
 * Time: 上午9:50
 */
public class DeleteTodoParam {
    /**
     * 待办来源,标识待办来源的系统
     */
    private String appName;

    /**
     * 模块名,标识待办来源的模块
     */
    private String modelName;

    /**
     * 待办唯一标识,标识待办在原系统唯一标识
     */
    private String modelId;

    /**
     * 待办类型,1:表示审批类待办 2:表示为通知类待办
     */
    private Integer type;

    /**
     * 关键字,待办关键字，用于区分同一文档下不同类型待办， 如:会议文档的抄送待办和与会人参加待办属于同一文档的不同类型的待办。
     */
    private String key;

    /**
     * 待办所属对象,待办对应接收人，数据格式为JSON，格式描述请查看"1.2.5.1组织架构数据说明"
     */
    private String targets;

    /**
     * 扩展参数
     */
    private String others;


    /**
     * 扩展参数1
     */
    protected String param1;

    /**
     * 扩展参数2
     */
    protected String param2;

    private List<Target> userIdList = new ArrayList<>();

    public DeleteTodoParam(){
        this.appName = "lims";
        this.type = 1;
        this.modelName = "lims";
    }

    public void addTarget(String id){
        Target target = new Target();
        target.LoginName = id;
        if(this.userIdList.contains(target)){
            return ;
        }
        this.userIdList.add(target);
        this.targets = JSON.toJSONString(this.userIdList);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTargets() {
        return targets;
    }

    public void setTargets(String targets) {
        this.targets = targets;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    static class Target{
        public String id;
        public String PersonNo;
        public String DeptNo;
        public String PostNo;
        public String GroupNo;
        public String LoginName;
        public String Keyword;
        public String LdapDN;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Target)) return false;

            Target target = (Target) o;

            return LoginName.equals(target.LoginName);
        }

        @Override
        public int hashCode() {
            return LoginName.hashCode();
        }

        /*public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPersonNo() {
            return PersonNo;
        }

        public void setPersonNo(String personNo) {
            PersonNo = personNo;
        }

        public String getDeptNo() {
            return DeptNo;
        }

        public void setDeptNo(String deptNo) {
            DeptNo = deptNo;
        }

        public String getPostNo() {
            return PostNo;
        }

        public void setPostNo(String postNo) {
            PostNo = postNo;
        }

        public String getGroupNo() {
            return GroupNo;
        }

        public void setGroupNo(String groupNo) {
            GroupNo = groupNo;
        }

        public String getLoginName() {
            return LoginName;
        }

        public void setLoginName(String loginName) {
            LoginName = loginName;
        }

        public String getKeyword() {
            return Keyword;
        }

        public void setKeyword(String keyword) {
            Keyword = keyword;
        }

        public String getLdapDN() {
            return LdapDN;
        }

        public void setLdapDN(String ldapDN) {
            LdapDN = ldapDN;
        }*/
    }
}
