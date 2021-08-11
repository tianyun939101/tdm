package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.Version;

/**
 * 版本号DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface VersionDao extends CrudDao<Version> {
	
}