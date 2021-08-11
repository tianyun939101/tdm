package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataReportDownloadHistory;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/15 11:40
 * @Description:
 */
@MyBatisDao
public interface DataReportDownloadHistoryDao extends CrudDao<DataReportDownloadHistory> {

    /**
    * @author Jason
    * @date 2020/7/15 11:41
    * @params [id]
    * 根据数据提报id查询下载记录
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.DataReportDownloadHistory>
    */
    List<DataReportDownloadHistory> findByTestId(String id);
}
