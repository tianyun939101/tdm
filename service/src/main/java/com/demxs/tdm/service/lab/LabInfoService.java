package com.demxs.tdm.service.lab;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.service.ILabInfoService;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.lab.LabUserDao;
import com.demxs.tdm.domain.ability.TestItem;
import com.demxs.tdm.domain.ability.TestItemCodition;
import com.demxs.tdm.domain.ability.TestItemUnit;
import com.demxs.tdm.domain.ability.TestSequenceItem;
import com.demxs.tdm.domain.ability.constant.AbilityConstants;
import com.demxs.tdm.domain.lab.*;
import com.demxs.tdm.domain.resource.feiyong.Feiyong;
import com.demxs.tdm.domain.sys.LabRole;
import com.demxs.tdm.service.ability.TestItemService;
import com.demxs.tdm.service.ability.TestSequenceService;
import com.demxs.tdm.service.resource.feiyong.FeiyongService;
import com.demxs.tdm.service.sys.LabRoleService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.github.ltsopensource.core.commons.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * 试验室信息Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabInfoService extends CrudService<LabInfoDao, LabInfo> implements ILabInfoService{

	@Resource
	private LabTestItemService labTestItemService;
	@Resource
	private LabTestSequenceService labTestSequenceService;
	@Resource
	private TestSequenceService testSequenceService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Resource
	private TestItemService testItemService;
	@Resource
	private FeiyongService feiyongService;
	@Autowired
	private LabRoleService labRoleService;
	@Autowired
	private LabUserDao labUserDao;

	@Override
	public LabInfo get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<LabInfo> findList(LabInfo labInfo) {
		return super.findList(labInfo);
	}



	@Override
	public Page<LabInfo> findPage(Page<LabInfo> page, LabInfo labInfo) {
		return super.findPage(page, labInfo);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(LabInfo labInfo) {
		super.save(labInfo);
		labTestItemService.deleteByLabId(labInfo.getId());
		labTestSequenceService.deleteByLabId(labInfo.getId());
		List<LabTestItem> testItemList = labInfo.getTestItemList();
		if (testItemList == null) {
			testItemList = new ArrayList<>();
		} else {
			for (LabTestItem lti : testItemList) {
				lti.setLabId(labInfo.getId());
			}
		}
		if(labInfo.getTestSequenceList() != null){
			for (LabTestSequence lts : labInfo.getTestSequenceList()) {
				lts.setId("");
				lts.setLabId(labInfo.getId());
				labTestSequenceService.save(lts);
				List<TestSequenceItem> sequenceItems = testSequenceService.listItem(lts.getSeqId());
				for (TestSequenceItem sequenceItem : sequenceItems) {//加入试验室试验项目中不包含的试验项目
					if (AbilityConstants.TestSequenceItemFlag.ITEM.equals(sequenceItem.getFlag())) {
						LabTestItem item = new LabTestItem();
						item.setItemId(sequenceItem.getItemId());
						item.setItem(sequenceItem.getItem());
						item.setLabId(labInfo.getId());
						if(!testItemList.contains(item)){
							testItemList.add(item);
						}
					}
				}
			}
		}
		if(testItemList != null){
			for (LabTestItem lti : testItemList) {
				lti.setId("");
				lti.setLabId(labInfo.getId());
				setLabTestItemJoins(lti);
				labTestItemService.save(lti);
				if(feiyongService.findByItemId(lti.getItem().getId(), lti.getLabId()) == null){//费用配置不存在则插入
					Feiyong feiyong = new Feiyong();
					feiyong.setItemId(lti.getItem().getId());
					feiyong.setItemName(lti.getItem().getName());
					feiyong.setLabInfoId(lti.getLabId());
					feiyong.setWeights("1");
					feiyongService.save(feiyong);
				}
			}
		}
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public LabInfo assignLabToRole(Role role, LabInfo labInfo) {
		if (labInfo == null){
			return null;
		}
		LabRole labRole = new LabRole(labInfo.getId(),role.getId());
		labRoleService.save(labRole);
		return labInfo;
	}

	private void setLabTestItemJoins(LabTestItem labTestItem){
		TestItem testItem = labTestItem.getItem();
		if (testItem.getTestUnitsList() == null || testItem.getTestUnitsList().size() == 0) {
			testItem = testItemService.get(testItem.getId());
			labTestItem.setItem(testItem);
		}
		setLabTestItemTestUnits(labTestItem);
		setLabTestItemConditions(labTestItem);
	}
	/**
	 * 试验项目没有设置条件、试验项时，从能力库获取
	 * @param labTestItem
	 */
	private void setLabTestItemTestUnits(LabTestItem labTestItem){
		if (labTestItem.getLabTestUnitsList() != null && labTestItem.getLabTestUnitsList().size() > 0) {
			return;
		}
		TestItem testItem = labTestItem.getItem();
		List<TestItemUnit> testUnitsList = testItem.getTestUnitsList();
		List<LabTestItemUnit> labTestItemUnits = new ArrayList<>();
		for (TestItemUnit testItemUnit : testUnitsList) {
			LabTestItemUnit labTestItemUnit = new LabTestItemUnit();
			try {
				BeanUtils.copyProperties(labTestItemUnit,testItemUnit);
			} catch (IllegalAccessException|InvocationTargetException e) {
				logger.info("TestItemUnit contruct LabTestItemUnit failed",e);
			}
			labTestItemUnits.add(labTestItemUnit);
		}
		labTestItem.setLabTestUnitsList(labTestItemUnits);
	}
	private void setLabTestItemConditions(LabTestItem labTestItem){
		if (labTestItem.getLabTestItemConditions() != null && labTestItem.getLabTestItemConditions().size() > 0) {
			return;
		}
		TestItem testItem = labTestItem.getItem();
		List<TestItemCodition> conditionsList = testItem.getConditionsList();
//						List<LabTestItemCondition> labConditionsList = BeanMapper.mapList(conditionsList, LabTestItemCondition.class);
//						item.setLabTestItemConditions(labConditionsList);
//						List<TestItemUnit> testUnitsList = sequenceItem.getItem().getTestUnitsList();
//						List<LabTestItemUnit> labTestItemUnits = BeanMapper.mapList(testUnitsList, LabTestItemUnit.class);
		List<LabTestItemCondition> labConditionsList = new ArrayList<>();
		for (TestItemCodition itemCodition : conditionsList) {
			LabTestItemCondition labTestItemCondition = new LabTestItemCondition();
			try {
				BeanUtils.copyProperties(labTestItemCondition,itemCodition);
			} catch (IllegalAccessException|InvocationTargetException e) {
				logger.info("TestItemCodition contruct LabTestItemCondition failed",e);
			}
			labConditionsList.add(labTestItemCondition);
		}
		labTestItem.setLabTestItemConditions(labConditionsList);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(LabInfo labInfo) {
		labTestItemService.deleteByLabId(labInfo.getId());
		labTestSequenceService.deleteByLabId(labInfo.getId());
		super.delete(labInfo);
	}


	/**
	 * 根据用户获取所在的试验室
	 * @param user
	 * @return
	 */
	@Override
	public Map<String,String> getLabByUserId(User user){
		if(user == null){
			return new HashMap<>();
		}else {
			Map<String,String> result = new HashMap<String, String>();
			//LabInfo labInfo = getLabByOfficeId(user.getOffice());
			LabInfo labInfo = this.dao.getLabInfoByUserId(user.getId());
			if(labInfo!=null && StringUtils.isNotEmpty(labInfo.getId())){
				result.put("id",labInfo.getId());
				result.put("name",labInfo.getName());
				//result.put("officeId",labInfo.getOffice().getId());
			}
			return result;
		}

	}

	/**
	 * 递归 从组织获取试验室
	 * @param office
	 * @return
	 */
	public LabInfo getLabByOfficeId(Office office){
		LabInfo labInfo = new LabInfo();
		if(office!=null && StringUtils.isNotEmpty(office.getId())){
			LabInfo l = new LabInfo();
			l.setOffice(office);
			List<LabInfo> labInfos = this.dao.findList(l);
			if(labInfos!=null && labInfos.size()>0){
				labInfo = labInfos.get(0);
			}else{
				labInfo =  getLabByOfficeId(office.getParent());
			}
		}

		return labInfo;
	}

	public LabInfo getBaseInfo(String id) {
		return dao.getBaseInfo(id);
	}

    public List<LabInfo> findAllList(LabInfo labInfo) {
        return super.dao.findAllList(labInfo);
    }

    public List<LabInfo> findAllListByCondition(String where){
		return this.dao.findAllListByCondition(where);
	}


	/**
	 * @Describe:查询所有正式试验室
	 * @Author:WuHui
	 * @Date:15:04 2020/11/11
	 * @param centerId
	 * @return:java.util.List<java.lang.String>
	*/
	public List<String> getEnalbeLabByCenter(String centerId){
		return dao.findAllEnalbeLab(centerId);
	}

	public List<String> getLabIdByCenter(String centerId){
		return dao.getLabIdByCenter(centerId);
	}

	public List<Map<String, Object>> getNew(LabInfo labInfo){
		List<Map<String, Object>> new1 = dao.getNew(labInfo);
		for(Map<String, Object> map : new1) {
			String id = map.get("id").toString();
			LabUser labUser = new LabUser();
			labUser.setUserId(UserUtils.getUser().getId());
			List<LabUser> byUserId1 = labUserDao.findByUserId(labUser);
			if (CollectionUtils.isNotEmpty(byUserId1)) {
				LabUser labUser1 = byUserId1.get(0);
				if (id.equals(labUser1.getNewLabId())) {

					map.put("userId", labUser1.getUserId());
				} else {

					map.put("userId", "没有");
				}
			} else {
				map.put("userId", "没有");
			}
		}
		return new1;
	}
	public List<Map<String, Object>> getOld(LabInfo labInfo){
		List<Map<String, Object>> getOld = dao.getOld(labInfo);
		for(Map<String, Object> map : getOld){
			String id = map.get("id").toString();
			LabUser labUser = new LabUser();
			labUser.setUserId(UserUtils.getUser().getId());
			List<LabUser> byUserId1 = labUserDao.findByUserId(labUser);
			if(CollectionUtils.isNotEmpty(byUserId1) ){
				LabUser labUser1 = byUserId1.get(0);
				if(id.equals(labUser1.getLabId())){

					map.put("userId",labUser1.getUserId());
				}else{

					map.put("userId","没有");
				}
			}else{
				map.put("userId","没有");
			}
			/*String isNewOrOld = map.get("isNewOrOld").toString();
			if("1".equals(isNewOrOld)){
				labUser.setFlag("1");
				labUser.setNewLabId(id);
			}else{
				labUser.setFlag("0");
				labUser.setLabId(id);
			}
			List<LabUser> byUserId = labUserDao.findByUserId(labUser);*/
		}
		return getOld;
	}

	@Transactional
	public void changeBelone(String oldId, String newId){
		String userId = UserUtils.getUser().getId();
		LabUser labUser = new LabUser();
		labUser.setUserId(userId);
		List<LabUser> byUserId = labUserDao.findByUserId(labUser);
		labUser.setLabId(oldId);
		labUser.setNewLabId(newId);
		labUser.setUpdateBy(UserUtils.getUser());
		labUser.setUpdateDate(new Date());
		if(CollectionUtils.isNotEmpty(byUserId)){
			labUserDao.changeBelone(labUser);
		}else{
			labUser.setId(UUID.randomUUID().toString());
			labUser.setCreateBy(UserUtils.getUser());
			labUser.setCreateDate(new Date());
			labUserDao.insert(labUser);
		}

	}

}