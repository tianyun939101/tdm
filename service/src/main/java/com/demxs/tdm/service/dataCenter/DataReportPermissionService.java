package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.DataReportDao;
import com.demxs.tdm.dao.dataCenter.DataReportPermissionDao;
import com.demxs.tdm.domain.business.constant.DataCenterConstants;
import com.demxs.tdm.domain.dataCenter.DataReport;
import com.demxs.tdm.domain.dataCenter.DataReportPermission;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class DataReportPermissionService extends CrudService<DataReportPermissionDao, DataReportPermission> {

    public void deleteByTestId(String id) {
        this.dao.deleteByTestId(id);
    }

    public List<DataReportPermission> getByTestId(String testId) {
        return this.dao.getByTestId(testId);
    }
}
