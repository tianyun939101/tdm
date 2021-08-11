package com.demxs.tdm.dao.business.instrument;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.instrument.DzStorageLocation;

import java.util.List;

@MyBatisDao
public interface DzStorageLocationDao extends TreeDao<DzStorageLocation> {
    List<DzStorageLocation> findByParentIdsLike(DzStorageLocation dzStorageLocation);

    List<DzStorageLocation> selectByList(String parentIds);
}