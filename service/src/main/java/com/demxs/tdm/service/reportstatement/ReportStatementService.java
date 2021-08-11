package com.demxs.tdm.service.reportstatement;

import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.dao.dataCenter.ExperimentAbilityDao;
import com.demxs.tdm.dao.reportstatement.ReportstatementDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.business.EntrustTestGroupAbility;
import com.demxs.tdm.domain.business.EntrustTestItemCodition;
import com.demxs.tdm.domain.business.TestPlanExecuteDetail;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.reportstatement.*;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import com.demxs.tdm.domain.resource.feiyong.Feiyong;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiLx;
import com.demxs.tdm.domain.resource.shebei.ShebeiQitingjl;
import com.demxs.tdm.domain.sys.LabRole;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.service.business.DevicePlanCaculator;
import com.demxs.tdm.service.business.EntrustTestGroupAbilityService;
import com.demxs.tdm.service.business.EntrustTestItemCoditionService;
import com.demxs.tdm.service.business.TestPlanExecuteDetailService;
import com.demxs.tdm.service.business.data.IDependReportService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.resource.feiyong.FeiyongService;
import com.demxs.tdm.service.resource.haocai.HaocaiLxService;
import com.demxs.tdm.service.resource.shebei.ShebeiLxService;
import com.demxs.tdm.service.resource.shebei.ShebeiQitingjlService;
import com.demxs.tdm.service.resource.shebei.ShebeiService;
import com.demxs.tdm.service.sys.LabRoleService;
import com.demxs.tdm.service.sys.SystemService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by chenjinfan on 2018/2/26.
 */
@Service
public class ReportStatementService {

	@Resource
	public ReportstatementDao reportstatementDao;

	@Resource
	public SystemService systemService;

	@Resource
	public FeiyongService feiyongService;
	@Resource
	public ShebeiService shebeiService;
	@Resource
	public TestPlanExecuteDetailService testPlanExecuteDetailService;
	@Resource
	public ShebeiQitingjlService shebeiQitingjlService;
	@Autowired
	private HaocaiLxService haocaiLxService;
	@Autowired
	private ShebeiLxService shebeiLxService;
	@Resource
	private IDependReportService dependReportService;
	@Resource
	private EntrustTestItemCoditionService entrustTestItemCoditionService;
	@Resource
	private EntrustTestGroupAbilityService entrustTestGroupAbilityService;
	@Resource
	private LabInfoService labInfoService;
	@Resource
	private LabRoleService labRoleService;
	@Autowired
	private ExperimentAbilityDao experimentAbilityDao;

	@Autowired
	private UserDao userDao;

