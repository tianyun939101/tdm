package com.demxs.tdm.service.resource.renyuan;

import com.demxs.tdm.dao.resource.renyuan.RenyuanZzDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.renyuan.RenyuanZz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员资质(方法,设备)Service
 * @author 詹小梅
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RenyuanZzService extends CrudService<RenyuanZzDao, RenyuanZz> {

	@Autowired
	private RenyuanService renyuanService;

	@Override
	public RenyuanZz get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<RenyuanZz> findList(RenyuanZz renyuanZz) {
		return super.findList(renyuanZz);
	}
	
	@Override
	public Page<RenyuanZz> findPage(Page<RenyuanZz> page, RenyuanZz renyuanZz) {
		return super.findPage(page, renyuanZz);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(RenyuanZz renyuanZz) {
		super.save(renyuanZz);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(RenyuanZz renyuanZz) {
		super.delete(renyuanZz);
	}


	/**
	 * 比较新关联的人员资质和历史人员资质
	 * 如果
	 * @param newzz
	 * @param renyuanid
	 */
	public void compareZz(RenyuanZz[] newzz,String renyuanid) {
		this.dao.deleteRyzz(renyuanid);
		List<RenyuanZz> newzzlist = new ArrayList<RenyuanZz>();
		RenyuanZz entity = null;
		for (int i = 0; i < newzz.length; i++) {
			entity = new RenyuanZz();
			entity.setRenyuanid(renyuanid);
			entity.setZizhiid(newzz[i].getZizhiid());
			this.save(entity);
		}
	}
		/**
		 * 比较新关联的人员资质和历史人员资质
		 * 如果
		 * @param renyuanids
		 * @param zizhiid
		 */
	public void compareZzForZizhiid(RenyuanZz[] renyuanids,String zizhiid){
		this.dao.deleteRyzzByZizhiid(zizhiid);
		List<RenyuanZz> newzzlist = new ArrayList<RenyuanZz>();
		RenyuanZz entity =null;
		for(int i=0;i<renyuanids.length;i++){
			entity =new RenyuanZz();
			//通过用户的ID获取员工ID
//			Renyuan renyuan = new Renyuan();
//			renyuan.setUserId(renyuanids[i].getRenyuanid());
//			List<Renyuan> renyuanList = renyuanService.findList(renyuan);
//			if(renyuanList != null & renyuanList.size() > 0){
//				entity.setRenyuanid(renyuanList.get(0).getId());
//			}
			entity.setRenyuanid(renyuanids[i].getRenyuanid());
			entity.setZizhiid(zizhiid);
			this.save(entity);
		}
	}




}