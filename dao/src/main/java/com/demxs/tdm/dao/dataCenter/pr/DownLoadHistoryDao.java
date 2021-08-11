package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.DownLoadHistory;

/**
 * @author: Jason
 * @Date: 2020/4/28 09:56
 * @Description:
 */
@MyBatisDao
public interface DownLoadHistoryDao extends CrudDao<DownLoadHistory> {

    /**
    * @author Jason
    * @date 2020/4/28 10:28
    * @params [downLoadRecord]
    * 修改不为空的字段
    * @return int
    */
    int updateActive(DownLoadHistory downLoadRecord);
}
