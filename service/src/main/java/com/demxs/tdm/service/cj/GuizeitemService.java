package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.GuizeitemDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.cj.Guizeitem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 采集规则项Service
 * @author 张仁
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class GuizeitemService extends CrudService<GuizeitemDao, Guizeitem> {
	@Autowired
	private GuizeitemDao guizeitemDao;
	@Override
	public Guizeitem get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Guizeitem> findList(Guizeitem guizeitem) {
		return super.findList(guizeitem);
	}
	
	@Override
	public Page<Guizeitem> findPage(Page<Guizeitem> page, Guizeitem guizeitem) {
		return super.findPage(page, guizeitem);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(Guizeitem guizeitem) {
		super.save(guizeitem);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Guizeitem guizeitem) {
		super.delete(guizeitem);
	}

	public void deleteids(String ids) {
		String[] arrayid = ids.split(",");
		for(int i=0;i<arrayid.length;i++){
			guizeitemDao.delete(arrayid[i]);
		}
	}
}