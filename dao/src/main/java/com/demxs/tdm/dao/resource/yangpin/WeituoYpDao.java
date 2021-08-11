package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.dao.resource.dto.YangPinDto;
import com.demxs.tdm.domain.resource.yangpin.WeituoYp;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 申请单关联样品组DAO接口
 * @author 詹小梅
 * @version 2017-06-22
 */
@MyBatisDao
public interface WeituoYpDao extends CrudDao<WeituoYp> {
    /**
     * @Author：詹小梅
     * @param： * @param null
     * @Description：获取申请单关联样品组
     * @Date：16:57 2017/6/22
     * @Return：
     * @Exception：
     */
    public List<WeituoYp> findList(@Param("fangfazj") String fangfazj, @Param("weituodzj") String weituodzj);

    /**
     * @Author：詹小梅
     * @param： * @param yangPinDto
     * @Description：获取申请单关联样品组
     * @Date：16:57 2017/6/27
     * @Return：
     * @Exception：
     */
    public List<YangPinDto> findweituoyp(WeituoYp yangPinDto);

    /**
     * @Author：詹小梅
     * @param： fangfaids 方法ID集合
     * @param： weituodzj 申请单主键
     * @Description：删除申请单关联样品组
     * @Date：17:09 2017/6/22
     * @Return：
     * @Exception：
     */
    public void deleteWeituodypz(@Param("fangfazjs") String[] fangfazjs,
                                 @Param("weituodzj") String weituodzj);

    public WeituoYp findList(@Param("yangpinid") String yangpinid);
    /**
     * @Author：詹小梅
     * @param： * @param null
     * @Description：
     * @Date：19:07 2017/6/22
     * @Return：
     * @Exception：
     */
    public void updateYangpinzt(@Param("weituodzj") String weituodzj, @Param("yangpinzt") String yangpinzt);

    /**
     * 获取申请单样品组（剩余数量大于0）
     * @param weituoYp
     * @return
     */
    public List<WeituoYp> findWeituoyp(WeituoYp weituoYp);
    /**
     * 获取申请单样品组（剩余数量大于0）--调度时
     * @param weituoYp  申请样品组
     * @return
     */
    public List<YangPinDto> findWeituosyyp(WeituoYp weituoYp);

    /**
     * 更新样品数量，剩余数量
     * @param id 申请样品组id
     * @param shuliang 调度更新后数量
     */
    public void updateYangpinsl(@Param("id") String id, @Param("shuliang") String shuliang);

    /**
     * 更新样品数量，剩余数量
     * @param allYangPinzsl 申请样品组 key:详品组ID value:数量
     */
    public void updateYangPinSysl(@Param("allYangPinzsl") Map allYangPinzsl);

    /**
     * 样品组剩余数量还原
     * @param weituodzj 申请单主键
     * @param fangfazj 主法主键
     */
    public void restoreSl(@Param("weituodzj") String weituodzj,
                          @Param("fangfazj") String fangfazj);

    /**
     * 还原申请单内所有样品组的剩余数量
     * @param weituodzj 申请单主键
     */
    public void restoreSYSL(@Param("weituodzj") String weituodzj);

    /**
     *  @Author：郭金龙
     * 拷贝方法使用设备
     * @param weituodzj 申请单ID
     * @param newId 新申请单ID
     * @param userId 用户ID
     * @param time 创建时间
     */
    public void copyypz(@Param("weituodzj") String weituodzj,
                        @Param("newId") String newId,
                        @Param("userId") String userId,
                        @Param("time") Date time);

    /**
     *  @Author：郭金龙
     * 根据申请单主键将申请样品状态变更为待检
     * @param weituodzj 申请单ID
     * @param status 状态
     * @param userId 用户ID
     * @param time 创建时间
     */
    public void updateStatusToDaiJian(@Param("weituodzj") String weituodzj,
                                      @Param("status") String status,
                                      @Param("userId") String userId,
                                      @Param("time") Date time);

    public String getYangpinzs(@Param("weituodzj") String weituodzj);


    public String findMaxYangpinzh(@Param("weituodzj") String weituodzj, @Param("fangfazj") String fangfazj);
}