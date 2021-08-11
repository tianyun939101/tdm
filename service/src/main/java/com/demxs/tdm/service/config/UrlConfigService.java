package com.demxs.tdm.service.config;

import java.util.List;

import com.demxs.tdm.dao.config.UrlConfigDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.config.UrlConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * url配置Service
 * @author sunjunhui
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class UrlConfigService extends CrudService<UrlConfigDao, UrlConfig> {

	public UrlConfig get(String id) {
		return super.get(id);
	}
	
	public List<UrlConfig> findList(UrlConfig urlConfig) {
		return super.findList(urlConfig);
	}
	
	public Page<UrlConfig> findPage(Page<UrlConfig> page, UrlConfig urlConfig) {
		return super.findPage(page, urlConfig);
	}
	
	@Transactional(readOnly = false)
	public void save(UrlConfig urlConfig) {
		super.save(urlConfig);
	}
	
	@Transactional(readOnly = false)
	public void delete(UrlConfig urlConfig) {
		super.delete(urlConfig);
	}
	
}