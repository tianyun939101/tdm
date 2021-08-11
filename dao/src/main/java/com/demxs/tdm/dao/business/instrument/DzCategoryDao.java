package com.demxs.tdm.dao.business.instrument;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.instrument.DzCategory;

import java.util.List;

@MyBatisDao
public interface DzCategoryDao  extends TreeDao<DzCategory> {
    List<DzCategory> findByParentIdsLike(DzCategory dzCategory);

    List<DzCategory> selectByList(String parentIds);
}