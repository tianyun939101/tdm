package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestCheck;
import org.apache.ibatis.annotations.Param;

/**
 * @author wuhui
 * @date 2020/12/9 15:29
 **/
@MyBatisDao
public interface NoStandardTestCheckDao extends CrudDao<NoStandardTestCheck> {

    NoStandardTestCheck getTestCheckByReportId(@Param("reportId") String reportId);
}
