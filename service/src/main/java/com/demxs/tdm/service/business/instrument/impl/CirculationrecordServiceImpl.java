package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.instrument.DzCirculationRecordDao;
import com.demxs.tdm.domain.business.instrument.DzCirculationRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CirculationrecordServiceImpl extends CrudService<DzCirculationRecordDao, DzCirculationRecord> {
    @Autowired
    DzCirculationRecordDao dzCirculationRecordDao;

    @Override
    public void save(DzCirculationRecord dzCirculationRecord) {
        super.save(dzCirculationRecord);
    }
    @Override
    public void delete(DzCirculationRecord dzCirculationRecord) {
        super.delete(dzCirculationRecord);
    }

    public DzCirculationRecord getAuditInfolist(String id) {
        DzCirculationRecord  selectlist  = dzCirculationRecordDao.selectAuditInfolist(id);
        return selectlist;
    }

    public List<DzCirculationRecord> getRecord(String relationId){
        return dzCirculationRecordDao.getRecord(relationId);
    }

    public List<DzCirculationRecord>  getRecordInfo(DzCirculationRecord record){
        return dzCirculationRecordDao.getRecordInfo(record);
    }

    public void updateRecordInfo(DzCirculationRecord record){
        dzCirculationRecordDao.updateInfo(record);
    }


    public   List<DzCirculationRecord> getLendRecord(String relationId){
        return dzCirculationRecordDao.getLendRecord(relationId);
    }

    public List<DzCirculationRecord>  getLendingRecordInfo(DzCirculationRecord record){
        return dzCirculationRecordDao.getLendingRecordInfo(record);
    }
}
