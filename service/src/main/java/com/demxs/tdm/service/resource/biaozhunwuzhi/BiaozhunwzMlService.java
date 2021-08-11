package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzMlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzMl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 标准物质名录Service
 * @author zhangdengcai
 * @version 2017-08-31
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzMlService extends CrudService<BiaozhunwzMlDao, BiaozhunwzMl> {

	@Override
	public BiaozhunwzMl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzMl> findList(BiaozhunwzMl biaozhunwzMl) {
		return super.findList(biaozhunwzMl);
	}
	
	@Override
	public Page<BiaozhunwzMl> findPage(Page<BiaozhunwzMl> page, BiaozhunwzMl biaozhunwzMl) {
		return super.findPage(page, biaozhunwzMl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzMl biaozhunwzMl) {
		if(StringUtils.isBlank(biaozhunwzMl.getId())){//新增时
			List<BiaozhunwzMl> mls = this.dao.findByMcXh(biaozhunwzMl);
			List<BiaozhunwzMl> mls2 = this.dao.findByBm(biaozhunwzMl);
			if((mls!=null && !mls.isEmpty()) || (mls2!=null && !mls2.isEmpty())){
				throw new RuntimeException("该名录已存在！");
			}
		}
		super.save(biaozhunwzMl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzMl biaozhunwzMl) {
		super.delete(biaozhunwzMl);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void batchDelete(String ids){
		if(StringUtils.isNotBlank(ids)){
			String[] idsArr = ids.split(",");
			BiaozhunwzMl biaozhunwzMl = new BiaozhunwzMl();
			biaozhunwzMl.setArrIDS(idsArr);
			this.dao.batchDelete(biaozhunwzMl);
		}
	}
}