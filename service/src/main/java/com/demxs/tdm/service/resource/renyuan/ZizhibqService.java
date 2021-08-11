package com.demxs.tdm.service.resource.renyuan;

import com.demxs.tdm.dao.resource.renyuan.ZizhibqDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.renyuan.Zizhibq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资质标签操作Service
 * @author 谭冬梅
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZizhibqService extends CrudService<ZizhibqDao, Zizhibq> {

	@Override
	public Zizhibq get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Zizhibq> findList(Zizhibq zizhibq) {
		return super.findList(zizhibq);
	}
	
	@Override
	public Page<Zizhibq> findPage(Page<Zizhibq> page, Zizhibq zizhibq) {
		/*if(UserUtils.getUser()!=null){
			//zizhibq.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			zizhibq.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}*/
		return super.findPage(page, zizhibq);
	}

	public Page<Zizhibq> findPageForOther(Page<Zizhibq> page, Zizhibq zizhibq) {
		page.setOrderBy("a.update_date desc");
		return super.findPage(page, zizhibq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Zizhibq zizhibq) {
		super.save(zizhibq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Zizhibq zizhibq) {
		super.delete(zizhibq);
	}
	
}