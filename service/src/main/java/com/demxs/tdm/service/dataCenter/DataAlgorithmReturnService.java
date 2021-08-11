package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataAlgorithmReturnDao;
import com.demxs.tdm.domain.dataCenter.DataAlgorithmReturn;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class DataAlgorithmReturnService extends CrudService<DataAlgorithmReturnDao, DataAlgorithmReturn> {

    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void deleteByAlgorithmId(String dataAlgorithmId){
        super.dao.deleteByAlgorithmId(dataAlgorithmId);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(DataAlgorithmReturn dataAlgorithmReturn){
        dataAlgorithmReturn.preInsert();
        super.dao.insert(dataAlgorithmReturn);
    }
}
