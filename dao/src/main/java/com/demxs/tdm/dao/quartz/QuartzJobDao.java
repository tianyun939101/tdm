package com.demxs.tdm.dao.quartz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.quartz.QuartzJob;
import org.apache.ibatis.annotations.Param;


@MyBatisDao
public interface QuartzJobDao extends CrudDao<QuartzJob> {


    void updateExecuteType(@Param("id")String id);
}
