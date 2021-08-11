package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ModelOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


@MyBatisDao
public interface ModelOrderDao extends CrudDao<ModelOrder> {

    List<ModelOrder> findStatusByUserId(@Param("id") String userId);
    List<Map<Object, Object>> exec( String str);
}
