package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecutionItem;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/2 14:52
 * @Description:
 */

@MyBatisDao
public interface NoStandardExecutionItemDao extends CrudDao<NoStandardExecutionItem> {

    /**
    * @author Jason
    * @date 2020/6/8 15:54
    * @params [executionId]
    * 根据任务执行单id查询任务执行项，阻燃大屏数据支持
    * @return java.util.List<com.demxs.tdm.domain.business.nostandard.NoStandardExecutionItem>
    */
    List<NoStandardExecutionItem> findByExecutionId(String executionId);

    NoStandardExecutionItem getByExecutionId(String executionId);

    NoStandardExecutionItem getItemDetailByExecutionId(String executionId);

    int deleteByExecutionId(String id);

    int updateActive(NoStandardExecutionItem executionItem);

    int changeStatus(NoStandardExecutionItem executionItem);
}
