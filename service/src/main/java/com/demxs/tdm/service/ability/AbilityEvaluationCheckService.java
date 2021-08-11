package com.demxs.tdm.service.ability;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.AbilityEvaluationChcekDao;
import com.demxs.tdm.domain.ability.AbilityEvaluationCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 能力自查表业务操作
 * @author: wuhui
 * @Date: 2020/10/17 10:54
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class AbilityEvaluationCheckService extends CrudService<AbilityEvaluationChcekDao, AbilityEvaluationCheck> {

    @Autowired
    private AbilityEvaluationChcekDao abilityEvaluationChcekDao;

    /**
     * @Describe:根据试验室、能力编号查询自查表
     * @Author:WuHui
     * @Date:14:46 2020/10/17
     * @param cId
     * @param vId
     * @param labId
     * @return:com.demxs.tdm.domain.ability.AbilityEvaluationCheck
    */
    public AbilityEvaluationCheck getEvalCheckByCVLId(String cId,String vId,String labId){
       return  abilityEvaluationChcekDao.getEvalCheckByCVLId(cId,vId,labId);
    }
}
