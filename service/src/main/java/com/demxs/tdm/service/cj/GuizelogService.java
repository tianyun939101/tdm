package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.GuizelogDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.cj.Guizelog;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 采集规则日志Service
 * @author 张仁
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class GuizelogService extends CrudService<GuizelogDao, Guizelog> {

	@Override
	public Guizelog get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Guizelog> findList(Guizelog guizelog) {
		return super.findList(guizelog);
	}
	
	@Override
	public Page<Guizelog> findPage(Page<Guizelog> page, Guizelog guizelog) {
		return super.findPage(page, guizelog);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(Guizelog guizelog) {
		super.save(guizelog);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Guizelog guizelog) {
		super.delete(guizelog);
	}
	
}