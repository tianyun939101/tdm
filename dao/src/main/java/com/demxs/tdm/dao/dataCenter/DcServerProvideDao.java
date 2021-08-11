package com.demxs.tdm.dao.dataCenter;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzProviderDirectories;
import com.demxs.tdm.domain.dataCenter.DcServerProvide;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface DcServerProvideDao extends CrudDao<DcServerProvide> {
    
    int deleteByPrimaryKey(String id);
    List<DcServerProvide> getList();
    List<DcServerProvide> selectList(@Param("name") String name, @Param("state") String state);
    List<DcServerProvide> listCooperation(@Param("name") String name, @Param("state") String state);

    int insert(DcServerProvide record);

    int insertSelective(DcServerProvide record);


    DcServerProvide selectByPrimaryKey(String id);

    DcServerProvide  selectListInfo(@Param("name") String  name);


    DcServerProvide  selectOneInfo(@Param("name") String  name,@Param("contacts") String  contacts);

    
    int updateByPrimaryKeySelective(DcServerProvide record);

    
    int updateByPrimaryKey(DcServerProvide record);

    List<String> findServiceByPhonePassword(@Param("phone") String phone, @Param("password") String password);
}