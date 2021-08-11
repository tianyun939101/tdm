package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.dataCenter.DataBasePerm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据中心基础信息权限DAO
 */
@MyBatisDao
public interface DataBasePermDao extends CrudDao<DataBasePerm> {

    List<DataBasePerm> getOfficePermByBaseId(String baseId);

    List<DataBasePerm> getUserPermByBaseId(String baseId);

    void detetePermByBaseId(String baseId);

    List<DataBasePerm> findPermByBaseId(@Param("baseId") String baseId, @Param("user") User user);

}