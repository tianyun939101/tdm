package com.demxs.tdm.service.sys;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.demxs.tdm.dao.sys.SysQuartzLogDao;
import com.demxs.tdm.service.external.IExternalApi;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.external.EhrDeptResult;
import com.demxs.tdm.domain.sys.SysQuartzLog;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 定时任务日志Service
 * @author sunjunhui
 * @version 2018-01-31
 */
@Service
@Transactional(readOnly = true)
public class SysQuartzLogService extends CrudService<SysQuartzLogDao, SysQuartzLog> {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private IExternalApi externalApi;
	@Autowired
	private OfficeService officeService;
	public SysQuartzLog get(String id) {
		return super.get(id);
	}
	
	public List<SysQuartzLog> findList(SysQuartzLog sysQuartzLog) {
		return super.findList(sysQuartzLog);
	}
	
	public Page<SysQuartzLog> findPage(Page<SysQuartzLog> page, SysQuartzLog sysQuartzLog) {
		return super.findPage(page, sysQuartzLog);
	}
	
	@Transactional(readOnly = false)
	public void save(SysQuartzLog sysQuartzLog) {
		super.save(sysQuartzLog);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysQuartzLog sysQuartzLog) {
		super.delete(sysQuartzLog);
	}


	public List<SysQuartzLog> getTodayDataByType(String type){

		return this.dao.getTodayDataByType(type);
	}


	public void updateDept(){
		logger.error("同步机构数据开始----");
		String apiAddress = "http://ehr.longi-silicon.com:8200/psc/ps/EMPLOYEE/HRMS/s/WEBLIB_LGI.ISCRIPT1.FieldFormula.IScript_Main?postDatabin=y";
		String userName = "PSAPPS";
		String password = "PSAPPS";
		String synData = getPreDate();
		try {
			List<EhrDeptResult> depts = externalApi.getEhrDeptList(apiAddress,userName,password,synData,"A");
			if(CollectionUtils.isNotEmpty(depts)){
				officeService.saveFromPS(depts);
			}
			logger.error("同步机构数据结束----");
	} catch (Exception e) {

		logger.error("同步机构数据错误",e);
	}

	}


	private String  getPreDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		return  sdf.format(date);
	}

}