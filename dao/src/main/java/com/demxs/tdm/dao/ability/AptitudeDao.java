package com.demxs.tdm.dao.ability;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.ability.Aptitude;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 试验资质DAO接口
 * @author sunjunhui
 * @version 2017-10-31
 */
@MyBatisDao
public interface AptitudeDao extends CrudDao<Aptitude> {


    List<Aptitude> findDataListById(Aptitude  aptitude);
}