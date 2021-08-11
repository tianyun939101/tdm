package com.demxs.tdm.dao.resource.feiyong;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.feiyong.Feiyong;
import org.apache.ibatis.annotations.Param;

/**
 * 试验费用DAO接口
 * @author sunjunhui
 * @version 2017-11-07
 */
@MyBatisDao
public interface FeiyongDao extends CrudDao<Feiyong> {

    void updateCost(@Param("id") String id, @Param("cost") String cost);

    void updateWeights(@Param("id") String id,@Param("weights") String weights);

    Feiyong findByItemIdAndLabId(@Param("itemId") String itemId,@Param("labId") String labId);

}