 package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.AbilityEvaluationCheck;
import org.apache.ibatis.annotations.Param;

/**
 * 能力自查数据库接口
 * @author: wuhui
 * @Date: 2020/10/17 10:54
 * @Description:
 */
@MyBatisDao
public interface AbilityEvaluationChcekDao extends CrudDao<AbilityEvaluationCheck> {

    /**
     * @Describe:根据试验室、能力编号查询自查表
     * @Author:WuHui
     * @Date:14:44 2020/10/17
     * @param cId
     * @param vid
     * @param labId
     * @return:com.demxs.tdm.domain.ability.AbilityEvaluationCheck
    */
    AbilityEvaluationCheck getEvalCheckByCVLId(@Param("cId") String cId,
                                               @Param("vId") String vid,
                                               @Param("labId") String labId);
}
