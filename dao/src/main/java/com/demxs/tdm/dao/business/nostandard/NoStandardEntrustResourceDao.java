package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardEntrustResource;

@MyBatisDao
public interface NoStandardEntrustResourceDao extends CrudDao<NoStandardEntrustResource> {

    int changeStatus(NoStandardEntrustResource resource);

    NoStandardEntrustResource detailPage(String id);

    NoStandardEntrustResource getByEntrustId(String id);

    int updateActive(NoStandardEntrustResource resource);

    NoStandardEntrustResource getOtherUsers(String id);
}
