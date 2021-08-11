package com.demxs.tdm.dao.sys;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.sys.SysQuartzLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 定时任务日志DAO接口
 * @author sunjunhui
 * @version 2018-01-31
 */
@MyBatisDao
public interface SysQuartzLogDao extends CrudDao<SysQuartzLog> {

    public List<SysQuartzLog> getTodayDataByType(@Param("type")String type);
	
}