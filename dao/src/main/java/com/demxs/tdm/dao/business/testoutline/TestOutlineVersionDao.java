package com.demxs.tdm.dao.business.testoutline;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.testoutline.TestOutlineVersion;

import java.util.List;

@MyBatisDao
public interface TestOutlineVersionDao extends CrudDao<TestOutlineVersion> {

    List<TestOutlineVersion> findByBaseId(String id);

    int changeStatus(TestOutlineVersion testOutlineVersion);

    int changeLocalReport(TestOutlineVersion testOutlineVersion);

    int changeAuditReport(TestOutlineVersion testOutlineVersion);

    int changeCurVersion(TestOutlineVersion testOutlineVersion);

    TestOutlineVersion getCurVersionDetail(String baseId);
}
