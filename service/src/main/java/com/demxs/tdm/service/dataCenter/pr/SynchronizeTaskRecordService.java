package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.pr.SynchronizeTaskRecordDao;
import com.demxs.tdm.domain.dataCenter.pr.SynchronizeTaskRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/8 16:15
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class SynchronizeTaskRecordService extends CrudService<SynchronizeTaskRecordDao,SynchronizeTaskRecord> {

    public List<SynchronizeTaskRecord> findByTaskId(String taskId){
        return this.dao.findByTaskId(taskId);
    }
}
