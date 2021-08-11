package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyTestTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface ZyTestTaskDao extends CrudDao<ZyTestTask> {

    List<ZyTestTask> findDataList(ZyTestTask zyTestTask);

    void  updateStatus(@Param("id")String id, @Param("status") String status);

    void  updateUser(@Param("preUser") String preUser,@Param("id")String id,@Param("approvalUser") String approvalUser);
}