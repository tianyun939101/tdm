package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzProviderInfo;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzProviderInfoDao  extends CrudDao<DzProviderInfoDao> {
     
    int deleteByPrimaryKey(String id);

     
    int insert(DzProviderInfo record);

     
    int insertSelective(DzProviderInfo record);


    Map<String,Object> selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DzProviderInfo record);

     
    int updateByPrimaryKey(DzProviderInfo record);
}