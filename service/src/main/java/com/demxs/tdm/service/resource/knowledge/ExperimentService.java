package com.demxs.tdm.service.resource.knowledge;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.resource.knowledge.ExperimentDao;
import com.demxs.tdm.domain.resource.kowledge.Experiment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ExperimentService extends CrudService<ExperimentDao, Experiment> {






}
