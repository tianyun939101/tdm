package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinCygcsDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.yangpin.YangpinCygcs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品拆样工程师Service
 * @author 詹小梅
 * @version 2017-07-19
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinCygcsService extends CrudService<YangpinCygcsDao, YangpinCygcs> {

	@Override
	public YangpinCygcs get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinCygcs> findList(YangpinCygcs yangpinCygcs) {
		return super.findList(yangpinCygcs);
	}
	
	@Override
	public Page<YangpinCygcs> findPage(Page<YangpinCygcs> page, YangpinCygcs yangpinCygcs) {
		return super.findPage(page, yangpinCygcs);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinCygcs yangpinCygcs) {
		super.save(yangpinCygcs);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinCygcs yangpinCygcs) {
		super.delete(yangpinCygcs);
	}
	
}