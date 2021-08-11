package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUpFile;
import com.demxs.tdm.domain.dataCenter.ZyLabBasicInfo;
import org.apache.ibatis.annotations.Param;

@MyBatisDao
public interface ZyLabBasicInfoDao extends CrudDao<ZyLabBasicInfo> {

}