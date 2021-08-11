package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.VO.ZyFileReport;

import java.util.List;

@MyBatisDao
public interface ZyFileReportDao extends CrudDao<ZyFileReport> {


}