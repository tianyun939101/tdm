package com.demxs.tdm.domain.resource.kowledge;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.resource.center.Department;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 编制计划统计
 */
public class KnowledgeConnect extends DataEntity<KnowledgeConnect> {
    /**
     * 编制计划
     */

    private String name;

        /**
         * 编制计划
         */
    @JsonFormat(pattern = "yyyy-mm-dd")
        private String plan;
        /**
         * 已发布
         */
        private String havePush;
        /**
         * 待编
         */
            private String makeUp;
        /**
         * 待修订
         */
            private String  beRevised;
        /**
         * 总数/统计
         */
        private int  planConnect;
    /**
     * 待编+待修订
     */
    private int  beUp;









    //模块名称
    private String moduleName;
    //类型
    private String type;
    //名称
    private String nameAdd;
    //时间-编制计划
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date time;
    //责任人
    private User user;
    private String userId;
    //科室
    private Department office;
    private String officeId;
    //数量
    private String num;

    //审批状态
    private String status;
    //发起人/最后审批人
    private User userStart;
    private String userStartId;
    //下一节点审批人
    private String authorizedId;
    private User authorizedMinUser;

    private String beloneId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlanConnect() {
        return planConnect;
    }

    public void setPlanConnect(int planConnect) {
        this.planConnect = planConnect;
    }

    public int getBeUp() {
        return beUp;
    }

    public void setBeUp(int beUp) {
        this.beUp = beUp;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getHavePush() {
        return havePush;
    }

    public void setHavePush(String havePush) {
        this.havePush = havePush;
    }

    public String getMakeUp() {
        return makeUp;
    }

    public void setMakeUp(String makeUp) {
        this.makeUp = makeUp;
    }

    public String getBeRevised() {
        return beRevised;
    }

    public void setBeRevised(String beRevised) {
        this.beRevised = beRevised;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNameAdd() {
        return nameAdd;
    }

    public void setNameAdd(String nameAdd) {
        this.nameAdd = nameAdd;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Department getOffice() {
        return office;
    }

    public void setOffice(Department office) {
        this.office = office;
    }

    public String getOfficeId() {
        return officeId;
    }

    public void setOfficeId(String officeId) {
        this.officeId = officeId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUserStart() {
        return userStart;
    }

    public void setUserStart(User userStart) {
        this.userStart = userStart;
    }

    public String getUserStartId() {
        return userStartId;
    }

    public void setUserStartId(String userStartId) {
        this.userStartId = userStartId;
    }

    public String getAuthorizedId() {
        return authorizedId;
    }

    public void setAuthorizedId(String authorizedId) {
        this.authorizedId = authorizedId;
    }

    public User getAuthorizedMinUser() {
        return authorizedMinUser;
    }

    public void setAuthorizedMinUser(User authorizedMinUser) {
        this.authorizedMinUser = authorizedMinUser;
    }

    public String getBeloneId() {
        return beloneId;
    }

    public void setBeloneId(String beloneId) {
        this.beloneId = beloneId;
    }
}
