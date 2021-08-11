package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.SoftwareLibraryDao;
import com.demxs.tdm.domain.business.configuration.SoftwareLibrary;
import org.springframework.stereotype.Service;

@Service
public class SoftwareLibraryService extends CrudService<SoftwareLibraryDao, SoftwareLibrary> {

}
