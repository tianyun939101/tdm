package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.instrument.DzCirculationRecordDao;
import com.demxs.tdm.dao.business.instrument.DzToolManagementDao;
import com.demxs.tdm.domain.business.instrument.DzCategory;
import com.demxs.tdm.domain.business.instrument.DzCirculationRecord;
import com.demxs.tdm.domain.business.instrument.DzStorageLocation;
import com.demxs.tdm.domain.business.instrument.DzToolManagement;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ToolManagementServiceImpl  extends CrudService<DzToolManagementDao, DzToolManagement> {
    @Autowired
    DzToolManagementDao dzToolManagementDao;
    @Autowired
    DzCirculationRecordDao dzCirculationRecordDao;
    @Autowired
    private OfficeService officeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private CategorytigerServiceImpl categorytigerServiceImpl;
    @Autowired
    private SeattigerSeviceImpl seattigerSeviceImpl;

    @Override
    public void save(DzToolManagement dzToolManagement) {
        super.save(dzToolManagement);
    }
    @Override
    public void delete(DzToolManagement dzToolManagement) {
        super.delete(dzToolManagement);
    }

    public List<DzToolManagement> selectfindList(String name,String manageId,String departments,String categoryId,String subCenter) {
        List<DzToolManagement> aa =  dzToolManagementDao.selectfindList(name,manageId,departments,categoryId,subCenter);
        return aa;
    }
    public List<DzToolManagement> selectfindListJG(String state,String departments,String leader) {
        List<DzToolManagement> aa =  dzToolManagementDao.selectfindListJG(state,departments,leader);
        return aa;
    }

    public List<DzToolManagement> selectfindListJGfor(String state) {
        List<DzToolManagement> aa =  dzToolManagementDao.selectfindListJGfor(state);
        return aa;
    }

    public List<DzCirculationRecord> selectfindListLYJL1(String name,String leader,String restitution) {
        List<DzCirculationRecord> aa =  dzCirculationRecordDao.selectfindListLYJL1(name,leader,restitution);
        return aa;
    }
    public List<DzCirculationRecord> selectfindListLYJL2(String name,String leader,String restitution) {
        List<DzCirculationRecord> aa =  dzCirculationRecordDao.selectfindListLYJL2(name,leader,restitution);
        return aa;
    }

    public List<DzToolManagement> dzToolManagement(String[] ids) {
        List<DzToolManagement>  dzToolManagement = dzToolManagementDao.selectByTgList(ids);
        return dzToolManagement;
    }

    public List<DzToolManagement> returnToolManagement(String[] ids) {
        List<DzToolManagement>  returnToolManagement = dzToolManagementDao.returnselectByTgList(ids);
        return returnToolManagement;
    }
    public List<DzToolManagement> selectBycodeList1(String code) {
        List<DzToolManagement>  returnToolManagement = dzToolManagementDao.selectBycodeList1(code);
        return returnToolManagement;
    }
    public List<DzToolManagement> selectBycodeList2(String code) {
        List<DzToolManagement>  returnToolManagement = dzToolManagementDao.selectBycodeList2(code);
        return returnToolManagement;
    }

    public DzToolManagement getAuditInfolist(String id) {
        DzToolManagement selectlist = dzToolManagementDao.selectAuditInfolist(id);
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getManageId())) {
            selectlist.setUser(systemService.getUser(selectlist.getManageId()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getDepartments())) {
            selectlist.setLabInfo(officeService.get(selectlist.getDepartments()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getCategoryId())) {
            selectlist.setCategory(categorytigerServiceImpl.get(selectlist.getCategoryId()));
        }
        if (selectlist != null && StringUtils.isNotBlank(selectlist.getPositionId())) {
            selectlist.setStorageLocation(seattigerSeviceImpl.get(selectlist.getPositionId()));
        }
        return selectlist;
    }
}
