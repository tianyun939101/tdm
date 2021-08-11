package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzProject;

@MyBatisDao
public interface DzProjectDao extends CrudDao<DzProject> {
    
    int deleteByPrimaryKey(String id);
    DzProject selectAuditInfolist(String id);
    
    int insert(DzProject record);

    
    int insertSelective(DzProject record);

    
    DzProject selectByPrimaryKey(String id);

    
    int updateByPrimaryKeySelective(DzProject record);

    
    int updateByPrimaryKey(DzProject record);
}