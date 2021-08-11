package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.AdjustPlanLogDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.business.AdjustPlanLog;
import com.demxs.tdm.domain.business.EntrustInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 调整计划日志Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class AdjustPlanLogService extends CrudService<AdjustPlanLogDao, AdjustPlanLog> {

	@Override
	public AdjustPlanLog get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<AdjustPlanLog> findList(AdjustPlanLog auditInfo) {
		return super.findList(auditInfo);
	}
	
	@Override
	public Page<AdjustPlanLog> findPage(Page<AdjustPlanLog> page, AdjustPlanLog auditInfo) {
		return super.findPage(page, auditInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(AdjustPlanLog auditInfo) {
		super.save(auditInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(AdjustPlanLog auditInfo) {
		super.delete(auditInfo);
	}


	public void savePlanLog(String labId,List<EntrustInfo> entrustInfos,Date startDate,Date endDate){
		if(CollectionUtils.isNotEmpty(entrustInfos)){
			for(EntrustInfo e:entrustInfos){
				AdjustPlanLog adjustPlanLog = new AdjustPlanLog(e);
				adjustPlanLog.setLabId(labId);
				adjustPlanLog.setAdjustStartDate(startDate);
				adjustPlanLog.setAdjustEndDate(endDate);
				adjustPlanLog.setAdjustName(UserUtils.getUser().getName());
				save(adjustPlanLog);
			}
		}
	}
}