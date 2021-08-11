package com.demxs.tdm.service.business.configuration;

import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.business.configuration.ConfigurationPositionDao;
import com.demxs.tdm.domain.business.configuration.ConfigurationPosition;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationPositionService extends TreeService<ConfigurationPositionDao, ConfigurationPosition> {

    public List<ConfigurationPosition> findAllList(ConfigurationPosition configurationPosition){
        return super.dao.findAllList(configurationPosition);
    }

    @Override
    public ConfigurationPosition get(String id) {
        ConfigurationPosition position = this.dao.get(id);
        if(position.getParent() != null && StringUtils.isNotBlank(position.getParent().getId())){
            position.setParent(this.dao.get(position.getParent().getId()));
        }
        return position;
    }

    @Override
    public List<ConfigurationPosition> findList(ConfigurationPosition entity) {
        if (StringUtils.isNotBlank(entity.getParentIds())){
            entity.setParentIds(","+entity.getParentIds()+",");
        }
        return super.findList(entity);
    }
}
