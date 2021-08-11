package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.AbilityRemovalDao;
import com.demxs.tdm.domain.ability.AbilityRemoval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wuhui
 * @date 2020/12/16 19:07
 **/
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class AbilityRemovalService extends CrudService<AbilityRemovalDao, AbilityRemoval> {
}
