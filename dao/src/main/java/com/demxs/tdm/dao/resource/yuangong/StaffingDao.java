package com.demxs.tdm.dao.resource.yuangong;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yuangong.Staffing;

/**
 * 试验人员资源调配Dao
 */
@MyBatisDao
public interface StaffingDao extends CrudDao<Staffing> {
}