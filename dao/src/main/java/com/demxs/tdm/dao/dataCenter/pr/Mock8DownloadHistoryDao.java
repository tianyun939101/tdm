package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.Mock8DownloadHistory;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author wuhui
 * @date 2020/12/7 11:03
 **/
@MyBatisDao
public interface Mock8DownloadHistoryDao extends CrudDao<Mock8DownloadHistory> {

    List<Mock8DownloadHistory> findGraphData(Mock8DownloadHistory history);

    List<Mock8DownloadHistory> findGraphUser(Mock8DownloadHistory history);
}
