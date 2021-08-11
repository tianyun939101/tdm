package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzSystemClass;

import java.util.List;

@MyBatisDao
public interface DzSystemClassDao   extends TreeDao<DzSystemClass> {

    List<DzSystemClass> findByParentIdsLike(DzSystemClass dzAbilityClass);

    List<DzSystemClass> selectByList(String parentIds);
}