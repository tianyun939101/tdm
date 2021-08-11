package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.TestPieceALog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface TestPieceALogDao extends CrudDao<TestPieceALog> {
    List<TestPieceALog> findListByPieceId(@Param("pieceId") String pieceId);
}
