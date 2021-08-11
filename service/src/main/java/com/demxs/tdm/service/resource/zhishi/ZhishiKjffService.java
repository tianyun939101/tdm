package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiKjffDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.zhishi.ZhishiKjff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识可见范围Service
 * @author 詹小梅
 * @version 2017-06-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiKjffService extends CrudService<ZhishiKjffDao, ZhishiKjff> {

	@Override
	public ZhishiKjff get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ZhishiKjff> findList(ZhishiKjff zhishiKjff) {
		return super.findList(zhishiKjff);
	}
	
	@Override
	public Page<ZhishiKjff> findPage(Page<ZhishiKjff> page, ZhishiKjff zhishiKjff) {
		return super.findPage(page, zhishiKjff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZhishiKjff zhishiKjff) {
		super.save(zhishiKjff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiKjff zhishiKjff) {
		super.delete(zhishiKjff);
	}

	/**
	 * 根据知识id，删除可见范围
	 * @param zhishiid
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteZhishiKjff(String zhishiid){
		ZhishiKjff zhishiKjff = new ZhishiKjff();
		zhishiKjff.setZhishiid(zhishiid);
		this.dao.deleteZhishiKjff(zhishiKjff);
	}

	/**
	 * 获取此组织以及其子组织有权限的设备id
	 * @param officeId
	 * @return
	 */
	public List<String> getShebeiidsByOffice(String officeId,String userId){
		return this.dao.getShebeiidsByOffice(officeId,userId);
	}
}