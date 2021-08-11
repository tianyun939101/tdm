package com.demxs.tdm.service.dataCenter.subcenterconfig;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.SubCenterTestTaskDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.SubCenterTestTask;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:49
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class SubCenterTestTaskService extends CrudService<SubCenterTestTaskDao, SubCenterTestTask> {

    public List<SubCenterTestTask> findByLabId(String labId){
        return this.dao.findByLabId(labId);
    }
}
