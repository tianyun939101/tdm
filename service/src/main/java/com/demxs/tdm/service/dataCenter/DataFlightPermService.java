package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.dataCenter.DataFlightPermDao;
import com.demxs.tdm.domain.dataCenter.DataFlightPerm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class DataFlightPermService extends CrudService<DataFlightPermDao, DataFlightPerm> {

    public void detetePermByFlightId(String id) {
        super.dao.detetePermByFlightId(id);
    }

    public List<DataFlightPerm> getUserPermByFlightId(String baseId) {
        return super.dao.getUserPermByFlightId(baseId);
    }

    public List<DataFlightPerm> getOfficePermByFlightId(String baseId) {
        return super.dao.getOfficePermByFlightId(baseId);
    }

}
