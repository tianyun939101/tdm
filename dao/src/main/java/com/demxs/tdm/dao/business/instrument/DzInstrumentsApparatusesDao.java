package com.demxs.tdm.dao.business.instrument;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.instrument.DzInstrumentsApparatuses;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface DzInstrumentsApparatusesDao extends CrudDao<DzInstrumentsApparatuses> {

    int insert(DzInstrumentsApparatuses record);

    DzInstrumentsApparatuses selectAuditInfolist(@Param("id") String id);

    List<DzInstrumentsApparatuses> selectfindList(@Param("name") String name, @Param("manageId") String manageId, @Param("departments") String departments);
    List<DzInstrumentsApparatuses> selectfindListJG(@Param("state") String state, @Param("name") String name, @Param("manageId") String manageId, @Param("departments") String departments);
    List<DzInstrumentsApparatuses> selectfindListJGfor(@Param("state") String state);
    int insertSelective(DzInstrumentsApparatuses record);
    List<DzInstrumentsApparatuses> selectAllList(DzInstrumentsApparatuses  dzInstrumentsApparatuses);


    List<DzInstrumentsApparatuses> selectByTgList(@Param("ids") String[] ids);
    List<DzInstrumentsApparatuses> returnselectByTgList(@Param("ids") String[] ids);

    List<DzInstrumentsApparatuses> selectBycodeList1(@Param("code") String code,@Param("name") String name);
    List<DzInstrumentsApparatuses> selectBycodeList2(@Param("code") String code);

    /**
     * @Describe:根据办公室汇总仪表
     * @Author:WuHui
     * @Date:10:42 2020/8/31
     * @param departmentId
     * @return:java.lang.Integer
    */
    Integer countInstrumentByDepartment(@Param("departmentId") String departmentId);

    /**
     * @Describe:根据办公室查询仪表数据
     * @Author:WuHui
     * @Date:10:20 2020/8/31
     * @param departmentId
     * @return:
    */
   List<DzInstrumentsApparatuses> findInstrumentByDepartment(@Param("page") Page<DzInstrumentsApparatuses> page, @Param("departmentId") String departmentId, @Param("labinfoId") String labinfoId);


   void updateState(DzInstrumentsApparatuses  dzInstrumentsApparatuses);

   List<DzInstrumentsApparatuses> selectByState(@Param("ids") String[] ids);

    List<DzInstrumentsApparatuses> selectfindInfoList(DzInstrumentsApparatuses dzInstrumentsApparatuses);

    DzInstrumentsApparatuses selectById(@Param("id") String id);

    void   insertIncreate(DzInstrumentsApparatuses dzInstrumentsApparatuses);


    void   updateIncreate(DzInstrumentsApparatuses dzInstrumentsApparatuses);


    void  updateStatus(@Param("id") String  id);

    List<String>  getAllLendingRecord();
}