package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyShortLab;

import java.util.List;

@MyBatisDao
public interface ZyShortLabDao extends CrudDao<ZyShortLab> {

     List<String> findLabInfo(ZyShortLab zyShortLab);

     List<ZyShortLab> findDataList(ZyShortLab zyShortLab);

     List<String> selectParentId();
}