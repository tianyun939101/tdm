package com.demxs.tdm.service.configure;

import java.util.List;

import com.demxs.tdm.dao.configure.ShebeiRuleDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.configure.ShebeiRule;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 设备采集配置Service
 * @author sunjunhui
 * @version 2018-01-08
 */
@Service
@Transactional(readOnly = true)
public class ShebeiRuleService extends CrudService<ShebeiRuleDao, ShebeiRule> {

	public ShebeiRule get(String id) {
		return super.get(id);
	}
	
	public List<ShebeiRule> findList(ShebeiRule shebeiRule) {
		return super.findList(shebeiRule);
	}
	
	public Page<ShebeiRule> findPage(Page<ShebeiRule> page, ShebeiRule shebeiRule) {
		return super.findPage(page, shebeiRule);
	}
	
	@Transactional(readOnly = false)
	public void save(ShebeiRule shebeiRule) {
		super.save(shebeiRule);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShebeiRule shebeiRule) {
		super.delete(shebeiRule);
	}
	
}