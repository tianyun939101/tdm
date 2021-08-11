package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.SynchronizeTaskRecord;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/8 16:12
 * @Description:
 */
@MyBatisDao
public interface SynchronizeTaskRecordDao extends CrudDao<SynchronizeTaskRecord> {

    /**
    * @author Jason
    * @date 2020/7/8 16:13
    * @params [taskRecord]
    * 根据同步任务id查询同步记录
    * @return java.util.List<com.demxs.tdm.domain.dataCenter.pr.SynchronizeTaskRecord>
     * @param taskRecord
    */
    List<SynchronizeTaskRecord> findByTaskId(String taskRecord);
}
