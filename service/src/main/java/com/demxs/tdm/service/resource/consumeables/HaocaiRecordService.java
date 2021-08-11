package com.demxs.tdm.service.resource.consumeables;

import java.util.List;

import com.demxs.tdm.dao.resource.consumeables.HaocaiRecordDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import com.demxs.tdm.domain.resource.consumeables.HaocaiRecord;
import com.demxs.tdm.service.resource.haocai.HaocaiLxService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 耗材流转记录Service
 * @author sunjunhui
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class HaocaiRecordService extends CrudService<HaocaiRecordDao, HaocaiRecord> {

	@Autowired
	private HaocaiLxService haocaiLxService;
	@Autowired
	private SystemService systemService;

	private HaocaiRecord getAll(HaocaiRecord haocaiRecord){
		//类型
		if(haocaiRecord.getHaocaiLx()!=null && StringUtils.isNotBlank( haocaiRecord.getHaocaiLx().getId())){
			haocaiRecord.setHaocaiLx(haocaiLxService.get(haocaiRecord.getHaocaiLx().getId()));
		}else {
			haocaiRecord.setHaocaiLx(new HaocaiLx());
		}
		if(haocaiRecord.getAcceptUser()!=null && StringUtils.isNotBlank(haocaiRecord.getAcceptUser().getId())){
			haocaiRecord.setAcceptUser(systemService.getUser(haocaiRecord.getAcceptUser().getId()));
		}else{
			haocaiRecord.setAcceptUser(new User());
		}

		if(haocaiRecord.getBackUser()!=null && StringUtils.isNotBlank(haocaiRecord.getBackUser().getId())){
			haocaiRecord.setBackUser(systemService.getUser(haocaiRecord.getBackUser().getId()));
		}else{
			haocaiRecord.setBackUser(new User());
		}
		return haocaiRecord;
	}

	private List<HaocaiRecord> getAll(List<HaocaiRecord> haocaiRecords){
		if(!Collections3.isEmpty(haocaiRecords)){
			for(HaocaiRecord h:haocaiRecords){
				getAll(h);
			}
		}
		return haocaiRecords;
	}

	public HaocaiRecord get(String id) {
		return getAll(super.get(id));
	}
	
	public List<HaocaiRecord> findList(HaocaiRecord haocaiRecord) {
		return getAll(super.findList(haocaiRecord));
	}
	
	public Page<HaocaiRecord> findPage(Page<HaocaiRecord> page, HaocaiRecord haocaiRecord) {

		haocaiRecord.getSqlMap().put("dsf", dataScopeFilter(haocaiRecord.getCurrentUser(), "o", "u8"));
		Page<HaocaiRecord> dataValue = super.findPage(page, haocaiRecord);
		if(dataValue!=null && !Collections3.isEmpty(dataValue.getList())){
				getAll(dataValue.getList());
		}
		return dataValue;
	}
	
	@Transactional(readOnly = false)
	public void save(HaocaiRecord haocaiRecord) {
		super.save(haocaiRecord);
	}
	
	@Transactional(readOnly = false)
	public void delete(HaocaiRecord haocaiRecord) {
		super.delete(haocaiRecord);
	}
	
}