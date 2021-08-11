package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.Version;
import com.demxs.tdm.domain.ability.ZyOutAblity;

import java.util.List;
import java.util.Map;

/**
 * 外部供应商DAO接口
 * @author zwm
 * @version 2017-10-30
 */
@MyBatisDao
public interface ZyOutAblityDao extends CrudDao<ZyOutAblity> {

    public List<Map<String,Object>>  getOutAbility(ZyOutAblity  zyOutAblity);

    public Map<String,String> getVersion();
}