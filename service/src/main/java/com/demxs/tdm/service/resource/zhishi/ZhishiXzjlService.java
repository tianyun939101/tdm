package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiXzjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXzjl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识下载记录Service
 * @author 詹小梅
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiXzjlService extends CrudService<ZhishiXzjlDao, ZhishiXzjl> {

	@Override
	public ZhishiXzjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ZhishiXzjl> findList(ZhishiXzjl zhishiXzjl) {
		return super.findList(zhishiXzjl);
	}
	
	@Override
	public Page<ZhishiXzjl> findPage(Page<ZhishiXzjl> page, ZhishiXzjl zhishiXzjl) {
		return super.findPage(page, zhishiXzjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZhishiXzjl zhishiXzjl) {
		super.save(zhishiXzjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiXzjl zhishiXzjl) {
		super.delete(zhishiXzjl);
	}
	
}