	/**
	 * 试验员工作量统计
	 * @param labId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> workload(String labId,List<String> userIds, Date start, Date end) {
		//获取时间范围内有工作的用户
		List<Map<String, Object>> userHasWork = reportstatementDao.getHasWorkUser(start, end);
		Set<String> users = new HashSet<>();
		for (Map<String, Object> map : userHasWork) {
			if (map != null) {
				String userId = (String) map.get("OWNER");
				users.addAll(Arrays.asList(userId.split(",")));
			}
		}
		//过滤用户
		if (userIds != null) {
			Set<String> users1 = new HashSet<>();
			for (String user : users) {
				if (userIds.contains(user)) {
					users1.add(user);
				}
			}
			users = users1;
		}else if((userIds==null || userIds.isEmpty()) && StringUtils.isEmpty(labId)){
			if(!UserUtils.getUser().isAdmin()) {
				LabRole filter = new LabRole();
				filter.setUser(UserUtils.getUser());
				List<LabRole> labRoles = labRoleService.findList(filter);
				users = new HashSet<>();
				for (LabRole labRole : labRoles) {
					List<User> testingEngineers = systemService.getUserByLabRoleExt(labRole.getLab().getId(), "TestingEngineer");
					for (User testingEngineer : testingEngineers) {
						if (!users.contains(testingEngineer.getId())) {
							users.add(testingEngineer.getId());
						}
					}
				}
			}
		}
		//过滤试验室
		if (StringUtils.isNotEmpty(labId)) {
			List<User> testingEngineers = systemService.getUserByLabRoleExt(labId, "TestingEngineer");
			Set<String> labUsers = new HashSet<>();
			for (User testingEngineer : testingEngineers) {
				if (users.contains(testingEngineer.getId())) {
					labUsers.add(testingEngineer.getId());
				}
			}
			users = labUsers;
		}
		List<Map<String, Object>> result = new ArrayList<>();
		for (String user : users) {
			if (StringUtils.isEmpty(user)) {
				continue;
			}
			//获取当前用户所属的中心----2021-05-21
			User u= UserUtils.getUser();
			String subCenter=systemService.getUserBelongTestCenter(u.getLoginName());
            User user1=userDao.get(user);
			String subCenterNew=systemService.getUserBelongTestCenter(user1.getLoginName());
			//判断当前用户所属的中心是否和数据的一致
			if(StringUtils.isNotEmpty(subCenter)){
				if(!subCenter.equals(subCenterNew)){
					continue;
				}
			}
			List<Map<String, Object>> userWork = reportstatementDao.getUserWork(labId,user, start, end,"");
			Map<String, Map<String, Object>> works = new HashMap<>();
			for (Map<String, Object> work : userWork) {
				Map<String, Object> testitemMap = workloadGroupByTestItem(work, works);
				String userId = (String) work.get("OWNER");
				double weight = 1.0 / (userId.split(",").length-1);//权重：1 / 任务试验员数量；权重*样品数量  =  工作量
				String sampleId = (String) work.get("SAMPLE_ID");
				int sampleCount = sampleId.split(",").length;
				testitemMap.put("workload", ((double) testitemMap.get("workload")) + weight * (double)testitemMap.get("weight") * sampleCount);//工作量
			}
			Map<String, Object> userData = new HashMap<>();
			List<Map<String, Object>> testitems = new ArrayList<>();//分组下试验项目数据
			double totalWorkload = 0;//分组下工作量
			for (String key : works.keySet()) {
				Map<String, Object> map = works.get(key);
				testitems.add(map);
				totalWorkload += (double) map.get("workload");
			}
			userData.put("id", user);
			userData.put("name", UserUtils.get(user).getName());
			userData.put("testItems", testitems);
			userData.put("total", totalWorkload);
			result.add(userData);
		}
		Collections.sort(result, Comparator.comparingInt(o -> ((String) o.get("name")).charAt(0)));
		return result;
	}

	/**
	 * 试验室工作量统计
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> labWorkload(String labIdp,Date start, Date end) {
		List<Map<String, Object>> result = new ArrayList<>();
		String user = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getId();
		List<Map<String, Object>> allwork = reportstatementDao.getUserWork(labIdp,"", start, end,user);
		Map<String, Map<String, Map<String, Object>>> labWorks = new HashMap<>();//group by lab
		Map<String, String> labs = new HashMap<>();
		for (Map<String, Object> work : allwork) {
			String labId = (String) work.get("LAB_ID");
			String labName = (String) work.get("LAB_NAME");
			labs.put(labId, labName);
			Map<String, Map<String, Object>> works = labWorks.get(labId);//试验室数据。group by testItem
			if (works == null) {
				works = new HashMap<>();
				labWorks.put(labId, works);
			}
			Map<String, Object> testitemMap = workloadGroupByTestItem(work, works);
			testitemMap.put("workload", (double)testitemMap.get("weight") * (int)testitemMap.get("count"));//工作量
		}
		for (String key : labWorks.keySet()) {
			Map<String, Map<String, Object>> labMap = labWorks.get(key);
			Map<String, Object> userData = new HashMap<>();
			List<Map<String, Object>> testitems = new ArrayList<>();//分组所有试验项目数据
			double totalWorkload = 0;//分组累计工作量
			for (String abilityId : labMap.keySet()) {
				Map<String, Object> map = labMap.get(abilityId);
				testitems.add(map);
				totalWorkload += (double) map.get("workload");
			}
			userData.put("id", key);
			userData.put("name", labs.get(key));
			userData.put("testItems", testitems);
			userData.put("total", totalWorkload);
			result.add(userData);
		}
		return result;
	}

	/**
	 * 按试验项目分组统计 试验项目数量、样品数量
	 * @param work 当前任务
	 * @param works 所有试验项目任务
	 * @return work 对应试验项任务
	 */
	private Map<String, Object> workloadGroupByTestItem(Map<String, Object> work,  Map<String, Map<String, Object>> works) {
		String abilityId = (String) work.get("ABILITY_ID");
		String abilityName = (String) work.get("ABILITY_NAME");
		Map<String, Object> testitemMap = works.get(abilityId);
		if (testitemMap == null) {
			testitemMap = new HashMap<>();
			Feiyong conf = feiyongService.findByItemId(abilityId,(String) work.get("LAB_ID"));
			testitemMap.put("id", abilityId);
			testitemMap.put("name", abilityName);
			testitemMap.put("weight", conf == null || StringUtils.isEmpty(conf.getWeights()) ? 1d : Double.parseDouble(conf.getWeights()));
			testitemMap.put("count", 0);
			testitemMap.put("sampleCount", 0);
			testitemMap.put("workload", 0d);
			works.put(abilityId, testitemMap);
		}
		testitemMap.put("count", ((Integer) testitemMap.get("count")) + 1);//试验项目数量
		String sampleId = (String) work.get("SAMPLE_ID");
		int sampleCount = sampleId.split(",").length;
		testitemMap.put("sampleCount", ((Integer) testitemMap.get("sampleCount")) + sampleCount);//样品数量
		return testitemMap;

	}

	/**
	 * 试验负责人工时
	 * @param labId
	 * @param start
	 * @param end
	 * @return
	 */
	public List<CheckUserTimeRate> getCheckUserTimeRate(String labId,Date start,Date end){

		String checkUsers = "";
		//获取试验室下面的试验负责人
		if(StringUtils.isNotBlank(labId)){//所有的试验负责人

		}else{
			List<User> users = systemService.getUserByLabRole(labId,"DetectingThePersonInCharge");
			if(CollectionUtils.isNotEmpty(users)){
				for(User u:users){
					checkUsers+=u.getId()+",";
				}
			}
		}

		List<CheckUserTimeRate> checkUserTimeRateList = reportstatementDao.getCheckUserTimeRate(checkUsers,start,end);
		return checkUserTimeRateList;

	}


