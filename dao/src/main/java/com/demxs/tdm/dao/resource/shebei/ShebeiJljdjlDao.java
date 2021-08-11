package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjl;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjlAll;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备计量检定记录DAO接口
 * @author zhangdengcai
 * @version 2017-07-13
 */
@MyBatisDao
public interface ShebeiJljdjlDao extends CrudDao<ShebeiJljdjl> {

    /**
     * 根据计量检定计划 获取计量检定记录
     * @param shebeiJljdjl
     * @return
     */
    public List<ShebeiJljdjl> listByJljdjhid(ShebeiJljdjl shebeiJljdjl);

    /**
     * 根据计量检定计划id 删除计量检定记录
     * @param jljdjhid
     */
    void deleteByJljdjhid(@Param("jljdjhid")String jljdjhid);

    /**
     * 批量删除
     * @param shebeiJljdjl
     */
    public void deleteMore(ShebeiJljdjl shebeiJljdjl);

    /**
     * 获取全部设备的计量检定记录
     * @return
     */
    public List<ShebeiJljdjlAll> findAllList(ShebeiJljdjlAll shebeiJljdjlAll);

    /**
     * 单个设备的所有记录列表
     * @param shebeiJljdjl
     * @return
     */
    public List<ShebeiJljdjl> dangesbsyjl(ShebeiJljdjl shebeiJljdjl);

    /**
     * 获得 所有设备计量检定任务
     * @param shebeiJljdjl
     * @return
     */
    public List<Map<String, Object>> getAllJdrw(ShebeiJljdjl shebeiJljdjl);
}