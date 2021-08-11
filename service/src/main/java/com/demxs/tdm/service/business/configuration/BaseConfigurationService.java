/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.demxs.tdm.service.business.configuration;


import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.configuration.BaseConfigurationDao;
import com.demxs.tdm.domain.business.configuration.BaseConfiguration;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 构型基础信息Service
 * @author neo
 * @version 2020-02-18
 */
@Service
@Transactional(readOnly = true)
public class BaseConfigurationService extends CrudService<BaseConfigurationDao, BaseConfiguration> {

	public BaseConfiguration get(String id) {
		return super.get(id);
	}
	
	public List<BaseConfiguration> findList(BaseConfiguration baseConfiguration) {
		return super.findList(baseConfiguration);
	}
	
	public Page<BaseConfiguration> findPage(Page<BaseConfiguration> page, BaseConfiguration baseConfiguration) {
		return super.findPage(page, baseConfiguration);
	}
	
	@Transactional(readOnly = false)
	public void save(BaseConfiguration baseConfiguration) {
		super.save(baseConfiguration);
	}

	@Transactional(readOnly = false)
	public int updateBase(BaseConfiguration baseConfiguration){
		return super.dao.updateBase(baseConfiguration);
	}

	@Transactional(readOnly = false)
	public int changeCurVersion(BaseConfiguration baseConfiguration){
		return super.dao.changeCurVersion(baseConfiguration);
	}

	@Transactional(readOnly = false)
	public int changeEditVersion(BaseConfiguration baseConfiguration){
		return super.dao.changeEditVersion(baseConfiguration);
	}
	
	@Transactional(readOnly = false)
	public void delete(BaseConfiguration baseConfiguration) {
		super.delete(baseConfiguration);
	}

	public List<BaseConfiguration> getCurVersionDetail(BaseConfiguration baseConfiguration){
	    return super.dao.findCurVersionDetail(baseConfiguration);
    }
	
}