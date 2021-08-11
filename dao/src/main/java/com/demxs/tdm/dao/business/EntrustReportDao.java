package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustReport;
import org.apache.ibatis.annotations.Param;

/**
 * 申请其他信息DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface EntrustReportDao extends CrudDao<EntrustReport> {
    /**
     * 根据申请单加载申请单报告
     * @param entrustId 申请单ID
     * @return
     */
    EntrustReport getByEntrustId(@Param("entrustId")String entrustId);

    /**
     * 报告上传
     * @param id
     * @param fileId
     */
    void uploadReport(@Param("id")String id, @Param("fileId")String fileId);
}