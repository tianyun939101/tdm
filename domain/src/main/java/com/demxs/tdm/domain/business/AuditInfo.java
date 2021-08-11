package com.demxs.tdm.domain.business;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;

/**
 * 审核信息
 * User: wuliepeng
 * Date: 2017-11-08
 * Time: 上午11:32
 */
public class AuditInfo extends DataEntity<AuditInfo> {
    private static final long serialVersionUID = 1L;

    private String businessKey;    //业务主键
    private User auditUser;   //审核用户
    private Integer result; //审核结果
    private String reason;  //审核意见
    private Integer type;   //审核类型

    public AuditInfo() {
        super();
    }

    public AuditInfo(String businessKey, User auditUser, Integer result, String reason) {
        this.businessKey = businessKey;
        this.auditUser = auditUser;
        this.result = result;
        this.reason = reason;
    }

    public AuditInfo(String id){
        super(id);
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public User getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(User auditUser) {
        this.auditUser = auditUser;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
