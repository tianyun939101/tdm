package com.demxs.tdm.service.resource.yuangong;

import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.FreeMarkers;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.resource.yuangong.LaborStaffingDao;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.constant.EntrustConstants;
import com.demxs.tdm.domain.resource.constant.yuangong.StaffingConstant;
import com.demxs.tdm.domain.resource.yuangong.LaborStaffing;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.service.ability.AptitudeService;
import com.demxs.tdm.service.business.AuditInfoService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 科室间用工协调
 */
@Service
@Transactional(readOnly = true)
public class LaborStaffingService extends CrudService<LaborStaffingDao, LaborStaffing> {

	@Autowired
	private LabInfoService labInfoService;

	@Autowired
	private ActTaskService actTaskService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private AuditInfoService auditInfoService;

	@Autowired
	private AptitudeService aptitudeService;

	@Autowired
	private YuangongService yuangongService;

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(LaborStaffing laborStaffing){
		User user = UserUtils.getUser();
		laborStaffing.setApplicant(user.getId());
		laborStaffing.setStatus(StaffingConstant.laborStaffingStatus.APPLY);
		Map<String,String> m = getAuditByZz(laborStaffing.getAptitude().getId());
		laborStaffing.setZzLabLeader(m.get("ids"));
		laborStaffing.setZzLabLeaderName(m.get("names"));
		super.save(laborStaffing);

	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void submit(LaborStaffing laborStaffing){
		User user = UserUtils.getUser();
		laborStaffing.setApplicant(user.getId());
		laborStaffing.setStatus(StaffingConstant.laborStaffingStatus.AUDIT);
		Map<String,String> m = getAuditByZz(laborStaffing.getAptitude().getId());
		laborStaffing.setZzLabLeader(m.get("ids"));
		laborStaffing.setZzLabLeaderName(m.get("names"));
		super.save(laborStaffing);

		//发起流程
		User audit = labInfoService.get(laborStaffing.getLabInfo().getId()).getLeader();
		User createUser = systemService.getUser(laborStaffing.getCreateById());
		Aptitude aptitude = aptitudeService.get(laborStaffing.getAptitude().getId());
		String taskTile = createUser.getName()+"用工申请："+aptitude.getName()+"资质"+laborStaffing.getQuantity()+"人";
		Map<String,Object> model = new HashMap<>();
		model.put("userName",createUser.getName());
		String title = FreeMarkers.renderString(StaffingConstant.LaborStaffingMessage.DEAL_WITH,model);

		Map<String,Object> vars = new HashMap<>();
		vars.put("message", title);
		//vars.put("code", laborStaffing.getLabInfo().getName()==null?"":laborStaffing.getLabInfo().getName());
		actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.LABOR_STAFFING,
				audit.getLoginName(),laborStaffing.getId(),taskTile,vars);
	}

	private Map<String,String> getAuditByZz(String zzId){
		//查询有资质的试验室
		List<Yuangong> list = yuangongService.findList(new Yuangong());
		HashSet hs = new HashSet();
		for(Yuangong y : list){
			if(StringUtils.isNotBlank(y.getZzIds())){
				String[] split = y.getZzIds().split(",");
				for(String s : split){
					if(zzId.equals(s)){
						if(StringUtils.isNotBlank(y.getLabInfoId())){
							hs.add(y.getLabInfoId());
						}
					}
				}
			}
		}
		List nameList = new ArrayList();
		List idList = new ArrayList();
		Iterator it = hs.iterator();
		while(it.hasNext()){
			String labId = (String)it.next();
			nameList.add(labInfoService.get(labId).getLeader().getName());
			idList.add(labInfoService.get(labId).getLeader().getId());
		}
		Map map = new HashMap();
		String names = StringUtils.join(nameList,",");
		String ids = StringUtils.join(idList,",");
		map.put("names",names);
		map.put("ids",ids);
		return map;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(LaborStaffing laborStaffing){
		super.delete(laborStaffing);
	}

	/**
	 * 审核
	 * @param id
	 * @param auditInfo
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void audit(String id, AuditInfo auditInfo) {
		Boolean finish = false;
		LaborStaffing laborStaffing = super.dao.get(id);
		StringBuilder auditor = new StringBuilder();
		User audit = UserUtils.getUser();//暂用当前人
		List<String> auditList = new ArrayList<>();
		Map<String, Object> model = new HashMap<>();
		User createUser = systemService.getUser(laborStaffing.getCreateById());
		model.put("userName", createUser.getName());
		String title = "";
		if (laborStaffing != null) {
			//审核通过
			if (EntrustConstants.AuditResult.PASS.equals(auditInfo.getResult())) {
				auditInfo.setReason(StringUtils.isEmpty(auditInfo.getReason()) ? "同意" : auditInfo.getReason());
				title = FreeMarkers.renderString(StaffingConstant.LaborStaffingMessage.AUDIT_PASS, model);
				//修改当前状态
				if(StaffingConstant.laborStaffingStatus.AUDIT.equals(laborStaffing.getStatus())){
					laborStaffing.setStatus(StaffingConstant.laborStaffingStatus.APPROVAL);
					String[] arr = laborStaffing.getZzLabLeader().split(",");

					for(String s: arr){
						User user = systemService.getUser(s);
						auditList.add(user.getLoginName());
					}
				}else if(StaffingConstant.laborStaffingStatus.APPROVAL.equals(laborStaffing.getStatus())){
					finish = true;
					laborStaffing.setStatus(StaffingConstant.laborStaffingStatus.FINISH);
				}else{

				}
			}
			//审核不通过
			if (EntrustConstants.AuditResult.RETURN.equals(auditInfo.getResult())) {
				laborStaffing.setStatus(StaffingConstant.laborStaffingStatus.APPLY);
				title = FreeMarkers.renderString(StaffingConstant.LaborStaffingMessage.AUDIT_RETURN, model);
			}
			super.save(laborStaffing);
			//设置审核信息
			auditInfo.setBusinessKey(id);
			auditInfo.setAuditUser(UserUtils.getUser());
			auditInfoService.save(auditInfo);
			//流程
			Map<String, Object> vars = new HashMap<>();
			vars.put("message", title);
			actTaskService.apiComplete(id, auditInfo.getReason(), auditInfo.getResult() + "", finish?null:StringUtils.join(auditList,";"), finish?null:vars);
		}
	}
}