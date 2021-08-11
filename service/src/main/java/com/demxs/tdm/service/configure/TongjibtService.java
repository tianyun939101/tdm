package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.TongjibtDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Tongjibt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统计标题Service
 * @author 张仁
 * @version 2017-08-07
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TongjibtService extends CrudService<TongjibtDao, Tongjibt> {
	@Autowired
	TongjibtDao tongjibtDao;
	@Override
	public Tongjibt get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Tongjibt> findList(Tongjibt tongjibt) {
		return super.findList(tongjibt);
	}
	
	@Override
	public Page<Tongjibt> findPage(Page<Tongjibt> page, Tongjibt tongjibt) {
		return super.findPage(page, tongjibt);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Tongjibt tongjibt) {
		super.save(tongjibt);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Tongjibt tongjibt) {
		super.delete(tongjibt);
	}

	public Object getMaxShunxu(String tongjiid){
		return tongjibtDao.getMaxShunxu(tongjiid);
	}
}