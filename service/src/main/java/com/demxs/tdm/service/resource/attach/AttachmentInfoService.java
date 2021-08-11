package com.demxs.tdm.service.resource.attach;

import java.util.List;

import com.demxs.tdm.dao.resource.attach.AttachmentInfoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 附件管理Service
 * @author sunjunhui
 * @version 2017-11-23
 */
@Service
@Transactional(readOnly = true)
public class AttachmentInfoService extends CrudService<AttachmentInfoDao, AttachmentInfo> {

	public AttachmentInfo get(String id) {
		return super.get(id);
	}
	
	public List<AttachmentInfo> findList(AttachmentInfo attachment) {
		return super.findList(attachment);
	}
	
	public Page<AttachmentInfo> findPage(Page<AttachmentInfo> page, AttachmentInfo attachment) {
		return super.findPage(page, attachment);
	}
	
	@Transactional(readOnly = false)
	public void save(AttachmentInfo attachment) {
		super.save(attachment);
	}
	
	@Transactional(readOnly = false)
	public void delete(AttachmentInfo attachment) {
		super.delete(attachment);
	}

	@Transactional(readOnly = false)
	public void updateBusinessId(String id,String businessId){
		this.dao.updateBusinessId(id,businessId);
	}
	
}