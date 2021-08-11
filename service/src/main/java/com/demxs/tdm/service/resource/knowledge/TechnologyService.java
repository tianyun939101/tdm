package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.dao.resource.knowledge.StandardDao;
import com.demxs.tdm.dao.resource.knowledge.TechnologyDao;
import com.demxs.tdm.domain.resource.kowledge.Standard;
import com.demxs.tdm.domain.resource.kowledge.Technology;
import com.demxs.tdm.domain.resource.kowledge.TechnologyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TechnologyService extends TreeService<TechnologyDao, Technology> {
    @Autowired
    TechnologyDao technologyDao;

    public void changeName(String name, String id){
        technologyDao.changeName(id,name);
    }

}
