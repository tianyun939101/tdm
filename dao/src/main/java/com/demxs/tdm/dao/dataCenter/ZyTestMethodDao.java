package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyShortLab;
import com.demxs.tdm.domain.dataCenter.ZyTestMethod;

import java.util.List;

@MyBatisDao
public interface ZyTestMethodDao extends CrudDao<ZyTestMethod> {

}