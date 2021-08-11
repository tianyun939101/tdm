package com.demxs.tdm.dao.resource.yuangong;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工DAO接口
 * @author sunjunhui
 * @version 2017-11-08
 */
@MyBatisDao
public interface YuangongDao extends CrudDao<Yuangong> {

    List<Yuangong> findListByZzids(Yuangong yuangong);

    List<Yuangong> getByUserId(String id);

    List<Integer> countYuangongByCenter(@Param("centerId") String centerId);

    List<Yuangong> findYuangongByCenter(@Param("page") Page<Yuangong> page, @Param("centerId") String centerId ,@Param("labinfoId")  String labinfoId);
    String count(@Param("centerName") String name);
}