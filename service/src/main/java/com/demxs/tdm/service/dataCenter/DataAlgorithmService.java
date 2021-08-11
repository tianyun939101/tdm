package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataAlgorithmDao;
import com.demxs.tdm.domain.dataCenter.DataAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
public class DataAlgorithmService extends CrudService<DataAlgorithmDao, DataAlgorithm> {

//    @Autowired
//    private DataAlgorithmParameterService dataAlgorithmParameterService;
//
//    @Autowired
//    private DataAlgorithmReturnService dataAlgorithmReturnService;

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void save(DataAlgorithm dataAlgorithm){

        //保存算法基础信息
        super.save(dataAlgorithm);
//        //删除入参信息
//        dataAlgorithmParameterService.deteteByAlgorithmId(dataAlgorithm.getId());
//        //保存入参信息
//        for(DataAlgorithmParameter parameter : dataAlgorithm.getParameterList()){
//            parameter.setAlgorithmId(dataAlgorithm.getId());
//            dataAlgorithmParameterService.save(parameter);
//        }
//        //删除返回值信息
//        dataAlgorithmReturnService.deteteByAlgorithmId(dataAlgorithm.getId());
//        //保存返回值信息
//        for(DataAlgorithmReturn value : dataAlgorithm.getReturnList()){
//            value.setAlgorithmId(dataAlgorithm.getId());
//            dataAlgorithmReturnService.save(value);
//        }
    }

    @Override
    @Transactional(readOnly = false,rollbackFor = ServiceException.class)
    public void delete(DataAlgorithm dataAlgorithm){

//        //删除返回值信息
//        dataAlgorithmReturnService.deteteByAlgorithmId(dataAlgorithm.getId());
//        //删除入参信息
//        dataAlgorithmParameterService.deteteByAlgorithmId(dataAlgorithm.getId());
        //删除算法信息
        super.dao.delete(dataAlgorithm);
    }

}
