package com.demxs.tdm.dao.resource.yuangong;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yuangong.StaffingReport;

/**
 * 试验人员资源分配任务报告Dao
 */
@MyBatisDao
public interface StaffingReportDao extends CrudDao<StaffingReport> {
    StaffingReport getByStaffingId(String id);
}