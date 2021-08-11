package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardEntrustReport;

/**
 * @Auther: Jason
 * @Date: 2020/3/9 18:33
 * @Description:
 */
@MyBatisDao
public interface NoStandardEntrustReportDao extends CrudDao<NoStandardEntrustReport> {

    int changeStatus(NoStandardEntrustReport report);

    NoStandardEntrustReport getDetail(String id);

    int updateActive(NoStandardEntrustReport report);

    NoStandardEntrustReport getByEntrustId(String id);

    NoStandardEntrustReport getBaseByEntrustId(String id);
}
