package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataReportPermission;

import java.util.List;

@MyBatisDao
public interface DataReportPermissionDao extends CrudDao<DataReportPermission> {

    void deleteByTestId(String testId);

    List<DataReportPermission> getByTestId(String testId);
}