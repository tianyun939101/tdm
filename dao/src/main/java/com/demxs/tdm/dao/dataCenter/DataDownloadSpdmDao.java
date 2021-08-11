package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataDownloadSpdm;

import java.util.List;

/**
 * SPDM下载 DAO
 */
@MyBatisDao
public interface DataDownloadSpdmDao extends CrudDao<DataDownloadSpdm> {

}