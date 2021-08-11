package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.UReportFile;

import java.util.List;

@MyBatisDao
public interface UReportFileDao extends CrudDao<UReportFile> {

    List<UReportFile> findDataList(UReportFile uReportFile);

    UReportFile   getUreportFile(String name);

     void deleteFile(String name);

     int getExistFIle(String name);
}