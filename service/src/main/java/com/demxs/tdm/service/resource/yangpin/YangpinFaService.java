package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinFaDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.yangpin.YangpinFa;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品方案Service
 * @author 詹小梅
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinFaService extends CrudService<YangpinFaDao, YangpinFa> {

	@Override
	public YangpinFa get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinFa> findList(YangpinFa yangpinFa) {
		return super.findList(yangpinFa);
	}
	
	@Override
	public Page<YangpinFa> findPage(Page<YangpinFa> page, YangpinFa yangpinFa) {
		return super.findPage(page, yangpinFa);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinFa yangpinFa) {
		super.save(yangpinFa);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinFa yangpinFa) {
		super.delete(yangpinFa);
	}
	
}