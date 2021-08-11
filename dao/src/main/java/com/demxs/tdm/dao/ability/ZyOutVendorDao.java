package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.ZyOutAblity;
import com.demxs.tdm.domain.ability.ZyOutVendor;

/**
 * 外部供应商DAO接口
 * @author zwm
 * @version 2017-10-30
 */
@MyBatisDao
public interface ZyOutVendorDao extends CrudDao<ZyOutVendor> {
	
}