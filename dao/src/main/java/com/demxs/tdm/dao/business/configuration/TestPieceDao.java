package com.demxs.tdm.dao.business.configuration;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.configuration.TestPiece;

import java.util.List;

@MyBatisDao
public interface TestPieceDao extends CrudDao<TestPiece> {

    List<String> getDept(String deptId);

    List<TestPiece> findAllList(TestPiece testPiece);

    List<TestPiece> findListByDeptId(TestPiece testPiece);

}
