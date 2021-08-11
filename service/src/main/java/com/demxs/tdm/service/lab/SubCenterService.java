package com.demxs.tdm.service.lab;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.lab.SubCenterDao;
import com.demxs.tdm.domain.lab.SubCenter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/18 16:39
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class SubCenterService extends CrudService<SubCenterDao, SubCenter> {

    public SubCenter getByName(SubCenter subCenter){
        return this.dao.getByName(subCenter);
    }

    @Override
    public void save(SubCenter entity) {
        if(StringUtils.isNotBlank(entity.getId())){
            SubCenter byName = this.dao.getByName(entity);
            if(null != byName && !byName.getId().equals(entity.getId())){
                throw new ServiceException("该名称已存在：" + entity.getName());
            }
        }else{
            if(this.dao.getByName(entity) != null){
                throw new ServiceException("该名称已存在：" + entity.getName());
            }
        }
        super.save(entity);
    }


    public List<String> getAllSubCenterName(){
        return this.dao.findAllSubCenterName();
    }

}
