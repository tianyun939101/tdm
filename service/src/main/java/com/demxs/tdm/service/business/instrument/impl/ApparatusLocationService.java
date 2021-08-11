package com.demxs.tdm.service.business.instrument.impl;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.instrument.ApparatusLocationDao;
import com.demxs.tdm.domain.business.instrument.ApparatusLocation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/5/6 16:57
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class ApparatusLocationService extends TreeService<ApparatusLocationDao, ApparatusLocation> {

    @Override
    public List<ApparatusLocation> findList(ApparatusLocation entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }

    @Override
    public ApparatusLocation get(String id) {
        ApparatusLocation c = this.dao.get(id);
        if(c != null && c.getParent()!=null && StringUtils.isNotBlank(c.getParent().getId())){
            c.setParent(this.dao.get(c.getParentId()));
        }
        return c;
    }
}
