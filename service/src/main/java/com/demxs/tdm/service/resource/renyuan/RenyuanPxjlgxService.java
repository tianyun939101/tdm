package com.demxs.tdm.service.resource.renyuan;

import com.demxs.tdm.dao.resource.renyuan.RenyuanPxjlgxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.resource.renyuan.RenyuanPxjlgx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 被培训人员与培训记录关系Service
 * @author 詹小梅
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RenyuanPxjlgxService extends CrudService<RenyuanPxjlgxDao, RenyuanPxjlgx> {

	@Override
	public RenyuanPxjlgx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<RenyuanPxjlgx> findList(RenyuanPxjlgx renyuanPxjlgx) {
		return super.findList(renyuanPxjlgx);
	}
	
	@Override
	public Page<RenyuanPxjlgx> findPage(Page<RenyuanPxjlgx> page, RenyuanPxjlgx renyuanPxjlgx) {
		if(UserUtils.getUser()!=null){
			//renyuanPxjlgx.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			renyuanPxjlgx.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page, renyuanPxjlgx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(RenyuanPxjlgx renyuanPxjlgx) {
		super.save(renyuanPxjlgx);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(RenyuanPxjlgx renyuanPxjlgx) {
		super.delete(renyuanPxjlgx);
	}
	//删除被培训人员与培训记录关系
	public void deletepxgx(String peixinid){
		this.dao.deletepxgx(peixinid);
	}

	/**
	 * 更新培训成绩
	 * @param entity
	 */
	public void updateCj(RenyuanPxjlgx entity){
		entity.preUpdate();
		this.dao.updateCj(entity);
	}

	/**
	 * 删除培训人员关系
	 * @param list
	 */
	public void batchDelete(List<RenyuanPxjlgx> list){
		this.dao.batchDelete(list);
	}

	/**
	 * 批量添加培训人员
	 * @param list
	 */
	public void batchInsert(List<RenyuanPxjlgx> list){
		this.dao.batchInsert(list);
	}
	
}