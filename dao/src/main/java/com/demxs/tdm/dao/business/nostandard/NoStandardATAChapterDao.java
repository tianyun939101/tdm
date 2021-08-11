package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.VO.DataCenterSearch;
import com.demxs.tdm.domain.business.nostandard.NoStandardATAChapter;

import java.util.List;

@MyBatisDao
public interface NoStandardATAChapterDao extends CrudDao<NoStandardATAChapter> {

    List<NoStandardATAChapter> getByEntrustId(String entrustId);

    int deleteByEntrustId(String entrustId);

    void getEntrustReATA(DataCenterSearch dataCenterSearch);
}
