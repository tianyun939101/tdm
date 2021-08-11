package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.MeasureCheck;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface NoStandardMeasureCheacksDao extends CrudDao<MeasureCheck> {

    List<MeasureCheck> getByBasicId(@Param("basicId") String basicId);
}
