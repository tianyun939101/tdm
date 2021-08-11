package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.YuanshijlmbRk;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 原始记录模板入库信息配置DAO接口
 * @author 谭冬梅
 * @version 2017-11-30
 */
@MyBatisDao
public interface YuanshijlmbRkDao extends CrudDao<YuanshijlmbRk> {
    public void   addTable(YuanshijlmbRk entity);
    public void   addLie(YuanshijlmbRk entity);
    public void   addComment(@Param("sql") String sql );

    public String getMaxshuxing(@Param("biaom") String biaom);
    public String getMaxbiao(@Param("biaom") String biaom);

    // 插入任务所有结果数据
    public void insertJgBatch(@Param("jieguos") List<Map> jieguos, @Param("ziduans") String[] ziduans, @Param("biaom") String biaom);


    public void insertJgBatchOne(@Param("jieguos") Map jieguos, @Param("ziduans") String[] ziduans, @Param("biaom") String biaom);

    public  List<Map>  findJgListBySql (@Param("sql") String sql );

}