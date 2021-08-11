package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.dao.resource.knowledge.KnowledgeMapDao;
import com.demxs.tdm.dao.resource.knowledge.StandardDao;
import com.demxs.tdm.domain.resource.kowledge.KnowledgeMap;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class KnowledgeMapService extends CrudService<KnowledgeMapDao, KnowledgeMap> {

    @Autowired
    private KnowledgeMapDao knowledgeMapDao;

    public void insertKnowledgeMap(KnowledgeMap entity) {
        knowledgeMapDao.insert(entity);
    }
}
