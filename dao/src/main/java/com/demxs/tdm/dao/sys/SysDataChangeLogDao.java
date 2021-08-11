package com.demxs.tdm.dao.sys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.sys.SysDataChangeLog;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by chenjinfan on 2018/1/26.
 */
@MyBatisDao
public interface SysDataChangeLogDao extends CrudDao<SysDataChangeLog> {
	List<SysDataChangeLog> findByServiceId(@RequestParam("serviceId") String serviceId);
}
