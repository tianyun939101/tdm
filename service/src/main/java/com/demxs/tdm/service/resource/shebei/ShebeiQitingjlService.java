package com.demxs.tdm.service.resource.shebei;

import java.util.List;

import com.demxs.tdm.dao.resource.shebei.ShebeiQitingjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.shebei.ShebeiQitingjl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 设备启停记录Service
 * @author sunjunhui
 * @version 2017-11-03
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiQitingjlService extends CrudService<ShebeiQitingjlDao, ShebeiQitingjl> {

	@Override
	public ShebeiQitingjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ShebeiQitingjl> findList(ShebeiQitingjl shebeiQitingjl) {
		return super.findList(shebeiQitingjl);
	}
	
	@Override
	public Page<ShebeiQitingjl> findPage(Page<ShebeiQitingjl> page, ShebeiQitingjl shebeiQitingjl) {
		return super.findPage(page, shebeiQitingjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiQitingjl shebeiQitingjl) {
		super.save(shebeiQitingjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiQitingjl shebeiQitingjl) {
		super.delete(shebeiQitingjl);
	}

	public List<ShebeiQitingjl> findListByType(String shebeiid,String type) {
		return this.dao.findListByType(shebeiid,type);
	}


	public void updateValidById(String id){
		this.dao.updateValidById(id);
	}


	public List<ShebeiQitingjl> findListByTypeValid(String shebeiid,String type){
		return this.dao.findListByTypeValid(shebeiid,type);
	}

}