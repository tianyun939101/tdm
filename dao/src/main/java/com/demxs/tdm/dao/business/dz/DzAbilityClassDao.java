package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzAbilityClass;

import java.util.List;

@MyBatisDao
public interface DzAbilityClassDao extends TreeDao<DzAbilityClass> {
    public List<DzAbilityClass> findByParentIdsLike(DzAbilityClass dzAbilityClass);
    List<DzAbilityClass> selectByList(String parentIds);
}