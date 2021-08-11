package com.demxs.tdm.service.resource.renyuan;

import com.demxs.tdm.dao.resource.renyuan.RenyuanGrrcDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.renyuan.RenyuanGrrc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 个人日程Service
 * @author zhangdengcai
 * @version 2017-08-09
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RenyuanGrrcService extends CrudService<RenyuanGrrcDao, RenyuanGrrc> {

	@Override
	public RenyuanGrrc get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<RenyuanGrrc> findList(RenyuanGrrc renyuanGrrc) {
		if(UserUtils.getUser()!=null){
			renyuanGrrc.getSqlMap().put("dsf", "and a.create_by = '"+UserUtils.getUser().getId()+"'");
		}
		return super.findList(renyuanGrrc);
	}
	
	@Override
	public Page<RenyuanGrrc> findPage(Page<RenyuanGrrc> page, RenyuanGrrc renyuanGrrc) {
		return super.findPage(page, renyuanGrrc);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(RenyuanGrrc renyuanGrrc) {
		renyuanGrrc.setShifouwc(Global.NO);
		super.save(renyuanGrrc);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(RenyuanGrrc renyuanGrrc) {
		super.delete(renyuanGrrc);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteMore(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] idsArr = ids.split(",");
			RenyuanGrrc renyuanGrrc = new RenyuanGrrc();
			renyuanGrrc.setArrIDS(idsArr);
			this.dao.batchDelete(renyuanGrrc);
		}
	}
}