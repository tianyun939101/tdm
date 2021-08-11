package com.demxs.tdm.dao.resource.renyuan;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.renyuan.RenyuanPxjlgx;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 被培训人员与培训记录关系DAO接口
 * @author 詹小梅
 * @version 2017-06-20
 */
@MyBatisDao

public interface RenyuanPxjlgxDao extends CrudDao<RenyuanPxjlgx> {
    //删除被培训人员与培训记录关系
    public void deletepxgx(@Param("peixunjlzj") String peixunjlzj);

    /**
     * 更新培训成绩
     * @param entity
     */
    public void updateCj(RenyuanPxjlgx entity);

    /**
     * 删除培训人员关系
     * @param list
     */
    public void batchDelete(List<RenyuanPxjlgx> list);

    /**
     * 批量添加培训人员
     * @param list
     */
    public void batchInsert(List<RenyuanPxjlgx> list);
	
}