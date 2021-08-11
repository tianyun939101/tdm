package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinCfwzDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.YangpinCfwz;
import com.demxs.tdm.comac.common.constant.YpConstans;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品存放位置Service
 * @author 詹小梅
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinCfwzService extends CrudService<YangpinCfwzDao, YangpinCfwz> {

	@Override
	public YangpinCfwz get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinCfwz> findList(YangpinCfwz yangpinCfwz) {
		return super.findList(yangpinCfwz);
	}
	
	@Override
	public Page<YangpinCfwz> findPage(Page<YangpinCfwz> page, YangpinCfwz yangpinCfwz) {
		if(UserUtils.getUser()!=null){
			//yangpinCfwz.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			yangpinCfwz.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page, yangpinCfwz);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinCfwz entity) {
		if(StringUtils.isNotBlank(entity.getId())){
			if (!entity.getFuzhuj().equals("0")){
				entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
				entity.setWeizhimcs(super.get(entity.getFuzhuj()).getWeizhimcs()+"_"+entity.getWeizhimc());
			}else{
				entity.setFuzhujs(",0,");
				entity.setWeizhimcs(entity.getWeizhimc());
			}
			super.save(entity);
			if(entity.getYouxiaox().equals(YpConstans.optYouxiaox.WUXIAO)){
				this.modifyYxxByfzj(entity);
			}
		}else{
			if (!entity.getFuzhuj().equals("0")){
				entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
				entity.setWeizhimcs(super.get(entity.getFuzhuj()).getWeizhimcs()+"_"+entity.getWeizhimc());
			}else{
				entity.setFuzhujs(",0,");
				entity.setWeizhimcs(entity.getWeizhimc());
			}
			super.save(entity);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinCfwz yangpinCfwz) {
		super.delete(yangpinCfwz);
	}

	/**
	 * 更新有效性
	 * @param entity
	 */
	public void modifyYxxByfzj(YangpinCfwz entity){
		String fuzhuj = ","+entity.getId()+",";
		entity.setFuzhuj(fuzhuj);
		this.dao.modifyYxxByfzj(entity);
	}
	
}