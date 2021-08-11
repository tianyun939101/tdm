package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzProjectclass;

import java.util.List;

@MyBatisDao
public interface DzProjectclassDao  extends TreeDao<DzProjectclass> {

    public List<DzProjectclass> findByParentIdsLike(DzProjectclass dzAbilityClass);

    List<DzProjectclass> selectByList(String parentIds);
}