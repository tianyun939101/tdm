package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yangpin.RenwuYp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 任务关联样品组DAO接口
 * @author 詹小梅
 * @version 2017-06-22
 */
@MyBatisDao
public interface RenwuYpDao extends CrudDao<RenwuYp> {
    /**
     * 任务关联样品组
     * @param renwuYps
     */
    public void saveRenwYpz(List<RenwuYp> renwuYps);

    /**
     * 删除任务样品组关联
     * @param diaoduzj
     */
    public void deleteRenwuYpz(@Param("diaoduzj") String diaoduzj);

    /**
     * 样品组入库后更新任务状态为【1：待执行】
     * @param renwuzj
     */
    public void updateRenwuzt(@Param("renwuzj") String renwuzj);

    /**
     * 样品组入库后更新样品组状态
     * @param renwuzj
     */
    public void updateYpzzt(@Param("renwuzj") String renwuzj);
	
}