package com.demxs.tdm.service.dataCenter.parse;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.parse.DataParseRuleDao;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 数据解析规则Service
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataParseRuleService extends CrudService<DataParseRuleDao, DataParseRule> {

	@Override
	public DataParseRule get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<DataParseRule> findList(DataParseRule dataParseRule) {
		return super.findList(dataParseRule);
	}
	
	@Override
	public Page<DataParseRule> findPage(Page<DataParseRule> page, DataParseRule dataParseRule) {
		return super.findPage(page, dataParseRule);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(DataParseRule dataParseRule) {
		super.save(dataParseRule);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(DataParseRule dataParseRule) {
		super.delete(dataParseRule);
	}

	
}