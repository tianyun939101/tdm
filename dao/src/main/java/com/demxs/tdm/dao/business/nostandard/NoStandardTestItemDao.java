package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestItem;

import java.util.List;

@MyBatisDao
public interface NoStandardTestItemDao extends CrudDao<NoStandardTestItem> {

    List<NoStandardTestItem> getByEntrustId(String entrustId);

    int deleteByEntrustId(String entrustId);
}
