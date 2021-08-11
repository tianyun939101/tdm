package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.JoininfoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Joininfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 关联表Service
 * @author 张仁
 * @version 2017-08-05
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class JoininfoService extends CrudService<JoininfoDao, Joininfo> {

	@Override
	public Joininfo get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Joininfo> findList(Joininfo joininfo) {
		return super.findList(joininfo);
	}
	
	@Override
	public Page<Joininfo> findPage(Page<Joininfo> page, Joininfo joininfo) {
		return super.findPage(page, joininfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Joininfo joininfo) {
		super.save(joininfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Joininfo joininfo) {
		super.delete(joininfo);
	}
	
}