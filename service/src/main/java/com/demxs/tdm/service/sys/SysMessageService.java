package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.SysMessageDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.ObjectUtils;
import com.demxs.tdm.domain.sys.SysMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统消息Service
 * @author 谭冬梅
 * @version 2017-09-06
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class SysMessageService extends CrudService<SysMessageDao, SysMessage> implements ISysMessageService {

	@Override
	public SysMessage get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<SysMessage> findList(SysMessage sysMessage) {
		return super.findList(sysMessage);
	}
	
	@Override
	public Page<SysMessage> findPage(Page<SysMessage> page, SysMessage sysMessage) {
		return super.findPage(page, sysMessage);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(SysMessage sysMessage) {
		super.save(sysMessage);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(SysMessage sysMessage) {
		super.delete(sysMessage);
	}

	//api 保存系统消息
	@Override
	public void saveMessage(SysMessageDTO sysMessageDTO){
		SysMessage sysMessage = new SysMessage();
		ObjectUtils.returnForUpdateObject(sysMessage,sysMessageDTO);
		this.save(sysMessage);
	}
}