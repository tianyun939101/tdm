package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.quartz.QuartzJobDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiJljdjhDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.quartz.QuartzJob;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjh;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjl;
import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.job.QuartzManager;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 设备计量检定计划Service
 * @author zhangdengcai
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiJljdjhService extends CrudService<ShebeiJljdjhDao, ShebeiJljdjh> {


	@Autowired
	private QuartzJobDao quartzJobDao;
	@Autowired
	private QuartzJobService quartzJobService;

	@Autowired
	private ShebeiJljdjlService shebeiJljdjlService;
	@Autowired
	private ShebeiDao shebeiDao;

	@Autowired
	private ExternalApi externalApi;
	@Autowired
	private SystemService systemService;

	private ShebeiJljdjh getAll(ShebeiJljdjh shebeiJljdjh){
		shebeiJljdjh.setShebeiJljdjl(shebeiJljdjlService.listByJljdjhid(shebeiJljdjh.getId()));
		shebeiJljdjh.setJiliangjdlxr(systemService.getUser(shebeiJljdjh.getJiliangjdlxr().getId()));
		return shebeiJljdjh;
	}
	@Override
	public ShebeiJljdjh get(String id) {
		return getAll(super.get(id));
	}

	@Override
	public List<ShebeiJljdjh> findList(ShebeiJljdjh shebeiJljdjh) {
		List<ShebeiJljdjh> shebeiJljdjhs = super.findList(shebeiJljdjh);
		if(!Collections3.isEmpty(shebeiJljdjhs)){
			for(ShebeiJljdjh s:shebeiJljdjhs){
				getAll(s);
			}
		}
		return shebeiJljdjhs;
	}
	
	@Override
	public Page<ShebeiJljdjh> findPage(Page<ShebeiJljdjh> page, ShebeiJljdjh shebeiJljdjh) {
		Page<ShebeiJljdjh> dataValue = super.findPage(page, shebeiJljdjh);
		if(dataValue!=null && !Collections3.isEmpty(dataValue.getList())){
			for(ShebeiJljdjh s:dataValue.getList()){
				getAll(s);
			}
		}
		return dataValue;
	}


	/**
	 * 保存
	 * @param shebeiJljdjh
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiJljdjh shebeiJljdjh) {
		Shebei shebei = shebeiDao.get(shebeiJljdjh.getShebeiid());


		if(StringUtils.isNotBlank(shebeiJljdjh.getId())){
			ShebeiJljdjh shebeiJljdjhs = this.dao.get(shebeiJljdjh.getId());
			shebeiJljdjhs.setJiliangjdlxrId(shebeiJljdjh.getJiliangjdlxrId());
			shebeiJljdjhs.setJiliangjdlxfs(shebeiJljdjh.getJiliangjdlxfs());
			shebeiJljdjhs.setJiliangjdnr(shebeiJljdjh.getJiliangjdnr());
			shebeiJljdjhs.setJiliangjddw(shebeiJljdjh.getJiliangjddw());
			shebeiJljdjhs.setJiliangfs(shebeiJljdjh.getJiliangfs());
			super.save(shebeiJljdjhs);
		}else{
			super.save(shebeiJljdjh);
			//删除记录
			shebeiJljdjlService.deleteByJljdjhid(shebeiJljdjh.getId());

			//新增最新的记录
			if(!Collections3.isEmpty(shebeiJljdjh.getShebeiJljdjl())){
				for(ShebeiJljdjl s:shebeiJljdjh.getShebeiJljdjl()){
					s.setShebeiid(shebeiJljdjh.getShebeiid());
					s.setJiliangjdjhid(shebeiJljdjh.getId());
					shebeiJljdjlService.save(s);
					if(StringUtils.isNotBlank(s.getId())){
						if(DateUtils.getAfterHDayTime(DateUtils.parseDate(s.getJihuajljdrq()),1).compareTo(new Date())<=0){
							sendDjTodo(shebei.getId(),shebei.getShebeibh(),systemService.getUser(shebeiJljdjh.getJiliangjdlxrId()).getLoginName(),DateUtils.parseDate(s.getJihuajljdrq()),shebeiJljdjh.getId());
						}else{
							String id = UUID.randomUUID().toString();
							Map<String,Object> param = new HashMap<>();
							param.put("id",id);
							param.put("shebeiid",shebeiJljdjh.getShebeiid());
							param.put("shebeibh",shebeiDao.get(shebeiJljdjh.getShebeiid()).getShebeibh());
							param.put("jihuaid",shebeiJljdjh.getId());
							param.put("jiluid",s.getId());
							param.put("txDate",s.getJihuajljdrq());
							param.put("loginName",systemService.getUser(shebeiJljdjh.getJiliangjdlxrId()).getLoginName());
							QuartzJob quartzJob = new QuartzJob("设备定检发消息任务"+id,"shebei_dj_send_group","shebei_dj_send_trigger"+id,"shebei_dj_send_trigger_group"+id, ShebeiConstans.quartzClassType.SHEBEI_DJ,DateUtils.getCron(DateUtils.getAfterHDayTime(DateUtils.parseDate(s.getJihuajljdrq()),1)),DateUtils.getAfterHDayTime(DateUtils.parseDate(s.getJihuajljdrq()),1),JsonMapper.toJsonString(param),"0");
							quartzJob.setId(id);
							quartzJob.setDelFlag("0");
							quartzJobDao.insert(quartzJob);
							QuartzManager.addJob(quartzJob);
						}

						Date txDate = DateUtils.getAfterHDayTime(DateUtils.parseDate(s.getJihuajljdrq()),0-Integer.parseInt(shebeiJljdjh.getJiliangjdtxts()));
						if(DateUtils.compare(txDate,new Date())<=0){
							if(shebei.getShebeigly()!=null){
								sendDjTxTodo(shebei.getId(),shebei.getShebeibh(),systemService.getUser(shebei.getShebeigly().getId()).getLoginName(),DateUtils.parseDate(s.getJihuajljdrq()),shebeiJljdjh.getId());
							}
						}else{
							if(shebei.getShebeigly()!=null){
								String id = UUID.randomUUID().toString();
								Map<String,Object> result = new HashMap<>();
								result.put("id",id);
								result.put("shebeiid",shebei.getId());
								result.put("shebeibh",shebei.getShebeibh());
								result.put("jihuaid",shebeiJljdjh.getId());
								result.put("txDate",s.getJihuajljdrq());
								result.put("loginName",systemService.getUser(shebei.getShebeigly().getId()).getLoginName());
								QuartzJob quartzJob = new QuartzJob("设备定检提醒发消息任务"+id,"shebei_dj_tx_send_group","shebei_dj_tx_send_trigger"+id,"shebei_dj_tx_send_trigger_group"+id, ShebeiConstans.quartzClassType.SHEBEI_DJ_TX,DateUtils.getCron(txDate),txDate,JsonMapper.toJsonString(result),"0");
								quartzJob.setId(id);
								quartzJob.setDelFlag("0");
								quartzJobDao.insert(quartzJob);
								QuartzManager.addJob(quartzJob);
							}
						}
					}
				}
			}
		}
	}


	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiJljdjh shebeiJljdjh) {
		super.delete(shebeiJljdjh);
		shebeiJljdjlService.deleteByJljdjhid(shebeiJljdjh.getId());

	}

	public void sendDjTxTodo(String shebeiid,String shebeibh,String loginName,Date date,String jihuaid){
		SendTodoParam sendTodoParam = new SendTodoParam();
		sendTodoParam.setType(2);
		sendTodoParam.setSubject(String.format(MessageConstants.qtMessage.QT,shebeibh,shebeiDao.get(shebeiid).getShebeimc(),DateUtils.formatDate(date),"计量检定"));
		sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/shebei/dingjian/edit?id="+jihuaid);
		sendTodoParam.setCreateTime(new Date());
		sendTodoParam.setModelId(IdGen.uuid());
		sendTodoParam.addTarget(loginName);
		externalApi.sendTodo(sendTodoParam);
	}

	public void sendDjTodo(String shebeiid,String shebeibh,String loginName,Date date,String jihuid){
		Shebei shebei = shebeiDao.get(shebeiid);
		String msg = shebei==null ? "" :shebei.getShebeimc();
		SendTodoParam sendTodoParam = new SendTodoParam();
		sendTodoParam.setType(2);
		sendTodoParam.setSubject(String.format(MessageConstants.qtMessage.TX,shebeibh,msg,DateUtils.formatDate(date),"计量检定"));
		sendTodoParam.setLink(Global.getConfig("activiti.form.server.url")+"/f/shebei/dingjian/eidt?id="+jihuid);
		sendTodoParam.setCreateTime(new Date());
		sendTodoParam.setModelId(IdGen.uuid());
		sendTodoParam.addTarget(loginName);
		externalApi.sendTodo(sendTodoParam);
	}

}