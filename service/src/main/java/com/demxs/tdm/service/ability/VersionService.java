package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.VersionDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.Version;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 版本号Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class VersionService extends CrudService<VersionDao, Version> {

	@Override
	public Version get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Version> findList(Version version) {
		return super.findList(version);
	}
	
	@Override
	public Page<Version> findPage(Page<Version> page, Version version) {
		return super.findPage(page, version);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Version version) {
		super.save(version);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Version version) {
		super.delete(version);
	}
	
}