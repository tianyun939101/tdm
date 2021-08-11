package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.ZyLabBasicInfoDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.ZyLabBasicInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyLabBasicInfoService extends  CrudService<ZyLabBasicInfoDao, ZyLabBasicInfo> {

    @Autowired
    ZyLabBasicInfoDao zyLabBasicInfoDao;



    public Page<ZyLabBasicInfo> list(Page<ZyLabBasicInfo> page, ZyLabBasicInfo entity) {
        Page<ZyLabBasicInfo> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyLabBasicInfo> findPage(Page<ZyLabBasicInfo> page, ZyLabBasicInfo entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyLabBasicInfo get(String id) {
        ZyLabBasicInfo equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyLabBasicInfo entity) {
        super.save(entity);

    }


}
