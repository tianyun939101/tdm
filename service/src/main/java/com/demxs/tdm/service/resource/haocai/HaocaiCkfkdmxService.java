package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiCkfkdmxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.haocai.HaocaiCkfkdmx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * &#x8017;&#x6750;&#x51fa;&#x5e93;/&#x8fd4;&#x5e93;&#x5355;&#x660e;&#x7ec6;Service
 * @author zhangdengcai
 * @version 2017-07-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiCkfkdmxService extends CrudService<HaocaiCkfkdmxDao, HaocaiCkfkdmx> {

	@Override
	public HaocaiCkfkdmx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HaocaiCkfkdmx> findList(HaocaiCkfkdmx haocaiCkfkdmx) {
		return super.findList(haocaiCkfkdmx);
	}
	
	@Override
	public Page<HaocaiCkfkdmx> findPage(Page<HaocaiCkfkdmx> page, HaocaiCkfkdmx haocaiCkfkdmx) {
		return super.findPage(page, haocaiCkfkdmx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiCkfkdmx haocaiCkfkdmx) {
		super.save(haocaiCkfkdmx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiCkfkdmx haocaiCkfkdmx) {
		super.delete(haocaiCkfkdmx);
	}
	
}