package com.demxs.tdm.service.ability;

import com.demxs.tdm.dao.ability.TestSequenceItemDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.ability.TestSequenceItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 试验序列详情Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class TestSequenceItemService extends CrudService<TestSequenceItemDao, TestSequenceItem> {

	@Override
	public TestSequenceItem get(String id) {
		return super.get(id);
	}

	/**
	 * 根据nodeId 查询对应记录
	 * @param nodeId
	 * @return
	 */
	public TestSequenceItem getByNodeId( String nodeId){
		return dao.getByNodeId(nodeId);
	}
	
	@Override
	public List<TestSequenceItem> findList(TestSequenceItem testSequenceItem) {
		return super.findList(testSequenceItem);
	}
	
	@Override
	public Page<TestSequenceItem> findPage(Page<TestSequenceItem> page, TestSequenceItem testSequenceItem) {
		return super.findPage(page, testSequenceItem);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(TestSequenceItem testSequenceItem) {
		super.save(testSequenceItem);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(TestSequenceItem testSequenceItem) {
		super.delete(testSequenceItem);

	}
	/**
	 * 根据序列ID 获取序列项内容
	 * @param seqId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TestSequenceItem> findBySeqId(String seqId){
		return dao.findBySeqId(seqId);
	}
	/**
	 * 根据序列ID 获取序列项详细内容，试验项目包含试验项详情等内容
	 * @param seqId
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TestSequenceItem> detailBySeqId(String seqId){
		return dao.detailBySeqId(seqId);
	}


	/**
	 * 删除试验项目所有关联项
	 * @param seqId
	 */
	@Transactional
	public void deleteBySeqId( String seqId){
		dao.deleteBySeqId(seqId);
	}
}