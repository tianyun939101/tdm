package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.EntrustInfoDao;
import com.demxs.tdm.dao.business.EntrustReportDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.EntrustInfo;
import com.demxs.tdm.domain.business.EntrustReport;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 申请报告Service
 * @author 吴列鹏
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustReportService extends CrudService<EntrustReportDao, EntrustReport> {

	@Autowired
	private EntrustInfoDao entrustInfoDao;

	@Override
	public EntrustReport get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<EntrustReport> findList(EntrustReport entrustReport) {
		return super.findList(entrustReport);
	}
	
	@Override
	public Page<EntrustReport> findPage(Page<EntrustReport> page, EntrustReport entrustReport) {
		return super.findPage(page, entrustReport);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(EntrustReport entrustReport) {
		super.save(entrustReport);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(String entrustId,String reportFile) {
		EntrustReport report = getByEntrustId(entrustId);
		if(report == null){
			EntrustInfo entrustInfo = entrustInfoDao.get(entrustId);
			entrustInfo.setStatus(EntrustConstants.ReportStatus.AUDIT);
			entrustInfoDao.update(entrustInfo);
			report = new EntrustReport();
			report.setEntrustInfo(entrustInfo);
			report.setReportFile(reportFile);
			// TODO: 18/1/12 设备报告审核(试验室技术负责人)与批准人(试验室主管)
			//report.setAuditUser();
			//report.setApprovalUser();
		}else{
			report.setReportFile(reportFile);
		}
		this.save(report);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void audit(String reportId, String reportFile, AuditInfo auditInfo) {
		EntrustReport report = this.get(reportId);
		if(report == null){
			throw new ServiceException("报告不存在");
		}
		EntrustInfo entrustInfo = entrustInfoDao.get(report.getEntrustInfo().getId());
		entrustInfo.setStatus(EntrustConstants.ReportStatus.REJECT);
		entrustInfoDao.update(entrustInfo);
		report.setAuditDate(new Date());
		report.setReportFile(reportFile);
		this.save(report);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void reject(String reportId, String reportFile, AuditInfo auditInfo) {
		EntrustReport report = this.get(reportId);
		if(report == null){
			throw new ServiceException("报告不存在");
		}
		EntrustInfo entrustInfo = entrustInfoDao.get(report.getEntrustInfo().getId());
		entrustInfo.setStatus(EntrustConstants.FinishStage.DONE);
		entrustInfoDao.update(entrustInfo);
		report.setApprovalDate(new Date());
		report.setReportFile(reportFile);
		this.save(report);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(EntrustReport entrustReport) {
		super.delete(entrustReport);
	}

	/**
	 * 根据申请单加载申请单其他信息
	 * @param entrustId 申请单ID
	 * @return
	 */
	public EntrustReport getByEntrustId(String entrustId) throws ServiceException{
		return this.dao.getByEntrustId(entrustId);
	}

	public void uploadReport(String id, String fileId) {
		this.dao.uploadReport(id,fileId);
	}
}