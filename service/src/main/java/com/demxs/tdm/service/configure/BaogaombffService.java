package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.BaogaombffDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Baogaombff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报告模板方法关系Service
 * @author 张仁
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BaogaombffService extends CrudService<BaogaombffDao, Baogaombff> {

	@Override
	public Baogaombff get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Baogaombff> findList(Baogaombff baogaombff) {
		return super.findList(baogaombff);
	}
	
	@Override
	public Page<Baogaombff> findPage(Page<Baogaombff> page, Baogaombff baogaombff) {
		return super.findPage(page, baogaombff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Baogaombff baogaombff) {
		super.save(baogaombff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Baogaombff baogaombff) {
		super.delete(baogaombff);
	}
	
}