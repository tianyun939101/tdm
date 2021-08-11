package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.ZyOutAblityDao;
import com.demxs.tdm.dao.ability.ZyOutAblityRelationDao;
import com.demxs.tdm.domain.ability.ZyOutAblity;
import com.demxs.tdm.domain.ability.ZyOutAblityRelation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 外部供应商能力表Service
 * @author zwm
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyOutAblityRelationService extends CrudService<ZyOutAblityRelationDao, ZyOutAblityRelation> {

	
}