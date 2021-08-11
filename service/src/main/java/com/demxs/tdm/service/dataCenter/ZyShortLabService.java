package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.ZyShortLabDao;
import com.demxs.tdm.dao.dataCenter.ZyShortVersionDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.ZyLabPerson;
import com.demxs.tdm.domain.dataCenter.ZyShortLab;
import com.demxs.tdm.domain.dataCenter.ZyShortVersion;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyShortLabService extends  CrudService<ZyShortLabDao, ZyShortLab> {

    @Autowired
    ZyShortLabDao zyShortLabDao;

    @Autowired
    ZyShortVersionDao zyShortVersionDao;



    public Page<ZyShortLab> list(Page<ZyShortLab> page, ZyShortLab entity) {
        Page<ZyShortLab> Page = super.findPage(page, entity);
        return Page;
    }

    public List<ZyShortLab> findAllDataList(ZyShortLab entity) {
        List<ZyShortLab> list=zyShortLabDao.findDataList(entity);
        //List<String> parentList=zyShortLabDao.selectParentId();
        if(CollectionUtils.isNotEmpty(list)){
                for(ZyShortLab s:list){
                    ZyShortLab   z=new ZyShortLab();
                    z.setParentId(s.getId());
                    List<ZyShortLab> sonList=zyShortLabDao.findList(z);
                    s.setChildren(sonList);

                }
        }

        return list;
    }
    public Page<ZyShortLab> findPage(Page<ZyShortLab> page, ZyShortLab entity) {
        entity.setPage(page);
        List<ZyShortLab> list=zyShortLabDao.findList(entity);
        page.setList(list);
        return page;
    }



    public ZyShortLab get(String id) {
        ZyShortLab equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyShortLab entity) {
        super.save(entity);

    }


}
