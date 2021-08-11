package com.demxs.tdm.service.sys;


import com.demxs.tdm.service.external.convertor.EhrDeptConvertor;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.OfficeTree;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.external.EhrDeptResult;
import com.demxs.tdm.job.JobType;
import com.demxs.tdm.job.JobUtil;
import com.github.ltsopensource.core.commons.utils.DateUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.exception.JobSubmitException;
import com.github.ltsopensource.jobclient.domain.Response;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 机构Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	OfficeDao officeDao;
	private static final Logger log = LoggerFactory.getLogger(OfficeService.class);

	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}

	@Transactional(readOnly = true,rollbackFor = ServiceException.class)
	public List<Office> findListByOffice(Office office){
		return  super.findList(office);
	}
	public Page<Office> findPageOffice(Page<Office> page, Office office) {
		office.setPage(page);
		// 执行分页查询
		page.setList(findListByOffice(office));
		return page;
	}
	/**
	 * 获取当前机构的 子级机构
	 * @param office
	 * @return
	 */
	@Transactional(readOnly = true,rollbackFor = ServiceException.class)
	public List<Office> findChildren(Office office){
		if(office != null){
			return dao.findChildrenByParentId(office);
		}
		return  new ArrayList<Office>();
	}

	@Override
	public Office get(Office office){
		return dao.get(office);
	}

	public String getId(String name){return officeDao.findID(name);}
	@Override
	@Transactional(readOnly = true,rollbackFor = ServiceException.class)
	public List<Office> findList(Office office){
		if(office != null){
//			office.setParentIds(office.getParentIds() == null ? "%" : "%"+office.getParentIds()+"%");
			office.setParentIds(office.getParentIds() == null ? "%" : "%"+office.getId()+"%");
            return dao.findByParentIdsLike(office);
		}
		return  new ArrayList<Office>();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}

	/**
	 * 获取机构树
	 * @return
	 */
	public List<OfficeTree> getTree(){
		List<OfficeTree> officeTrees = new ArrayList<OfficeTree>();
		String rootId = "";
		List<Office> Offices = findList(true);
		if(Offices!=null && Offices.size()>0) {
			for (Office office : Offices) {
				if (office.getParent()==null || (office.getParent()!=null && "0".equals(office.getParent().getId()))) {
					rootId = office.getId();
					OfficeTree officeTree = createTree(rootId);
					officeTrees.add(officeTree);
				}
			}
		}

		return officeTrees;
	}


	/**
	 * 拼装机构树
	 * @param officeId
	 * @return
	 */
	public OfficeTree createTree(String officeId) {
		OfficeTree node = null;
		List<OfficeTree> childTreeNodes = new ArrayList<OfficeTree>();

		List<Office> offices = findList(true);
		if(offices!=null && offices.size()>0) {
			//根据shebeiId获取节点对象
			for (Office office : offices) {
				if (officeId.equals(office.getId())) {
					node = new OfficeTree(office.getId(), office.getParent().getId(), office.getName());
					break;
				}
			}

			//查询节点对象下的所有子节点
			for (Office office : offices) {
				if (officeId.equals(office.getParent().getId())) {
					OfficeTree cNode = new OfficeTree(office.getId(), office.getParent().getId(), office.getName());
					childTreeNodes.add(cNode);
				}
			}

			//遍历子节点
			for (OfficeTree child: childTreeNodes) {
				OfficeTree n = createTree(child.getNodeValue()); //递归
				node.getChildren().add(n);
			}
		}

		return node;
	}


	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveFromPS(List<EhrDeptResult> depts){
		if(CollectionUtils.isNotEmpty(depts)){
			for(EhrDeptResult d:depts){
//			    if(d.getEffStatus().equals("I")){//表示无效
//				}else{
                    Office one = new Office();
                    one = EhrDeptConvertor.convertBean(d,new Office());
                    //判断该组织在系统存在不存在 存在就走更新 否则走新增
					if(get(one.getId())!=null && StringUtils.isNotBlank(get(one.getId()).getId())){
                        //存在 修改
						one.setUpdateDate(new Date());
						one.setUpdateBy(new User("1"));
						this.dao.update(one);

                    }else{
                        //新增
                        one.setCreateDate(new Date());
                        one.setUpdateDate(new Date());
                        one.setCreateBy(new User("1"));
                        one.setUpdateBy(new User("1"));
                        one.setDelFlag(Office.DEL_FLAG_NORMAL);
                        this.dao.insert(one);
                    }
                //}
			}
		}

		//取所有的数据 执行update
		List<Office> allOffice = this.dao.findAllListMine(new Office());
		for(Office o:allOffice){
			if(o.getParentIds()==null || o.getParentIds().equals("")){
				if(o.getParent().getId().equals("0")){
					o.setParentIds("0,");
				}else{
					o.setParent(get(o.getParent().getId()));
					o.setParentIds(arryChange(getParentIds(o.getParent(),new StringBuilder())));
				}
				this.dao.update(o);
			}
		}
		//UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);

	}


	/**
	 * 递归获取父节点ids
	 * @param office
	 * @param str
	 * @return
	 */
	public String getParentIds(Office office,StringBuilder str){

		if(office!=null && StringUtils.isNotBlank(office.getId())){
			if(!office.getId().equals("0")){
				str.append(office.getId()+",");
				office.setParent(get(office.getParent().getId()));
				getParentIds(office.getParent(),str);
			}else{
				str.append("0,");
			}
		}else{
			str.append("0,");
		}
		return str.toString();
	}

	/**
	 * 数据位置本末倒置
	 * @param str
	 * @return
	 */
	private String arryChange(String str){
		StringBuilder end = new StringBuilder();
		if(StringUtils.isNotBlank(str)){
			String[] oldIdArr = str.split(",");
			for(int i =oldIdArr.length-1;i>=0;i--){
				end.append(oldIdArr[i]+",");
			}
			System.out.println(end);
		}
		return  end.toString();
	}


	/**
	 * 添加组织同步发送任务(增量)
	 * @param apiAddress
	 * @param userName
	 * @param password
	 */
	private void addSyncOfficeAll(String apiAddress,String userName,String password)  {
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("apiAddress",apiAddress);
			param.put("userName",userName);
			param.put("password",password);
			param.put("synFlag","A");
			param.put("synDate",DateUtils.format(new Date(),"yyyy-MM-dd"));
			Job job = new Job();
			job.setParam("type", JobType.SyncOffice.getType());
			job.setTaskId(String.format("%s-%s", JobType.SyncOffice.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
			job.setParam("param", JsonMapper.toJsonString(param));
			job.setTaskTrackerNodeGroup(JobType.SyncOffice.getTaskTracker());
			job.setNeedFeedback(true);
			Response response = JobUtil.getJobClient().submitJob(job);
			if (!response.isSuccess()) {
				String msg = String.format("提交消息发送任务失败: %s",response.toString());
				log.error(msg);
			}
		}catch (JobSubmitException e){
		}
	}


	/**
	 * 添加组织同步发送任务(每天晚上增量同步)
	 * @param apiAddress
	 * @param userName
	 * @param password
	 */
	private void addSyncOffice(String apiAddress,String userName,String password)  {
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("apiAddress",apiAddress);
			param.put("userName",userName);
			param.put("password",password);
			param.put("synFlag","U");
			param.put("synDate",DateUtils.format(new Date(),"yyyy-MM-dd"));
			Job job = new Job();
			job.setParam("type", JobType.SyncOffice.getType());
			job.setTaskId(String.format("%s-%s", JobType.SyncOffice.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
			job.setParam("param", JsonMapper.toJsonString(param));
			job.setTaskTrackerNodeGroup(JobType.SyncOffice.getTaskTracker());
			job.setNeedFeedback(true);
			//设置任务的定时参数
			job.setCronExpression("0 50 23 ? * *");
			Response response = JobUtil.getJobClient().submitJob(job);
			if (!response.isSuccess()) {
				String msg = String.format("提交消息发送任务失败: %s",response.toString());
				log.error(msg);
			}
		}catch (JobSubmitException e){
		}
	}

	public Office getByOfficeCode(Office office){
		return this.dao.getByOfficeCode(office);
	}

	public List<Office> findListByNameIfExist(Office office){
	    return this.dao.findListByNameIfExist(office);
    }
}
