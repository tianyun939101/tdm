package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.ZyShortLabDao;
import com.demxs.tdm.dao.dataCenter.ZyShortVersionDao;
import com.demxs.tdm.dao.dataCenter.ZyTestMethodDao;
import com.demxs.tdm.domain.dataCenter.ZyShortLab;
import com.demxs.tdm.domain.dataCenter.ZyTestMethod;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyTestMethodService extends  CrudService<ZyTestMethodDao, ZyTestMethod> {

    @Autowired
    ZyTestMethodDao zyTestMethodDao;

    @Autowired
    ZyShortVersionDao zyShortVersionDao;



    public Page<ZyTestMethod> list(Page<ZyTestMethod> page, ZyTestMethod entity) {
        Page<ZyTestMethod> Page = super.findPage(page, entity);
        return Page;
    }

   /* public List<ZyShortLab> findAllDataList(ZyShortLab entity) {
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
    }*/
    public Page<ZyTestMethod> findPage(Page<ZyTestMethod> page, ZyTestMethod entity) {
        entity.setPage(page);
        List<ZyTestMethod> list=zyTestMethodDao.findList(entity);
        page.setList(list);
        return page;
    }



    public ZyTestMethod get(String id) {
        ZyTestMethod equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyTestMethod entity) {
        super.save(entity);

    }


}
