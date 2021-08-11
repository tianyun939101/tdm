package com.demxs.tdm.dao.business.nostandard;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardOtherUser;

import java.util.List;

@MyBatisDao
public interface NoStandardOtherUserDao extends CrudDao<NoStandardOtherUser> {

    NoStandardOtherUser getByResourceId(String resourceId);

    int deleteByResourceId(String resourceId);

    List<NoStandardOtherUser> findListByResourceId(String resourceId);
}
