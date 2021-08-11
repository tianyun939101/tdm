package com.demxs.tdm.service.business;

import com.demxs.tdm.dao.business.DeviceUseOffDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.business.DeviceUseOff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


/**
 * 试验设备使用结束时间Service
 * @author sunjunhui
 * @version 2017-10-30
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DeviceUseOffService extends CrudService<DeviceUseOffDao, DeviceUseOff> {

	@Override
	public DeviceUseOff get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<DeviceUseOff> findList(DeviceUseOff deviceUseOff) {
		return super.findList(deviceUseOff);
	}
	
	@Override
	public Page<DeviceUseOff> findPage(Page<DeviceUseOff> page, DeviceUseOff deviceUseOff) {
		return super.findPage(page, deviceUseOff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(DeviceUseOff deviceUseOff) {
		super.save(deviceUseOff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(DeviceUseOff deviceUseOff) {
		super.delete(deviceUseOff);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void update(String deviceId, Integer useStation, Date planEndTime) {
		this.dao.update(deviceId,useStation,planEndTime);
	}
}