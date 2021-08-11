package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.dataCenter.DataTestInfoDao;
import com.demxs.tdm.domain.dataCenter.DataTestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class DataTestInfoService extends CrudService<DataTestInfoDao, DataTestInfo> {

    @Autowired
    private  DataTestInfoDao  dataTestInfoDao;

    public void deleteByBaseId(String baseId) {
        super.dao.deleteByBaseId(baseId);
    }


    public List<DataTestInfo> getByBaseId(String baseId) {

        return  dataTestInfoDao.getByBaseId(baseId);
    }
}
