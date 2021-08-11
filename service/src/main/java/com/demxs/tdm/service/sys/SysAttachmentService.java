package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.SysAttachmentDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.sys.SysAttachment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 附件管理Service
 * @author 张仁
 * @version 2017-07-01
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class SysAttachmentService extends CrudService<SysAttachmentDao, SysAttachment> {

	@Override
	public SysAttachment get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<SysAttachment> findList(SysAttachment sysAttachment) {
		return super.findList(sysAttachment);
	}
	
	@Override
	public Page<SysAttachment> findPage(Page<SysAttachment> page, SysAttachment sysAttachment) {
		return super.findPage(page, sysAttachment);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(SysAttachment sysAttachment) {
		super.save(sysAttachment);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(SysAttachment sysAttachment) {
		super.delete(sysAttachment);
	}
	
}