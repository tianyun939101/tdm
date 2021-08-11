package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ModelPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface ModelPlanDao extends CrudDao<ModelPlan> {
    List<ModelPlan> findFather(@Param("serialNumber") String serialNumber,@Param("workId") String workId);
    List<ModelPlan>  findWorkId(@Param("workId") String workId);
    List<ModelPlan> findStatus(@Param("workId") String workId);
    void deleteRe(@Param("serialNumber") String serialNumber,@Param("name") String name);
    void deleteRe1(@Param("serialNumber") String serialNumber,@Param("name") String name);
    void saveReName(ModelPlan modelPlan);
    List<ModelPlan> findReName(@Param("y2") String y2);
    void deleteReName(ModelPlan modelPlan);
    ModelPlan getReName(@Param("id") String id);
    int delete(@Param("workId") String workId);
}
