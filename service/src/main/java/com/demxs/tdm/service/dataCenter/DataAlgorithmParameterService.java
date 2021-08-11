package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataAlgorithmParameterDao;
import com.demxs.tdm.domain.dataCenter.DataAlgorithm;
import com.demxs.tdm.domain.dataCenter.DataAlgorithmParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class DataAlgorithmParameterService extends CrudService<DataAlgorithmParameterDao, DataAlgorithmParameter> {

    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void deteteByAlgorithmId(String dataAlgorithmId){
        super.dao.deteteByAlgorithmId(dataAlgorithmId);
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(DataAlgorithmParameter dataAlgorithmParameter){
        dataAlgorithmParameter.preInsert();
        super.dao.insert(dataAlgorithmParameter);
    }
}
