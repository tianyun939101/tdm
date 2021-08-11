package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.dataCenter.DataDownloadSpdmDao;
import com.demxs.tdm.domain.dataCenter.DataDownloadSpdm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DataDownloadSpdmService extends CrudService<DataDownloadSpdmDao, DataDownloadSpdm> {


}
