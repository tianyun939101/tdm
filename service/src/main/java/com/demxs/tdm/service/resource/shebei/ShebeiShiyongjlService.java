package com.demxs.tdm.service.resource.shebei;

import java.util.List;

import com.demxs.tdm.dao.resource.shebei.ShebeiShiyongjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.shebei.ShebeiShiyongjl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 设备使用记录Service
 * @author sunjunhui
 * @version 2017-11-03
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiShiyongjlService extends CrudService<ShebeiShiyongjlDao, ShebeiShiyongjl> {

	@Override
	public ShebeiShiyongjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ShebeiShiyongjl> findList(ShebeiShiyongjl shebeiShiyongjl) {
		return super.findList(shebeiShiyongjl);
	}
	
	@Override
	public Page<ShebeiShiyongjl> findPage(Page<ShebeiShiyongjl> page, ShebeiShiyongjl shebeiShiyongjl) {
		return super.findPage(page, shebeiShiyongjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiShiyongjl shebeiShiyongjl) {
		super.save(shebeiShiyongjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiShiyongjl shebeiShiyongjl) {
		super.delete(shebeiShiyongjl);
	}
	
}