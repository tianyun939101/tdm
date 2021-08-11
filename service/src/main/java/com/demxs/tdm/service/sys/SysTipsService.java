package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.SysTipsDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.sys.SysTips;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 系统提示信息配置Service
 * @author 谭冬梅
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class SysTipsService extends CrudService<SysTipsDao, SysTips> {

	@Override
	public SysTips get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<SysTips> findList(SysTips sysTips) {
		return super.findList(sysTips);
	}
	
	@Override
	public Page<SysTips> findPage(Page<SysTips> page, SysTips sysTips) {
		return super.findPage(page, sysTips);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(SysTips sysTips) {
		super.save(sysTips);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(SysTips sysTips) {
		super.delete(sysTips);
	}

	public List<Map> findListBySql(String sql){
		return dao.findListBySql(sql);
	}
}