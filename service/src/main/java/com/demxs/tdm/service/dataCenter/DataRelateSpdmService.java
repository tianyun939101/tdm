package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.dataCenter.DataRelateSpdmDao;
import com.demxs.tdm.domain.dataCenter.DataRelateSpdm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DataRelateSpdmService extends CrudService<DataRelateSpdmDao, DataRelateSpdm> {

    public List<DataRelateSpdm> getByTestId(String testId){
        return this.dao.getByTestId(testId);
    }

    public void deleteReSpdm(DataRelateSpdm dataRelateSpdm) {
        List<DataRelateSpdm> list = this.dao.getBySpdm(dataRelateSpdm);
    }
}
