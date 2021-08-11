package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.TestCategoryAssessRequest;
import com.demxs.tdm.domain.resource.kowledge.BestPractices;
import com.demxs.tdm.domain.resource.kowledge.Standard;

import java.util.List;

@MyBatisDao
public interface BestPracticesDao extends CrudDao<BestPractices> {
    /**
     * @author Jason
     * @date 2020/9/23 10:38
     * @params [request]
     * 修改状态
     * @return int
     */
    int changeStatus(BestPractices request);

    /**
     * @author Jason
     * @date 2020/9/23 10:38
     * @params [request]
     * 修改审核人
     * @return int
     */
    int changeAuditUser(BestPractices request);

    /**
     * @author Jason
     * @date 2020/9/23 10:47
     * @params [request]
     * 根据试验室id查询
     * @return java.util.List<com.demxs.tdm.domain.ability.TestCategoryAssessRequest>
     */
    List<BestPractices> findByLabId(BestPractices request);

    /**
     * @author Jason
     * @date 2020/9/24 17:15
     * @params [setvId]
     * 根据版本id删除
     * @return int
     */
    int deleteByVersionId(BestPractices setvId);


    List<BestPractices> findStat();
}
