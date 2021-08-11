package com.demxs.tdm.service.resource.sample;

import java.util.List;

import com.demxs.tdm.dao.resource.sample.SampleOperateDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.resource.sample.SampleOperate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 样品操作记录信息Service
 * @author sunjunhui
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class SampleOperateService extends CrudService<SampleOperateDao, SampleOperate> {

	public SampleOperate get(String id) {
		return super.get(id);
	}
	
	public List<SampleOperate> findList(SampleOperate sampleOperate) {
		return super.findList(sampleOperate);
	}
	
	public Page<SampleOperate> findPage(Page<SampleOperate> page, SampleOperate sampleOperate) {
		sampleOperate.getSqlMap().put("dsf", dataScopeFilter(sampleOperate.getCurrentUser(), "o", "u8"));
		return super.findPage(page, sampleOperate);
	}
	
	@Transactional(readOnly = false)
	public void save(SampleOperate sampleOperate) {
		super.save(sampleOperate);
	}
	
	@Transactional(readOnly = false)
	public void delete(SampleOperate sampleOperate) {
		super.delete(sampleOperate);
	}
	
}