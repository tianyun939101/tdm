package com.demxs.tdm.service.dataCenter.pr;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.pr.DownLoadHistoryDao;
import com.demxs.tdm.domain.dataCenter.pr.DownLoadHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/4/28 10:03
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class DownLoadHistoryService extends CrudService<DownLoadHistoryDao, DownLoadHistory> {

    @Transactional(readOnly = true)
    public int updateActive(DownLoadHistory downLoadRecord){
        return this.dao.updateActive(downLoadRecord);
    }
}