	/**
	 * 试验负责人试验室统计
	 * @param labIds
	 * @param start
	 * @param end
	 * @return
	 */
	public List<CheckUserTimeRate> getLabTimeRate(String labIds,Date start,Date end){
		String loginName = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getLoginName();
		String user="";
		if(StringUtils.isNotEmpty(loginName)){
			user=systemService.getUserBelongTestCenter(loginName);
		}
		return reportstatementDao.getLabTimeRate(labIds,start,end,user);

	}


	/**
	 * 设备使用率
	 * @param start
	 * @param end
	 * @param labId
	 * @param compensate 补偿
	 * @param page
	 * @return
	 */
	public Page<List<Map>> deviceUseRatio(@NotNull Date start, @NotNull Date end, String labId, int compensate, Page page){
		Shebei filter = new Shebei();
		LabInfo labInfo = new LabInfo();
		labInfo.setId(labId);
		filter.setLabInfo(labInfo);
		filter.setShebeizt("0");
		String user=UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getLoginName();
		if(StringUtils.isNotEmpty(user)){
			String subCenter=systemService.getUserBelongCenter(user);
			filter.setSubCenter(subCenter);
		}
		Page<Shebei> list = shebeiService.findPage(page,filter);
		List<Map> result = new ArrayList<>();
		for (Shebei shebei : list.getList()) {
			if (null == shebei.getFirstStartDate() || shebei.getFirstStartDate().getTime() >= end.getTime()) {//时间范围内未投入使用的设备
				continue;
			}
			Integer stationCount = StringUtils.isEmpty(shebei.getKeshiyanypsl()) ? 0 : Integer.parseInt(shebei.getKeshiyanypsl());
			//按工位分组设备计划
			List<TestPlanExecuteDetail> executeDetails = testPlanExecuteDetailService.findByDeviceBetweenTime(start, end, shebei.getId(), null);
			List<List<TestPlanExecuteDetail>> stationList = DevicePlanCaculator.groupByStation(executeDetails, stationCount);
			double realCapacity = 0;
			for (List<TestPlanExecuteDetail> details : stationList) {
				double realStationCapacity = 0;
				for (int i = 0; i < details.size(); ) {//顺序为按开始时间从小到大
					TestPlanExecuteDetail task = details.get(i);
					int j = i+1;
					//寻找交叉（连续）的计划，避免重复计算时间
					Date maxEndDate = task.getPlanEndDate();
					if(j < details.size()){
						TestPlanExecuteDetail next = details.get(j);
						TestPlanExecuteDetail current = task;
						while(current.getPlanStartDate().getTime() <= next.getPlanStartDate().getTime() && next.getPlanStartDate().getTime() <= current.getPlanEndDate().getTime()){
							if (next.getPlanEndDate().getTime() > maxEndDate.getTime()) {
								maxEndDate = next.getPlanEndDate();
							}
							j++;
							if(j == details.size()){
								break;
							}
							if(next.getPlanEndDate().getTime() > current.getPlanEndDate().getTime()){
								current = next;
							}
							next = details.get(j);
						}
					}
					realStationCapacity += (Math.min(maxEndDate.getTime(),end.getTime()) - Math.max(task.getPlanStartDate().getTime(),start.getTime())) / 3600000.0;
					i = j;
				}
				realCapacity += realStationCapacity;
			}
			realCapacity = Math.round(realCapacity);
			CaculateDeviceStop caculateDeviceStop = new CaculateDeviceStop(start, end, shebei, compensate).invoke();
			long maxCapacity = caculateDeviceStop.getMaxCapacity() * stationCount;
			long stopTime = Math.round(caculateDeviceStop.getStopTime() * stationCount);
			Map<String, Object> data = new HashMap<>();
			data.put("name", shebei.getShebeimc());
			data.put("code", shebei.getNewCode());
			data.put("maxCapacity", maxCapacity);
			data.put("stopTime", stopTime);
			data.put("realCapacity", realCapacity);
			data.put("ratio", realCapacity/maxCapacity);
			result.add(data);
		}
		//Collections.sort(result,Comparator.comparingInt(value -> ((String)value.get("code")).charAt(0)));//按设备编号排序
		page.setList(result);
		page.setCount(list.getCount());
		return page;
	}


