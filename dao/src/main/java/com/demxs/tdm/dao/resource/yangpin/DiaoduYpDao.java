package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.dao.resource.dto.YangPinDto;
import com.demxs.tdm.domain.resource.yangpin.DiaoduYp;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 调度关联样品组DAO接口
 * @author 詹小梅
 * @version 2017-06-22
 */
@MyBatisDao
public interface DiaoduYpDao extends CrudDao<DiaoduYp> {
    /**
     *从申请单样品组-调度样品组
     * @param weituodzj
     * @param userId
     * @param time
     */
    public void copyWeituoYptoRw(@Param("weituodzj") String weituodzj, @Param("userId") String userId, @Param("time") Date time);
    /**
     * 删除调度样品组
     * @param weituodzj 申请单主键
     * @param fangfazj 方法主键
     */
    public void deleteDiaodu(@Param("weituodzj") String weituodzj,
                             @Param("fangfazj") String fangfazj);

    /**
     * 任务关联样品组，修改数量后更新调度样品组数量
     * @param yangpinzid（样品组id）
     */
    public void updateYpsl(@Param("yangpinzid") String yangpinzid);

    /**
     * 还原调度样品组数量
     * @param id（调度样品id）
     */
    public void restoreSl(@Param("id") String id);

    /**
     * 更新样品数量
     * @param yangpinzid
     * @param shuliang
     */
    public void updateYangpinsl(@Param("yangpinzid") String yangpinzid, @Param("shuliang") String shuliang);


    public List<YangPinDto> findDiaoduyp(DiaoduYp diaoduYp);

}