package com.demxs.tdm.dao.business.instrument;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.instrument.DzCirculationRecord;
import com.demxs.tdm.domain.business.instrument.DzToolManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@MyBatisDao
public interface DzToolManagementDao extends CrudDao<DzToolManagement> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_TOOL_MANAGEMENT
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    int insert(DzToolManagement dzToolManagement);

    DzToolManagement selectAuditInfolist(@Param("id") String id);

    List<DzToolManagement> selectfindList(@Param("name") String name, @Param("manageId") String manageId, @Param("departments") String departments, @Param("categoryId") String categoryId, @Param("subCenter") String subCenter);
    List<DzToolManagement> selectfindListJG(@Param("state") String state, @Param("departments") String departments, @Param("leader") String leader);
    List<DzToolManagement> selectfindListJGfor(@Param("state") String state);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table DZ_TOOL_MANAGEMENT
     *
     * @mbggenerated Sat Apr 04 22:07:54 CST 2020
     */
    int insertSelective(DzToolManagement record);

    List<DzToolManagement> selectByTgList(@Param("ids") String[] ids);
    List<DzToolManagement> returnselectByTgList(@Param("ids") String[] ids);

    List<DzToolManagement> selectBycodeList1(@Param("code") String code);
    List<DzToolManagement> selectBycodeList2(@Param("code") String code);
}