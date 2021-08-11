package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.SynchronizeTask;

/**
 * @author: Jason
 * @Date: 2020/7/8 15:52
 * @Description:
 */
@MyBatisDao
public interface SynchronizeTaskDao extends CrudDao<SynchronizeTask> {

    /**
    * @author Jason
    * @date 2020/7/8 16:11
    * @params [task]
    * 根据路径查找父级
    * @return com.demxs.tdm.domain.dataCenter.pr.SynchronizeTask
    */
    SynchronizeTask getParentByPath(SynchronizeTask task);

    /**
    * @author Jason
    * @date 2020/7/8 17:05
    * @params []
    * 根据命名空间删除定时任务
    * @return int
    */
    int deleteByNameSpace(SynchronizeTask task);
}
