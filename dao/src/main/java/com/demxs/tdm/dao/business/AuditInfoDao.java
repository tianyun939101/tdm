package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.AuditInfo;

import java.util.List;

/**
 * 审核信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface AuditInfoDao extends CrudDao<AuditInfo> {

    AuditInfo getByType(AuditInfo auditInfo);

    List<AuditInfo> getByKey(String businessKey);
}