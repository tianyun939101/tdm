package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyLabInfo;

import java.util.List;

@MyBatisDao
public interface ZyLabInfoDao extends TreeDao<ZyLabInfo> {
    List<ZyLabInfo> findByParentIdsLike(ZyLabInfo zyLabInfo);

    List<ZyLabInfo> selectByList(String parentIds);

    List<ZyLabInfo> findByParent(String parentIds);
}