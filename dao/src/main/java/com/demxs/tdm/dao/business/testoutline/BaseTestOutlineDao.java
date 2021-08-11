package com.demxs.tdm.dao.business.testoutline;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.testoutline.BaseTestOutline;

import java.util.List;

@MyBatisDao
public interface BaseTestOutlineDao extends CrudDao<BaseTestOutline> {

    int updateBase(BaseTestOutline baseTestOutline);

    int changeEditVersion(BaseTestOutline baseTestOutline);

    int changeAuditVersion(BaseTestOutline baseTestOutline);

    List<BaseTestOutline> findCurVersionDetail(BaseTestOutline baseTestOutline);
}
