package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.MailmobanDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Mailmoban;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 邮件模板Service
 * @author 张仁
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class MailmobanService extends CrudService<MailmobanDao, Mailmoban> {

	@Autowired
	private MailmobanDao mailmobanDao;

	@Override
	public Mailmoban get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Mailmoban> findList(Mailmoban mailmoban) {
		return super.findList(mailmoban);
	}
	
	@Override
	public Page<Mailmoban> findPage(Page<Mailmoban> page, Mailmoban mailmoban) {
		return super.findPage(page, mailmoban);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Mailmoban mailmoban) {
		super.save(mailmoban);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Mailmoban mailmoban) {
		super.delete(mailmoban);
	}


}