	/**
	 * 耗材统计
	 * @return
	 */
	public List<Map<String,Object>> consumablesReport(String labId,Date start,Date end){

		List<Map<String,Object>> result = new ArrayList<>();

		//获取所有的耗材类型
		List<HaocaiLx> haocaiLxes = haocaiLxService.findList(new HaocaiLx());
		if(CollectionUtils.isNotEmpty(haocaiLxes)){
			for(HaocaiLx lx:haocaiLxes){
				Map<String,Object> lxMap= new HashMap<>();
				String loginName = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getLoginName();
				String user="";
				if(StringUtils.isNotEmpty(loginName)){
					user=systemService.getUserBelongTestCenter(loginName);
				}
				List<Map<String,Object>> itemList = reportstatementDao.consumablesReport(lx.getId(),labId,start,end,user);
				if(CollectionUtils.isNotEmpty(itemList)){
					lxMap.put("id",lx.getId());
					lxMap.put("name",lx.getLeixingmc());
					lxMap.put("itemList",itemList);
					result.add(lxMap);
				}
			}
		}
		//获取每个耗材类型下面的耗材记录
		return result;

	}


	/***
	 * 设备定检统计
	 * @param labId
	 * @param shebeiLx
	 * @param code
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> shebeiDingjianReport(String labId,String shebeiLx,String code,Date start,Date end){

		List<Map<String,Object>> result = new ArrayList<>();

		ShebeiLx lx = new ShebeiLx();
		lx.setId(shebeiLx);
		List<ShebeiLx> lxList = shebeiLxService.findList(lx);
		if(CollectionUtils.isNotEmpty(lxList)){
			for(ShebeiLx s:lxList){
				Map<String,Object> oneShebei = new HashMap<>();

				//获取该类型的设备
				Shebei sh = new Shebei();
				sh.setShebeilx(s);
				sh.setLabInfo(new LabInfo(labId));
				sh.setShebeibh(code);
				List<Shebei> shebeiList = shebeiService.findList(sh);
				List<Map<String,Object>> sbList = new ArrayList<>();
				Integer allTotal = 0;
				if(CollectionUtils.isNotEmpty(shebeiList)){


					for(Shebei sb:shebeiList){
						//获取每个设备下面的定检记录
						List<Map<String,Object>> djList = reportstatementDao.shebeiDJ(sb.getId(),start,end);
						if(CollectionUtils.isNotEmpty(djList)){
							Map<String,Object> sMap = new HashMap<>();
							allTotal+=djList.size();

							sb.setDjList(djList);
//							sMap.put("shebeiInfo",sb);
							sMap.put("shebeimc",sb.getShebeimc());
							sMap.put("shebeibh",sb.getShebeibh());
							sMap.put("shebeiStatus",sb.getShebeizt());
							sMap.put("shebeixh",sb.getShebeixh());
							if(null != sb.getShiyongdw()){
                                sMap.put("shebeidw",sb.getShiyongdw().getName());
                            }
							sMap.put("shebeipq",sb.getShifouxypq());
							sMap.put("shebeiyt",sb.getShebeiyt());
							sMap.put("shebeirl",sb.getKeshiyanypsl());
							sMap.put("shebeifzr",sb.getShebeigly()==null?"":sb.getShebeigly().getName());
							sMap.put("djList",djList);
							sbList.add(sMap);
						}


					}
					if(CollectionUtils.isNotEmpty(sbList)){
						oneShebei.put("lxName",s.getLeixingmc());
						oneShebei.put("shebeis",sbList);
						oneShebei.put("total",allTotal);
						result.add(oneShebei);
					}
				}



			}
		}
		return result;
	}


	public List<ShebeiGzReport> shebeiGuzhangReport(String labId,String shebeiids,Date start,Date end){
		String user = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getId();
		List<ShebeiGzReport> gzReports =  reportstatementDao.shebeiGuzhang(labId, shebeiids, start, end,user);
		if(CollectionUtils.isNotEmpty(gzReports)){
			for(ShebeiGzReport gz:gzReports){
				Shebei shebei = shebeiService.getByCode(gz.getCode());
				gz.setName(shebei.getShebeimc());
				/*//获取设备的启停记录
				Shebei shebei = shebeiService.getByCode(gz.getCode());
				if(DateUtils.compare(shebei.getFirstStartDate(),start)<=0){
					gz.setAllTotal(String.valueOf(Math.round(DateUtils.getDistanceHoursOfTwoDate(start,end))));
				}else if(DateUtils.compare(shebei.getFirstStartDate(),end)<=0){
					gz.setAllTotal(String.valueOf(Math.round(DateUtils.getDistanceHoursOfTwoDate(shebei.getFirstStartDate(),end))));
				}else{
					gz.setAllTotal("0");
				}*/
				CaculateDeviceStop caculateDeviceStop = new CaculateDeviceStop(start, end, shebei).invoke();
				long maxCapacity = caculateDeviceStop.getMaxCapacity();
				gz.setAllTotal(String.valueOf(maxCapacity));

			}
		}
		return gzReports;

	}

	/**
	 * 试验费用
	 * @param type
	 * @param labId
	 * @param userIds
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String, Object>> testMoney(int type,String labId, String userIds, Date start, Date end){
		List<Map<String, Object>> result = new ArrayList<>();
		String user = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getId();
		List<Map<String, Object>> list = reportstatementDao.testMoneyStatistic(type, labId, userIds, start, end,user);
		if (type == 1) {//group by office
			result.addAll(testMoneyListGroupBy(list, "OFFICEID", "OFFICENAME"));
		}else if(type == 2){//group by project
			result.addAll(testMoneyListGroupBy(list, "PROJECTID", "PROJECTNAME"));
		}
		return result;
	}

	/**
	 * 试验费用分组统计，生成嵌套列表
	 * @param list  平铺数据
	 * @param keyProp  分组key
	 * @param nameProp  分组名称key
	 * @return
	 */
	private List<Map<String, Object>> testMoneyListGroupBy(List<Map<String, Object>> list, String keyProp, String nameProp) {
		List<Map<String, Object>> result = new ArrayList<>();
		Map<String, List<Map>> groupData = new HashMap<>();
		for (Map<String, Object> row : list) {
			String key = (String) row.get(keyProp);
			List<Map> item = groupData.get(key);
			if (item == null) {
				item = new ArrayList();
				groupData.put(key, item);
			}
			item.add(row);
		}
		for (String key : groupData.keySet()) {
			Map<String, Object> projectItem = new HashMap<>();
			long totalMoney = 0;
			String name = "";
			List<Map> detail = groupData.get(key);
			for (Map map : detail) {
				name = (String) map.get(nameProp);
				long total = Math.round(Double.parseDouble((String)map.get("MONEY"))) * ((BigDecimal)map.get("SAMPLECOUNT")).intValue();
				map.put("TOTAL", total);
				totalMoney += total;
			}
			projectItem.put("ID", key);
			projectItem.put("NAME", name);
			projectItem.put("TOTAL", totalMoney);
			projectItem.put("DETAIL", detail);
			result.add(projectItem);
		}
		return result;
	}

	public List<Map<String,Object>> shebeiBaoyangReport(String labId,String shebeiLx,String code,Date start,Date end){

		List<Map<String,Object>> result = new ArrayList<>();
		Shebei shebei = new Shebei();
		shebei.setShebeilx(new ShebeiLx(shebeiLx));
		shebei.setShebeibh(code);
		shebei.setLabInfo(new LabInfo(labId));
		List<Shebei> shebeiList = shebeiService.findList(shebei);
		if(CollectionUtils.isNotEmpty(shebeiList)){
			Map<String,Object> map = null;
			for(Shebei s:shebeiList){
				//获取每个设备的保养计划
				List<Map<String,Object>> baoyangList = reportstatementDao.shebeiBaoyang(s.getId(),start,end);
				if(CollectionUtils.isNotEmpty(baoyangList)){
					map = new HashMap<>();
					map.put("shebeibh",s.getShebeibh());
					map.put("shebeimc",s.getShebeimc());
					map.put("bylist",baoyangList);
					result.add(map);
				}
			}
		}

		return result;
	}



	public List<TestEngineerTimeRate> testEngineerReport(String labId,Date start,Date end){
		//创建返回的list
		List<TestEngineerTimeRate> checkUserTimeRateList = new ArrayList<>();
		//用户list
		List<User> users = new ArrayList<>();
		
		if(StringUtils.isEmpty(labId)){
			if(!UserUtils.getUser().isAdmin()) {
				LabRole filter = new LabRole();
				filter.setUser(UserUtils.getUser());
				List<LabRole> labRoles = labRoleService.findList(filter);

				for (LabRole labRole : labRoles) {
					List<User> testingEngineers = systemService.getUserByLabRoleExt(labRole.getLab().getId(), "TestingEngineer");
					for (User testingEngineer : testingEngineers) {
						if (!users.contains(testingEngineer)) {
							users.add(testingEngineer);
						}
					}
				}
			}else {
				List<User> testingEngineers = systemService.getUserByLabRoleExt(null, "TestingEngineer");
				for (User testingEngineer : testingEngineers) {
					if (!users.contains(testingEngineer)) {
						users.add(testingEngineer);
					}
				}
			}
		}else {
			//获取试验工程师
			users = systemService.getUserByLabRoleExt(labId, "TestingEngineer");
		}
		//获取当前用户所属的中心----2021-05-21
		User u= UserUtils.getUser();
		String subCenter=systemService.getUserBelongTestCenter(u.getLoginName());
		for (User us : users) {

			User user1=userDao.get(us.getId());
			String subCenterNew=systemService.getUserBelongTestCenter(user1.getLoginName());
			//判断当前用户所属的中心是否和数据的一致
			if(StringUtils.isNotEmpty(subCenter)){
				if(!subCenter.equals(subCenterNew)){
					continue;
				}
			}
			TestEngineerTimeRate ck = new TestEngineerTimeRate();
			ck.setCheckUser(us.getId());
			ck.setCheckUserName(us.getName());
			ck.setComNum(0);
			ck.setNoComNum(0);
			ck.setTotalEntrust(0);
			checkUserTimeRateList.add(ck);
		}
		//获取这段时间内所有完成的申请单

		String user = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getId();
		List<TestEngineer> testEngineers = reportstatementDao.getTestEngineers(user,start,end);
		/*List<String> allUsers = new ArrayList<>();
		for(TestEngineer t:testEngineers){
			allUsers.addAll(this.getTestUsersByEntrustCode(t.getEntrustCode()));
		}
		System.out.println(allUsers);
		Map<String,Integer> uMap = new HashMap<>();
		for(String s:allUsers){
			if(uMap.containsKey(s)){
				uMap.put(s,uMap.get(s)+1);
			}else {
				uMap.put(s,1);
			}
		}
		System.out.println(uMap);*/

		List<TestEngineer> noCompletes = new ArrayList<>();
		List<TestEngineer> completes = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(testEngineers)){
			for(TestEngineer t:testEngineers){
				if(DateUtils.compare(t.getCompleteDate(),t.getPlanDate())<=0){
					completes.add(t);
				}else{
					noCompletes.add(t);
				}
			}
			tranferTestEngTimeRate(checkUserTimeRateList,testEngineers,1);
			tranferTestEngTimeRate(checkUserTimeRateList,noCompletes,2);
			tranferTestEngTimeRate(checkUserTimeRateList,completes,3);
		}

		return removeEmpety(checkUserTimeRateList);
	}

	private List<TestEngineerTimeRate> removeEmpety(List<TestEngineerTimeRate> testEngineerTimeRates){
		List<TestEngineerTimeRate> emptyTimeRates = new ArrayList<>();
		for(TestEngineerTimeRate t:testEngineerTimeRates){
			if(t.getTotalEntrust() == 0){
				emptyTimeRates.add(t);
			}
		}
		if(testEngineerTimeRates.removeAll(emptyTimeRates)){
			return testEngineerTimeRates;
		}
		return testEngineerTimeRates;

	}

	private List<String> getTestUsersByEntrustCode(String entrustCode){
		List<String> result = new ArrayList<>();
		String allOwners = "";
		List<Map<String,Object>> owners = reportstatementDao.getItemOwners(entrustCode);
		if(CollectionUtils.isNotEmpty(owners)){
			for(Map m:owners){
				allOwners+=m.get("owners");
			}
		}
		String[] userArr = allOwners.split(",");
		for(String u:userArr){

			if(StringUtils.isNotBlank(u)){
				if(!result.contains(u)){
					result.add(u);
				}
			}
		}
		return result;

	}

	private void tranferTestEngTimeRate(List<TestEngineerTimeRate> checkUserTimeRateList,List<TestEngineer> tes,Integer type){

		//遍历未完成的申请单
		for(TestEngineer nt:tes){
			//这个申请单关联的用户
			List<String> noComUsers = this.getTestUsersByEntrustCode(nt.getEntrustCode());
			//给这些用户对应的加上一
			for(TestEngineerTimeRate te:checkUserTimeRateList){
				for(String nc:noComUsers){
					if(te.getCheckUser().equals(nc)){
						if(type.equals(1)){//总数
							te.setTotalEntrust(te.getTotalEntrust()+1);
						}else if(type.equals(2)){//未完成的
							te.setNoComNum(te.getNoComNum()+1);
						}else if(type.equals(3)){//完成的
							te.setComNum(te.getComNum()+1);
						}
					}
				}
			}
		}
	}

	/**
	 * 加急申请单
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Map<String,Object>> emergentEntrustStatistic(String labId,Date start, Date end){
		String user = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getId();
		return reportstatementDao.emergentEntrustStatistic(labId,start, end,user);
	}


	private  Boolean checkCode(String code,String lastCode){

		if(code.equals(lastCode)){
			return true;
		}else{
			if((code+"-A").equals(lastCode)){
				return  true;
			}else if((code+"-B").equals(lastCode)){
				return  true;
			}else if((code+"-a").equals(lastCode)){
				return  true;
			}else if((code+"-b").equals(lastCode)){
				return  true;
			}else if((code+"-1").equals(lastCode)){
				return  true;
			}else if((code+"-2").equals(lastCode)){
				return  true;
			}
		}
		return false;
	}

	private  String getBothOrFront(String code){

		if(code.contains("-a")||code.contains("-A")||code.contains("-1")){
			return "正";
		}
		if(code.contains("-b")||code.contains("-B")||code.contains("-2")){
			return "反";
		}
		return "";
	}

	private  List<String> getUnionList(String code,List<String> sampleList){
		List<String> result = new ArrayList<>();
		for(String s:sampleList){
			if(checkCode(code,s)){
				result.add(s);
			}
		}
		return result;
	}

	private List<Map<String,Object>> getResult(List<Map<String,Object>> list){
		List<Map<String,Object>> result = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(list)){

			for(Map map:list){
				String taskId = (String) map.get("taskId");
				String entrustId = (String) map.get("entrustId");
				String testItemId = (String) map.get("testItemId");
				String sn = (String) map.get("sampleCode");
				//获取所有的样品
				List<String> allSamples = dependReportService.getAllSampleCodes(taskId,entrustId,testItemId);
				List<String> endCodes = getUnionList(sn, allSamples);

				for(String endCode:endCodes){
					Map map1 =new HashMap();
					map1.putAll(map);
					map1.put("sampleCode",endCode);
					result.add(map1);
				}
			}
		}

		return result;
	}


	/**
	 * 可靠性测试
	 * @param entrustCode
	 * @param testType
	 * @param sampleType
	 * @param projectCode
	 * @param sampleCode
	 * @param testItems
	 * @param start
	 * @param end
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<Map<String,Object>> reliabilityTest(String labIds,String entrustCode,String testType, String sampleType,String projectCode,String
			sampleCode, String testItems, Date start, Date end){
		String user = UserUtils.getUser().isAdmin()?"":UserUtils.getUser().getId();
		List<Map<String, Object>> data = reportstatementDao.reliabilityTest(labIds,entrustCode,testType,sampleType,projectCode,sampleCode,testItems,
				start, end,user);
		List<Map<String, Object>> list = getResult(data);

		String currentSample = null,
				prevWaiguan = null,
				prevJueyuan = null,
				prevSld = null;//记录当前样品，当前试验前数据

		Map<String,Object> prevGonglv = null;//试验前功率
		String initPmax = "";//预处理Pmax
		for (Map<String, Object> map : list) {
			map.put("bothFront",getBothOrFront((String)map.get("sampleCode")));
			String testItemId = (String) map.get("testItemId");
			String labId = (String) map.get("labId");
			LabInfo labInfo = labInfoService.get(labId);
			TestItem preHandleItem = labInfo.getPreHandleItem();//预处理
			TestItem preItem = labInfo.getPreItem();//前置试验
			String taskId = (String) map.get("taskId");
			String sn = (String) map.get("sampleCode");
			String entrustId = (String) map.get("entrustId");
			Map<String, Map<String, Object>> dataMap = dependReportService.getDependDate(taskId, sn, entrustId, testItemId);
			Map<String,Object> initGonglv = null;//初始功率

			if(dataMap != null){
				if (currentSample == null || !currentSample.equals(map.get("sampleCode"))) {//new group by sample，按样品排序，遇到不同样品编码，进入下一组数据
					currentSample = sn;
					prevWaiguan = dataMap.get("waiguan")!=null ? (String) dataMap.get("waiguan").get("waiguan") : null;
					prevJueyuan = dataMap.get("jueyuan")!=null ? (String) dataMap.get("jueyuan").get("JIEGUO") : null;
					prevSld = dataMap.get("sld")!=null ? (String) dataMap.get("sld").get("JIEGUO") : null;
				//	initPmax = "";
					prevGonglv = dataMap.get("gonglv");
					if (preHandleItem.getId().equals(testItemId)) {//预处理若有，则一定是第一个试验项目，获取初始功率
						initPmax = dependReportService.getChushigonglv(taskId, sn, entrustId,testItemId);
					}else{
						initPmax = "";
					}
				} else {
					if (dataMap.get("waiguan") != null) {
						if (StringUtils.isNotEmpty(prevWaiguan)) {
							dataMap.get("waiguan").put("prev",prevWaiguan);
						}
						if (dataMap.get("waiguan").get("waiguan") != null) {//设置当前为下一次的试验前，下同
							prevWaiguan = dataMap.get("waiguan").get("waiguan").toString();
						}
					}
					if (dataMap.get("jueyuan") != null) {
						if (StringUtils.isNotEmpty(prevJueyuan)) {
							dataMap.get("jueyuan").put("prev",prevJueyuan);
						}
						if (dataMap.get("jueyuan").get("JIEGUO") != null) {
							prevJueyuan = dataMap.get("jueyuan").get("JIEGUO").toString();
						}
					}
					if (dataMap.get("sld") != null) {
						if (StringUtils.isNotEmpty(prevSld)) {
							dataMap.get("sld").put("prev",prevSld);
						}
						if (dataMap.get("sld").get("JIEGUO") != null) {
							prevSld = dataMap.get("sld").get("JIEGUO").toString();
						}
					}
					if (dataMap.get("gonglv") != null) {
						if (prevGonglv != null) {
							dataMap.put("prevGonglv",prevGonglv);
						}
						prevGonglv = dataMap.get("gonglv");
					}

				}

				if (preItem != null && preItem.getId().equals(testItemId)) {//前测，初始功率设置 Pmax
					//	initGonglv = dataMap.get("gonglv");

					initGonglv = dataMap.get("gonglv");

				}

				if (StringUtils.isNotEmpty(initPmax)) {//预处理，设置初始功率 Pmax
					//if (preHandleItem.getId().equals(testItemId)) {
					initGonglv = new HashMap<>();
					initGonglv.put("PMAX", initPmax);
					//	}
					dataMap.put("initGonglv", initGonglv);
				}else{

				}
				map.putAll(dataMap);
				//设置试验条件，试验序列内容
				String tGroupId = (String) map.get("tGroupId");
				List<EntrustTestGroupAbility> abilities = entrustTestGroupAbilityService.listByTestGroupId(tGroupId);
				boolean isSeq = abilities.size() == 1 && StringUtils.isNotBlank(abilities.get(0).getSeqId());
				if (isSeq) {
					List<EntrustTestItemCodition> seqConditions = entrustTestItemCoditionService.listByEntrustAbilityId(abilities.get(0).getId());
					if (seqConditions.size() == 1) {
						map.put("condition",seqConditions.get(0).getParameter());
					}
					map.put("seq",abilities.get(0).getSeqName());
				}else{
					List<EntrustTestItemCodition> coditions = entrustTestItemCoditionService.listByTestGroupIdAndItemId(tGroupId, testItemId);
					if(coditions.size() > 0){
						map.put("condition",coditions.get(0).getParameter());
					}
					List<String> abilityNames = new ArrayList<>();
					for (EntrustTestGroupAbility ability : abilities) {
						abilityNames.add(ability.getItemName());
					}
					map.put("seq",StringUtils.join(abilityNames,"+"));//
				}
			}
		}
		return list;
	}


	/**
	 * 计算设备停机时间
	 */
	private class CaculateDeviceStop {
		private Date start;
		private Date end;
		private Shebei shebei;
		private Integer stationCount;
		private double maxCapacity;
		private double stopTime;

		public CaculateDeviceStop(Date start, Date end, Shebei shebei, double compensate) {
			this.start = start;
			this.end = end;
			this.shebei = shebei;
			this.stationCount = StringUtils.isEmpty(shebei.getKeshiyanypsl()) ? 0 : Integer.parseInt(shebei.getKeshiyanypsl());
			this.maxCapacity = (end.getTime() - Math.max(shebei.getFirstStartDate().getTime(),start.getTime())) / 3600000.0 - compensate * 24;//最大产能
		}
		public CaculateDeviceStop(Date start, Date end, Shebei shebei) {
			this.start = start;
			this.end = end;
			this.shebei = shebei;
			this.stationCount = StringUtils.isEmpty(shebei.getKeshiyanypsl()) ? 0 : Integer.parseInt(shebei.getKeshiyanypsl());
			this.maxCapacity = (end.getTime() - Math.max(shebei.getFirstStartDate().getTime(),start.getTime())) / 3600000.0;//最大产能
		}

		/**
		 * 应工作时间，已四舍五入
		 * @return
		 */
		public long getMaxCapacity() {
			return Math.round(maxCapacity);
		}

		public double getStopTime() {
			return stopTime;
		}

		public CaculateDeviceStop invoke() {
			stopTime = 0;
			if (shebei.getFirstStartDate().getTime() < end.getTime()) {
				ShebeiQitingjl sq = new ShebeiQitingjl();
				sq.setShebeiId(shebei.getId());
				List<ShebeiQitingjl> shebeiQitingjls = shebeiQitingjlService.findList(sq);
				Collections.reverse(shebeiQitingjls);
				Date startTime = null;//停机开始时间
				for (int i = 0; i < shebeiQitingjls.size(); i++) {
					ShebeiQitingjl shebeiQitingjl = shebeiQitingjls.get(i);
					if (shebeiQitingjl.getQtTime() == null) {//保证 qttime 不为空
						continue;
					}
					if (ShebeiConstans.ShebeiQTZT.STOP.equals(shebeiQitingjl.getStatus())) {
						if (startTime != null) {//跳过 连续地启动或停机
							startTime = shebeiQitingjl.getQtTime();
							continue;
						} else {
							startTime = shebeiQitingjl.getQtTime();
						}
					} else {
						if (startTime == null) {//没有对应停机节点
							if (i == 0 && shebeiQitingjl.getQtTime().getTime() > start.getTime() && maxCapacity>0) {//第一条记录是启动，则启动以前不计入最大产能
								maxCapacity -= shebeiQitingjl.getQtTime().getTime() - start.getTime() / 3600000.0;
							}
							continue;
						}
						if (shebeiQitingjl.getQtTime().getTime() > start.getTime() && shebeiQitingjl.getQtTime().getTime() < end.getTime()) {//时间范围内停机时间
							stopTime += ((Math.min(end.getTime(),shebeiQitingjl.getQtTime().getTime()) - Math.max(start.getTime(),startTime.getTime()))) / 3600000.0;
						} else if (shebeiQitingjl.getQtTime().getTime() >= end.getTime()) {//超出结束时间, 结束
							break;
						}
						startTime = null;//配对完成，进行下一轮配对
					}
				}
				if (startTime != null) {//end 的时候仍处于停机状态，加入最后一段的停机时间
					stopTime += ((end.getTime() - Math.max(start.getTime(),startTime.getTime()))) / 3600000.0;
				}
			}
			maxCapacity -= stopTime;
			return this;
		}
	}


	/**
	 * 试验能力统计	39
	 * @return
	 */
	public List<LabAbilityCount> labAbility(String status){
		List<LabAbilityCount> labAbilityCounts = experimentAbilityDao.labAbilityCount( status);
		return labAbilityCounts;
	}
	/**
	 *试验验证能力	40
	 */
	public List<LabAbilityCount> verificationCapability(String status){
		List<LabAbilityCount> verificationCapabilityCount = experimentAbilityDao.verificationCapabilityCount(status);
		return verificationCapabilityCount;
	}

	/**
	 *试验室能力建设状态	41
	 */
	public List<LabAbilityCount> abilityBuild(String status){
		return experimentAbilityDao.abilityBUildCount(status,"");
	}


}
