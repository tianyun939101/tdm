package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.YuanshijlmbbqDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Yuanshijlmbbq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 原始记录模板标签Service
 * @author 张仁
 * @version 2017-06-09
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YuanshijlmbbqService extends CrudService<YuanshijlmbbqDao, Yuanshijlmbbq> {

	@Override
	public Yuanshijlmbbq get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Yuanshijlmbbq> findList(Yuanshijlmbbq yuanshijlmbbq) {
		return super.findList(yuanshijlmbbq);
	}
	@Override
	public Page<Yuanshijlmbbq> findPage(Page<Yuanshijlmbbq> page, Yuanshijlmbbq yuanshijlmbbq) {
		return super.findPage(page, yuanshijlmbbq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Yuanshijlmbbq yuanshijlmbbq) {
		super.save(yuanshijlmbbq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Yuanshijlmbbq yuanshijlmbbq) {
		super.delete(yuanshijlmbbq);
	}
	
}