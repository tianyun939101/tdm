package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.VersionDao;
import com.demxs.tdm.dao.ability.ZyOutAblityDao;
import com.demxs.tdm.domain.ability.Version;
import com.demxs.tdm.domain.ability.ZyOutAblity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 外部供应商能力表Service
 * @author zwm
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyOutAblityService extends CrudService<ZyOutAblityDao, ZyOutAblity> {

    @Autowired
    private   ZyOutAblityDao  zyOutAblityDao;

    public List<Map<String,Object>>  getOutAbility(ZyOutAblity  zyOutAblity){

        return zyOutAblityDao.getOutAbility(zyOutAblity);
    }

    public Map<String,String> getVersion(){
        return zyOutAblityDao.getVersion();
    }

}