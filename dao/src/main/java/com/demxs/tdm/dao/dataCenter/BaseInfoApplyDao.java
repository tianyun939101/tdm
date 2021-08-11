package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.BaseInfoApply;

/**
 * 试验数据下载申请DAO
 */
@MyBatisDao
public interface BaseInfoApplyDao extends CrudDao<BaseInfoApply> {

       int getDownLoadAhory(BaseInfoApply  baseInfoApply);

       void updateDelete(BaseInfoApply  baseInfoApply);
}