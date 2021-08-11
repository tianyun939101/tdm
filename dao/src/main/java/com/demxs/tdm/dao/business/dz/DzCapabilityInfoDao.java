package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzCapabilityInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzCapabilityInfoDao extends CrudDao<DzCapabilityInfoDao> {
     
    int deleteByPrimaryKey(String id);

    int insert(DzCapabilityInfo record);

     
    int insertSelective(DzCapabilityInfo record);


    Map<String,Object> selectByPrimaryKey(String id);

    List<DzCapabilityInfo> selectByRelationIds(@Param("relationIds") String relationIds);

     
    int updateByPrimaryKeySelective(DzCapabilityInfo record);

     
    int updateByPrimaryKey(DzCapabilityInfo record);
}