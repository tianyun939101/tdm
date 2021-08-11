package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.dao.resource.knowledge.DesignFlowDao;
import com.demxs.tdm.dao.resource.knowledge.StandardDao;
import com.demxs.tdm.domain.resource.kowledge.DesignFlow;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DesignFlowService extends TreeService<DesignFlowDao, DesignFlow> {
    @Autowired
    private DesignFlowDao designFlowDao;

    public void changeName(String name, String id){
        designFlowDao.changeName(id,name);
    }
}
