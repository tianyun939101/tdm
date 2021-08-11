package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiMlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.haocai.HaocaiMl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 耗材名录Service
 * @author zhangdengcai
 * @version 2017-08-31
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiMlService extends CrudService<HaocaiMlDao, HaocaiMl> {

	@Override
	public HaocaiMl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HaocaiMl> findList(HaocaiMl haocaiMl) {
		return super.findList(haocaiMl);
	}
	
	@Override
	public Page<HaocaiMl> findPage(Page<HaocaiMl> page, HaocaiMl haocaiMl) {
		return super.findPage(page, haocaiMl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiMl haocaiMl) {
		if(StringUtils.isBlank(haocaiMl.getId())){//新增
			List<HaocaiMl> mls = this.dao.findByMcXh(haocaiMl);
			if(mls!=null && !mls.isEmpty()){
				throw new RuntimeException("该耗材名录已存在！");
			}
		}
		super.save(haocaiMl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiMl haocaiMl) {
		super.delete(haocaiMl);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void batchDelete(String ids){
		if(StringUtils.isNotBlank(ids)){
			String[] idsArr = ids.split(",");
			HaocaiMl haocaiMl = new HaocaiMl();
			haocaiMl.setArrIDS(idsArr);
			this.dao.batchDelete(haocaiMl);
		}
	}
}