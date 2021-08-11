package com.demxs.tdm.service.resource.yuangong;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.resource.yuangong.YuangongCertDao;
import com.demxs.tdm.dao.resource.yuangong.YuangongCertRecordDao;
import com.demxs.tdm.domain.resource.yuangong.YuangongCert;
import com.demxs.tdm.domain.resource.yuangong.YuangongCertRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YuangongCertRecordService extends CrudService<YuangongCertRecordDao, YuangongCertRecord> {

}
