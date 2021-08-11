package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.TestTaskRecordDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.TestTaskRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 试验任务原始记录单Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestTaskRecordService extends CrudService<TestTaskRecordDao, TestTaskRecord> {

	@Override
	public TestTaskRecord get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<TestTaskRecord> findList(TestTaskRecord testTaskRecord) {
		return super.findList(testTaskRecord);
	}
	
	@Override
	public Page<TestTaskRecord> findPage(Page<TestTaskRecord> page, TestTaskRecord testTaskRecord) {
		return super.findPage(page, testTaskRecord);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestTaskRecord testTaskRecord) {
		super.save(testTaskRecord);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestTaskRecord testTaskRecord) {
		super.delete(testTaskRecord);
	}
	
}