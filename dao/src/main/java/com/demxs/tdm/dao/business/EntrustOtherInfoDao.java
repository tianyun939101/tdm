package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustOtherInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 申请其他信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustOtherInfoDao extends CrudDao<EntrustOtherInfo> {

    /**
     * 根据申请单加载申请单其他信息
     * @param entrustId 申请单ID
     * @return
     */
    EntrustOtherInfo getByEntrustId(@Param("entrustId")String entrustId);
}