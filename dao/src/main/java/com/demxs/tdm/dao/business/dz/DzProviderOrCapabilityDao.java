package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzProviderOrCapability;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzProviderOrCapabilityDao extends CrudDao<DzProviderOrCapability> {
     
    int deleteByPrimaryKey(String id);

    int deleteList(String relationid);

    DzProviderOrCapability selectAuditInfolist(String relationid);
    int insert(DzProviderOrCapability record);

    int updatestate(@Param("id") String id, @Param("state") String state);
     
    int insertSelective(DzProviderOrCapability record);

     
    DzProviderOrCapability selectByPrimaryKey(String id);

    List<DzProviderOrCapability> selectList(@Param("supplier_name") String supplier_name, @Param("number") String number, @Param("department") String department, @Param("state") String state);
     
    int updateByPrimaryKeySelective(DzProviderOrCapability record);

     
    int updateByPrimaryKey(DzProviderOrCapability record);

    List<Map<String,Object>> selectlist(String relationid);

    List<DzProviderOrCapability> selectSumListById(String relationid);

    List<Map<String,Object>> findByParentIdsLike(DzProviderOrCapabilityDao dzProviderOrCapabilityDao);
}