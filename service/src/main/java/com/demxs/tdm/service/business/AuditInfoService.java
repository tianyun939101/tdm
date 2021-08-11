package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.AuditInfoDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.AuditInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 申请信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class AuditInfoService extends CrudService<AuditInfoDao, AuditInfo> {

	@Override
	public AuditInfo get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<AuditInfo> findList(AuditInfo auditInfo) {
		return super.findList(auditInfo);
	}
	
	@Override
	public Page<AuditInfo> findPage(Page<AuditInfo> page, AuditInfo auditInfo) {
		return super.findPage(page, auditInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(AuditInfo auditInfo) {
		super.save(auditInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(AuditInfo auditInfo) {
		super.delete(auditInfo);
	}

	public AuditInfo getByType(AuditInfo auditInfo){
		return this.dao.getByType(auditInfo);
	}

	public List<AuditInfo> getByKey(String key){
	    return this.dao.getByKey(key);
    }
}