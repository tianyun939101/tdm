package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.BaobiaoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Baobiao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报表Service
 * @author 张仁
 * @version 2017-08-04
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BaobiaoService extends CrudService<BaobiaoDao, Baobiao> {

	@Override
	public Baobiao get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Baobiao> findList(Baobiao baobiao) {
		return super.findList(baobiao);
	}
	
	@Override
	public Page<Baobiao> findPage(Page<Baobiao> page, Baobiao baobiao) {
		return super.findPage(page, baobiao);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Baobiao baobiao) {
		super.save(baobiao);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Baobiao baobiao) {
		super.delete(baobiao);
	}
	
}