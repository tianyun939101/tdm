package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.TongjitjDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Tongjitj;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统计条件Service
 * @author 张仁
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TongjitjService extends CrudService<TongjitjDao, Tongjitj> {

	@Override
	public Tongjitj get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Tongjitj> findList(Tongjitj tongjitj) {
		return super.findList(tongjitj);
	}
	
	@Override
	public Page<Tongjitj> findPage(Page<Tongjitj> page, Tongjitj tongjitj) {
		return super.findPage(page, tongjitj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Tongjitj tongjitj) {
		super.save(tongjitj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Tongjitj tongjitj) {
		super.delete(tongjitj);
	}
	
}