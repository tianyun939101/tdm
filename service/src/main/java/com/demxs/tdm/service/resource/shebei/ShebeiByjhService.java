package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.quartz.QuartzJobDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiByjhDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.quartz.QuartzJob;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiByjh;
import com.demxs.tdm.domain.resource.shebei.ShebeiByjl;
import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.job.QuartzManager;
import com.demxs.tdm.service.job.*;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 设备保养计划Service
 * @author zhangdengcai
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiByjhService extends CrudService<ShebeiByjhDao, ShebeiByjh> {

	@Autowired
	private QuartzJobDao quartzJobDao;
	@Autowired
	private QuartzJobService quartzJobService;
	@Autowired
	private ShebeiByjlService shebeiByjlService;
	@Autowired
	private ShebeiDao shebeiDao;
	@Autowired
	private SystemService systemService;

	@Autowired
	private ExternalApi externalApi;

	private ShebeiByjh getAll(ShebeiByjh shebeiByjh){

		//获取该计划里面的所有记录
		shebeiByjh.setBaoyangjl(shebeiByjlService.listByByjhid(shebeiByjh.getId()));
		if(shebeiByjh.getCreateBy()!=null && StringUtils.isNotBlank(shebeiByjh.getCreateBy().getId())){
			shebeiByjh.setCreateBy(UserUtils.get(shebeiByjh.getCreateBy().getId()));
		}
		return shebeiByjh;
	}

	@Override
	public ShebeiByjh get(String id) {
		return getAll(super.get(id));
	}
	
	@Override
	public List<ShebeiByjh> findList(ShebeiByjh shebeiByjh) {
		List<ShebeiByjh> shebeiByjhList = super.findList(shebeiByjh);
		if(!Collections3.isEmpty(shebeiByjhList)){
			for(ShebeiByjh s:shebeiByjhList){
				getAll(s);
			}
		}
		return shebeiByjhList;
	}

	/**
	 * 计划分页列表
	 * @param page 分页对象
	 * @param shebeiByjh
	 * @return
	 */
	@Override
	public Page<ShebeiByjh> findPage(Page<ShebeiByjh> page, ShebeiByjh shebeiByjh) {
		Page<ShebeiByjh> dataValue = super.findPage(page, shebeiByjh);
		if(dataValue!=null && !Collections3.isEmpty(dataValue.getList())){
			for(ShebeiByjh s:dataValue.getList()){
				getAll(s);
			}
		}
		return dataValue;
	}

	/**
	 * 保存保养计划
	 * @param shebeiByjh
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiByjh shebeiByjh) {
		Shebei shebei = shebeiDao.get(shebeiByjh.getShebeiid());


		if(StringUtils.isNotBlank(shebeiByjh.getId())){
			ShebeiByjh shebeiJljdjhs = this.dao.get(shebeiByjh.getId());
			shebeiJljdjhs.setBaoyangyq(shebeiByjh.getBaoyangyq());
			shebeiJljdjhs.setBaoyangcs(shebeiByjh.getBaoyangcs());
			super.save(shebeiJljdjhs);
		}else{


			//保存保养计划
			super.save(shebeiByjh);
			//保存保养计划里面的记录
			//删除全部记录
			shebeiByjlService.deleteByByjhid(shebeiByjh.getId());
			//新增最新的记录
			if(!Collections3.isEmpty(shebeiByjh.getBaoyangjl())){
				for(ShebeiByjl shebeiByjl:shebeiByjh.getBaoyangjl()){
					shebeiByjl.setShebeiid(shebeiByjh.getShebeiid());
					//给保养记录赋值保养计划id 传过来的数据都是没有保养记录id
					shebeiByjl.setBaoyangjhid(shebeiByjh.getId());
					shebeiByjlService.save(shebeiByjl);
					if(StringUtils.isNotBlank(shebeiByjl.getId())){
						//DateUtils.parseDate(shebeiByjl.getBaoyangksrq())
						if(DateUtils.getAfterHDayTime(DateUtils.parseDate(shebeiByjl.getJihuabyrq()),1).compareTo(new Date())<=0){
							sendByTodo(shebei.getId(),shebei.getShebeibh(),systemService.getUser(shebeiByjl.getBaoyangrId()).getLoginName(),DateUtils.parseDate(shebeiByjl.getJihuabyrq()),shebeiByjh.getId());
						}else{
							String id = UUID.randomUUID().toString();
							Map<String,Object> param = new HashMap<>();
							param.put("id",id);
							param.put("shebeiid",shebei.getId());
							param.put("shebeibh",shebei.getShebeibh());
							param.put("jihuaid",shebeiByjh.getId());
							param.put("jiluid",shebeiByjl.getId());
							param.put("txDate",shebeiByjl.getJihuabyrq());
							param.put("loginName",systemService.getUser(shebeiByjl.getBaoyangrId()).getLoginName());
							QuartzJob quartzJob = new QuartzJob("设备保养发消息任务"+id,"shebei_by_send_group","shebei_by_send_trigger"+id,"shebei_by_send_trigger_group"+id, ShebeiConstans.quartzClassType.SHEBEI_BY,DateUtils.getCron(DateUtils.getAfterHDayTime(DateUtils.parseDate(shebeiByjl.getJihuabyrq()),1)),DateUtils.getAfterHDayTime(DateUtils.parseDate(shebeiByjl.getJihuabyrq()),1),JsonMapper.toJsonString(param),"0");
							quartzJob.setId(id);
							quartzJob.setDelFlag("0");
							quartzJobDao.insert(quartzJob);
							QuartzManager.addJob(quartzJob);
							//QuartzManager.addJob("设备保养发消息任务"+ UUID.randomUUID(), "shebei_by_send_group", "shebei_by_send_trigger"+UUID.randomUUID(), "shebei_by_send_trigger_group"+UUID.randomUUID(), ShebeiByJob.class, DateUtils.getCron(DateUtils.getAfterHDayTime(DateUtils.parseDate(shebeiByjl.getJihuabyrq()),1)), JsonMapper.toJsonString(param));

						}


						Date txDate = DateUtils.getAfterHDayTime(DateUtils.parseDate(shebeiByjl.getJihuabyrq()),0-Integer.parseInt(shebeiByjh.getBaoyangtxts()));
						if(DateUtils.compare(txDate,new Date())<=0){
							if(shebei.getShebeigly()!=null){
								sendByTxTodo(shebei.getId(),shebei.getShebeibh(),systemService.getUser(shebei.getShebeigly().getId()).getLoginName(),DateUtils.parseDate(shebeiByjl.getJihuabyrq()),shebeiByjh.getId());
							}
						}else{
							if(shebei.getShebeigly()!=null){
								String txid = UUID.randomUUID().toString();
								Map<String,Object> result = new HashMap<>();
								result.put("id",txid);
								result.put("shebeiid",shebei.getId());
								result.put("shebeibh",shebei.getShebeibh());
								result.put("jihuaid",shebeiByjh.getId());
								result.put("txDate",shebeiByjl.getJihuabyrq());
								result.put("loginName",systemService.getUser(shebei.getShebeigly().getId()).getLoginName());
								QuartzJob quartzJob = new QuartzJob("设备保养提醒发消息任务"+txid,"shebei_by_tx_send_group","shebei_by_tx_send_trigger"+txid,"shebei_by_tx_send_trigger_group"+txid, ShebeiConstans.quartzClassType.SHEBEI_BY_TX,DateUtils.getCron(txDate),txDate,JsonMapper.toJsonString(result),"0");
								quartzJob.setId(txid);
								quartzJob.setDelFlag("0");
								quartzJobDao.insert(quartzJob);
								QuartzManager.addJob(quartzJob);
								//QuartzManager.addJob("设备保养提醒发消息任务"+ UUID.randomUUID(), "shebei_by_tx_send_group", "shebei_by_tx_send_trigger"+UUID.randomUUID(), "shebei_by_tx_send_trigger_group"+UUID.randomUUID(),ShebeiByTxJob.class, DateUtils.getCron(txDate), JsonMapper.toJsonString(result));
							}
						}
					}

				}
			}
		}


	}


	public void sendByTxTodo(String shebeiid, String shebeibh, String loginName, Date date, String jihuaid){
		SendTodoParam sendTodoParam = new SendTodoParam();
		sendTodoParam.setType(2);
		sendTodoParam.setSubject(String.format(MessageConstants.qtMessage.QT,shebeibh,shebeiDao.get(shebeiid).getShebeimc(),DateUtils.formatDate(date),"保养"));
		sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/shebei/baoyang/edit?id="+jihuaid);
		sendTodoParam.setCreateTime(new Date());
		sendTodoParam.setModelId(IdGen.uuid());
		sendTodoParam.addTarget(loginName);
		externalApi.sendTodo(sendTodoParam);
	}

	public void sendByTodo(String shebeiid,String shebeibh,String loginName,Date date,String jihuid){
		SendTodoParam sendTodoParam = new SendTodoParam();
		sendTodoParam.setType(2);
		sendTodoParam.setSubject(String.format(MessageConstants.qtMessage.TX,shebeibh,shebeiDao.get(shebeiid).getShebeimc(),DateUtils.formatDate(date),"保养"));
		sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/shebei/baoyang/edit?id="+jihuid);
		sendTodoParam.setCreateTime(new Date());
		sendTodoParam.setModelId(IdGen.uuid());
		sendTodoParam.addTarget(loginName);
		externalApi.sendTodo(sendTodoParam);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiByjh shebeiByjh) {
		super.delete(shebeiByjh);
		shebeiByjlService.deleteByByjhid(shebeiByjh.getId());
	}

	/**
	 * 查询全部设备的保养计划
	 * @param page
	 * @param shebeiByjh
	 * @return
	 */
	public Page<ShebeiByjh> findAllPage(Page<ShebeiByjh> page, ShebeiByjh shebeiByjh) {
//		page.setOrderBy("a.create_date desc");
		shebeiByjh.setPage(page);
		page.setList(this.dao.findAllList(shebeiByjh));
		return page;
	}
}