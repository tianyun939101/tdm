package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinBqDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.yangpin.YangpinBq;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品标签Service
 * @author 詹小梅
 * @version 2017-07-27
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinBqService extends CrudService<YangpinBqDao, YangpinBq>  {

	@Override
	public YangpinBq get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinBq> findList(YangpinBq yangpinBq) {
		return super.findList(yangpinBq);
	}
	
	@Override
	public Page<YangpinBq> findPage(Page<YangpinBq> page, YangpinBq yangpinBq) {
		return super.findPage(page, yangpinBq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinBq yangpinBq) {
		super.save(yangpinBq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinBq yangpinBq) {
		super.delete(yangpinBq);
	}

}