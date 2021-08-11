package com.demxs.tdm.dao.business;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.Plan;
import com.demxs.tdm.domain.business.PlanConnect;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;
import java.util.Map;

@MyBatisDao
public interface PlanConnectDao extends CrudDao<PlanConnect> {

    /**
     * 查询状态信息合集
     * @return
     */
  public List<PlanConnect> findState(
            @Param("labId")String labId, @Param("start") Date start,  @Param("end") Date end,@Param("userIds")  String userIds,  @Param("name") String name,@Param("parentId") String parentId,@Param("id") String id,@Param("time") String time);

    public List<PlanConnect> findColum(
            @Param("labId")String labId, @Param("start") Date start, @Param("end") Date end, @Param("name") String name,@Param("userIds")  String userIds);
    public List<PlanConnect> findPie(
            @Param("labId")String labId, @Param("start") Date start, @Param("end") Date end, @Param("name") String name,@Param("userIds")  String userIds,@Param("parentId") String parentId,@Param("id") String id,@Param("time") String time);

    public List<PlanConnect> findStateSend(@Param("liableUserId") String liableUserId);



    public List<PlanConnect> findStateModel(
            @Param("labId")String labId, @Param("start") Date start,  @Param("end") Date end,@Param("userIds")  String userIds,  @Param("name") String name,@Param("parentId") String parentId,@Param("id") String id,@Param("time") String time);
    public List<PlanConnect> findColumModel(
            @Param("labId")String labId, @Param("start") Date start, @Param("end") Date end, @Param("name") String name,@Param("userIds")  String userIds);
    public List<PlanConnect> findPieModel(
            @Param("labId")String labId, @Param("start") Date start, @Param("end") Date end, @Param("name") String name,@Param("userIds")  String userIds,@Param("parentId") String parentId,@Param("id") String id,@Param("time") String time);

    public List<PlanConnect> findStateSendModel(@Param("liableUserId") String liableUserId);


  public List<PlanConnect> hour(
          @Param("labId")String labId, @Param("start") Date start,  @Param("end") Date end,@Param("userIds")  String userIds,  @Param("name") String name,@Param("parentId") String parentId,@Param("time") String time);
  public List<PlanConnect> hourById(
          @Param("labId")String labId, @Param("start") Date start,  @Param("end") Date end,@Param("userIds")  String userIds,  @Param("name") String name,@Param("id") String id,@Param("time") String time);
  public List<PlanConnect> hourModel(
          @Param("labId")String labId, @Param("start") Date start,  @Param("end") Date end,@Param("userIds")  String userIds,  @Param("name") String name,@Param("parentId") String parentId,@Param("time") String time);
  public List<PlanConnect> hourModelById(
          @Param("labId")String labId, @Param("start") Date start,  @Param("end") Date end,@Param("userIds")  String userIds,  @Param("name") String name,@Param("id") String id,@Param("time") String time);

}
