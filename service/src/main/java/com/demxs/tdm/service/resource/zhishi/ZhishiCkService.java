package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiCkDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.zhishi.ZhishiCk;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识查看Service
 * @author 詹小梅
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiCkService extends CrudService<ZhishiCkDao, ZhishiCk> {

	@Override
	public ZhishiCk get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ZhishiCk> findList(ZhishiCk zhishiCk) {
		return super.findList(zhishiCk);
	}
	
	@Override
	public Page<ZhishiCk> findPage(Page<ZhishiCk> page, ZhishiCk zhishiCk) {
		return super.findPage(page, zhishiCk);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZhishiCk zhishiCk) {
		super.save(zhishiCk);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiCk zhishiCk) {
		super.delete(zhishiCk);
	}
	
}