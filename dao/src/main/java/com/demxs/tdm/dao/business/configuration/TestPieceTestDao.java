package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.TestPiceSoftware;
import com.demxs.tdm.domain.business.configuration.TestPiceTest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface TestPieceTestDao extends CrudDao<TestPiceTest> {
    List<TestPiceTest> getByPieceId(@Param("pieceId") String pieceId);
}
