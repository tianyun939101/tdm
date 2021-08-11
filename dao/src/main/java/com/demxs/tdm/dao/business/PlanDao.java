package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.Plan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/25 15:19
 * @Description:
 */
@MyBatisDao
public interface PlanDao extends CrudDao<Plan> {

   List<Plan> findFather(String serialNumber);

   List<Plan> findState(@Param("workId") String workId);

   List<Plan> workId(@Param("workId") String workId);




   void saveReName(Plan plan);
   List<Plan> findReName(@Param("y2") String y2);
   void deleteReName(Plan plan);
   Plan getReName(@Param("id") String id);
   int delete(@Param("workId") String workId);
}