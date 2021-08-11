package com.demxs.tdm.service.sys;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.sys.dao.AreaDao;
import com.demxs.tdm.common.sys.entity.Area;
import com.demxs.tdm.common.sys.utils.UserUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 区域Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}
