package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiKjffRyDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.zhishi.ZhishiKjffRy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识可见范围-人员Service
 * @author 詹小梅
 * @version 2017-07-12
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiKjffRyService extends CrudService<ZhishiKjffRyDao, ZhishiKjffRy> {

	@Override
	public ZhishiKjffRy get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ZhishiKjffRy> findList(ZhishiKjffRy zhishiKjffRy) {
		return super.findList(zhishiKjffRy);
	}
	
	@Override
	public Page<ZhishiKjffRy> findPage(Page<ZhishiKjffRy> page, ZhishiKjffRy zhishiKjffRy) {
		return super.findPage(page, zhishiKjffRy);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZhishiKjffRy zhishiKjffRy) {
		super.save(zhishiKjffRy);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiKjffRy zhishiKjffRy) {
		super.delete(zhishiKjffRy);
	}

	/**
	 * 根据知识id，删除可见人员
	 * @param zhishiid
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteZhishiKjffRy(String zhishiid){
		ZhishiKjffRy zhishiKjffRy = new ZhishiKjffRy();
		zhishiKjffRy.setZhishiid(zhishiid);
		this.dao.deleteZhishiKjffRy(zhishiKjffRy);
	}


	/**
	 * 获取此人有权限的设备id
	 * @param userId
	 * @return
	 */
	public List<String> getShebeiidsByUser(String userId){
		return this.dao.getShebeiidsByUser(userId);
	}
}