package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataReportDownloadHistoryDao;
import com.demxs.tdm.domain.dataCenter.DataReportDownloadHistory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/7/15 11:41
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class DataReportDownloadHistoryService extends CrudService<DataReportDownloadHistoryDao,DataReportDownloadHistory> {

    public List<DataReportDownloadHistory> findByTestId(String id){
        return this.dao.findByTestId(id);
    }
}
