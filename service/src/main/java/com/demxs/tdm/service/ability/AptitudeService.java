package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.AptitudeDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.Aptitude;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 试验资质Service
 * @author sunjunhui
 * @version 2017-10-31
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class AptitudeService extends CrudService<AptitudeDao, Aptitude> {

	@Autowired
	private  AptitudeDao  aptitudeDao;

	@Override
	public Aptitude get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Aptitude> findList(Aptitude aptitude) {
		return super.findList(aptitude);
	}
	
	@Override
	public Page<Aptitude> findPage(Page<Aptitude> page, Aptitude aptitude) {
		return super.findPage(page, aptitude);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Aptitude aptitude) {
		super.save(aptitude);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Aptitude aptitude) {
		super.delete(aptitude);
	}


	public List<Aptitude> findDataListById(Aptitude  aptitude){
		return aptitudeDao.findDataListById(aptitude);
	}
	
}