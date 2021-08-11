package com.demxs.tdm.service.resource.yuangong;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.resource.yuangong.StaffingReportDao;
import com.demxs.tdm.domain.resource.yuangong.StaffingReport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 试验人员资源分配任务报告service
 */
@Service
@Transactional(readOnly = true)
public class StaffingReportService extends CrudService<StaffingReportDao, StaffingReport> {

    public StaffingReport getByStaffingId(String id) {
        return this.dao.getByStaffingId(id);
    }
}