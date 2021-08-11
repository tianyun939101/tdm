package com.demxs.tdm.dao.business.dz;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzProviderDirectories;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface DzProviderDirectoriesDao extends CrudDao<DzProviderDirectories> {
    
    int deleteByPrimaryKey(String id);

    List<DzProviderDirectories> selectList(@Param("name") String name, @Param("state") String state);
    List<DzProviderDirectories> listCooperation(@Param("name") String name, @Param("state") String state);

    int insert(DzProviderDirectories record);

    
    int insertSelective(DzProviderDirectories record);

    
    DzProviderDirectories selectByPrimaryKey(String id);

    
    int updateByPrimaryKeySelective(DzProviderDirectories record);

    
    int updateByPrimaryKey(DzProviderDirectories record);

    List<String> findServiceByPhonePassword(@Param("phone") String phone, @Param("password") String password);
}