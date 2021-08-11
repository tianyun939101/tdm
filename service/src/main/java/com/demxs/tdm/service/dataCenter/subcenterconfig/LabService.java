package com.demxs.tdm.service.dataCenter.subcenterconfig;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.LabDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.Lab;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:48
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class LabService extends CrudService<LabDao, Lab> {
}
