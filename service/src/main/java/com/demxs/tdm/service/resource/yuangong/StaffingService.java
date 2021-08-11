package com.demxs.tdm.service.resource.yuangong;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.resource.yuangong.StaffingDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.resource.constant.yuangong.StaffingConstant;
import com.demxs.tdm.domain.resource.yuangong.Staffing;
import com.demxs.tdm.domain.resource.yuangong.StaffingReport;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * 试验人员资源调配
 */
@Service
@Transactional(readOnly = true)
public class StaffingService extends CrudService<StaffingDao, Staffing> {

	@Autowired
	private LabInfoService labInfoService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private AuditInfoService auditInfoService;

	@Autowired
	private StaffingReportService staffingReportService;

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Staffing staffing){
		User user = UserUtils.getUser();
		staffing.setApplicant(user.getId());
		staffing.setStatus(StaffingConstant.staffingStatus.APPLY);
		super.save(staffing);

	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void submit(Staffing staffing){
		User user = UserUtils.getUser();
		staffing.setApplicant(user.getId());
		staffing.setStatus(StaffingConstant.staffingStatus.AUDIT);
		super.save(staffing);

		//发起流程
		//User audit = UserUtils.getUser();//暂用当前人，使用申请试验室负责人
		User audit = labInfoService.get(staffing.getLabInfo().getId()).getLeader();
		User createUser = systemService.getUser(staffing.getCreateById());
		String taskTile = createUser.getName() +"用工申请："+staffing.getWorkName();
		Map<String,Object> model = new HashMap<>();
		model.put("userName",createUser.getName());
		model.put("code",staffing.getWorkName()==null?"":staffing.getWorkName());
		String title = FreeMarkers.renderString(StaffingConstant.MessageTemplate.DEAL_WITH,model);

		Map<String,Object> vars = new HashMap<>();
		vars.put("message", title);
		vars.put("code", staffing.getWorkName()==null?"":staffing.getWorkName());
		actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.STAFFING,
				audit.getLoginName(),staffing.getId(),taskTile,vars);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Staffing staffing){
		//删除流程
		super.delete(staffing);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void audit(String id, AuditInfo auditInfo) {
		Staffing staffing = super.dao.get(id);
		//User audit = UserUtils.getUser();//暂用当前人
		User audit = systemService.getUser(staffing.getApplicant());
		Map<String, Object> model = new HashMap<>();
		model.put("code", staffing.getWorkName() == null ? "" : staffing.getWorkName());
		User createUser = systemService.getUser(staffing.getCreateById());
		model.put("userName", createUser.getName());
		String title = "";
		if (staffing != null) {
			//审核通过
			if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
				auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason()) ? "同意" : auditInfo.getReason());
				title = FreeMarkers.renderString(StaffingConstant.MessageTemplate.AUDIT_PASS, model);
				//修改当前状态
				staffing.setStatus(StaffingConstant.staffingStatus.RECORD);
			}
			//审核不通过
			if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
				staffing.setStatus(StaffingConstant.staffingStatus.APPLY);
				title = FreeMarkers.renderString(StaffingConstant.MessageTemplate.AUDIT_RETURN, model);
			}
			super.save(staffing);
			//设置审核信息
			auditInfo.setBusinessKey(id);
			auditInfo.setAuditUser(UserUtils.getUser());
			auditInfoService.save(auditInfo);
			//流程
			Map<String, Object> vars = new HashMap<>();
			vars.put("message", title);
			actTaskService.apiComplete(id, auditInfo.getReason(), auditInfo.getResult() + "", audit.getLoginName(), vars);
		}
	}

	public void saveReport(StaffingReport staffingReport) {
		Staffing staffing = super.get(staffingReport.getStaffingId());
		staffing.setStatus(StaffingConstant.staffingStatus.FINISH);
		super.save(staffing);
		staffingReportService.save(staffingReport);
		actTaskService.apiComplete(staffingReport.getStaffingId(),"","1",null,null);
	}
}