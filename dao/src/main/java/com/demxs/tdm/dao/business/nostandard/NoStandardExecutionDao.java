package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardExecution;

import java.util.List;

@MyBatisDao
public interface NoStandardExecutionDao extends CrudDao<NoStandardExecution> {

    int updateActive(NoStandardExecution noStandardExecution);

    int changeStatus(NoStandardExecution noStandardExecution);

    NoStandardExecution getByEntrustId(String entrustId);

    NoStandardExecution getBaseByEntrustId(String entrustId);

    /**
    * @author Jason
    * @date 2020/7/2 17:10
    * @params [id]
    * 获得单表基础信息
    * @return com.demxs.tdm.domain.business.nostandard.NoStandardExecution
    */
    NoStandardExecution getBase(String id);

    /**
    * @author Jason
    * @date 2020/6/8 16:09
    * @params [labId]
    * 根据试验室id获取正在执行的全部任务，阻燃大屏试验任务列表面板数据支持
    * @return java.util.List<java.lang.String>
    */
    List<String> findIdByLabId(String labId);
}
