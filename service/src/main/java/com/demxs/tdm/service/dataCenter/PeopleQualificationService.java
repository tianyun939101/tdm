package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.PeopleQualificationDao;
import com.demxs.tdm.domain.dataCenter.PeopleQualification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class PeopleQualificationService  extends CrudService<PeopleQualificationDao, PeopleQualification> {
}
