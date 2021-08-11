package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.instrument.ApparatusCategoryDao;
import com.demxs.tdm.domain.business.instrument.ApparatusCategory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/5/6 16:46
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class ApparatusCategoryService extends TreeService<ApparatusCategoryDao, ApparatusCategory> {

    @Override
    public List<ApparatusCategory> findList(ApparatusCategory entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public ApparatusCategory get(String id) {
        ApparatusCategory c = this.dao.get(id);
        if(c != null && c.getParent()!=null && StringUtils.isNotBlank(c.getParent().getId())){
            c.setParent(this.dao.get(c.getParentId()));
        }
        return c;
    }
}
