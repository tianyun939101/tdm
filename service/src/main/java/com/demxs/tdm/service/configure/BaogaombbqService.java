package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.BaogaombbqDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Baogaombbq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报告模板标签Service
 * @author 张仁
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BaogaombbqService extends CrudService<BaogaombbqDao, Baogaombbq> {

	@Override
	public Baogaombbq get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Baogaombbq> findList(Baogaombbq baogaombbq) {
		return super.findList(baogaombbq);
	}
	
	@Override
	public Page<Baogaombbq> findPage(Page<Baogaombbq> page, Baogaombbq baogaombbq) {
		return super.findPage(page, baogaombbq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Baogaombbq baogaombbq) {
		super.save(baogaombbq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Baogaombbq baogaombbq) {
		super.delete(baogaombbq);
	}
	
}