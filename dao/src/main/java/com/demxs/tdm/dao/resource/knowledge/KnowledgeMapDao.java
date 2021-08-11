package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.KnowledgeMap;
import com.demxs.tdm.domain.resource.kowledge.Standard;

@MyBatisDao
public interface KnowledgeMapDao extends CrudDao<KnowledgeMap> {

}
