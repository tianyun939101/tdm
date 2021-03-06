package com.demxs.tdm.dao.business.dz;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.dz.DzDevelopmentPlanning;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzDevelopmentPlanningDao extends CrudDao<DzDevelopmentPlanning> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_DEVELOPMENT_PLANNING
     *
     * @mbggenerated Thu Mar 12 15:30:17 CST 2020
     */
    int deleteByPrimaryKey(String id);

    List<DzDevelopmentPlanning> selectList(@Param("code") String code, @Param("name") String name, @Param("state") String state);

    Map getplanning(String id);

    List<DzDevelopmentPlanning> selectPlanningById();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_DEVELOPMENT_PLANNING
     *
     * @mbggenerated Thu Mar 12 15:30:17 CST 2020
     */
    int insert(DzDevelopmentPlanning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_DEVELOPMENT_PLANNING
     *
     * @mbggenerated Thu Mar 12 15:30:17 CST 2020
     */
    int insertSelective(DzDevelopmentPlanning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_DEVELOPMENT_PLANNING
     *
     * @mbggenerated Thu Mar 12 15:30:17 CST 2020
     */
    DzDevelopmentPlanning selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_DEVELOPMENT_PLANNING
     *
     * @mbggenerated Thu Mar 12 15:30:17 CST 2020
     */
    int updateByPrimaryKeySelective(DzDevelopmentPlanning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_DEVELOPMENT_PLANNING
     *
     * @mbggenerated Thu Mar 12 15:30:17 CST 2020
     */
    int updateByPrimaryKey(DzDevelopmentPlanning record);
}