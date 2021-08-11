package com.demxs.tdm.service.resource.shebei;


import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiCfwzDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.ShebeiCfwz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备存放位置Service
 * @author zhangdengcai
 * @version 2017-06-13
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiCfwzService extends TreeService<ShebeiCfwzDao, ShebeiCfwz> {

    @Autowired
	private LabInfoDao labInfoDao;
	@Autowired
	private ShebeiCfwzDao shebeiCfwzDao;
	@Override
	public ShebeiCfwz get(String id) {
		ShebeiCfwz p = this.dao.get(id);
		if(p != null && p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public List<ShebeiCfwz> findList(ShebeiCfwz shebeiCfwz) {
		if (StringUtils.isNotBlank(shebeiCfwz.getParentIds())){
			shebeiCfwz.setParentIds(","+shebeiCfwz.getParentIds()+",");
		}
		shebeiCfwz.getSqlMap().put("dsf", dataScopeFilter(shebeiCfwz.getCurrentUser(), "o", "u8"));
		return super.findList(shebeiCfwz);
	}

	@Override
	public Page<ShebeiCfwz> findPage(Page<ShebeiCfwz> page, ShebeiCfwz shebeiCfwz) {
		shebeiCfwz.getSqlMap().put("dsf", dataScopeFilter(shebeiCfwz.getCurrentUser(), "o", "u8"));
		Page<ShebeiCfwz> shebeiPage = super.findPage(page, shebeiCfwz);
		return shebeiPage;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiCfwz shebeiCfwz) {
		if(StringUtils.isNotBlank(shebeiCfwz.getParent().getId())){
			ShebeiCfwz shebeiwz = this.get(shebeiCfwz.getParent().getId());
			//如果所选父级为自身时提示
            if(StringUtils.isNotBlank(shebeiCfwz.getId()) && shebeiwz.getId().equals(shebeiCfwz.getId())){
				throw new ServiceException("不能选择自身为父级，请重新选择！");
			}
			//如果修改父级信息，需要校验父级所属试验室是否与子级所选择的一致
			if(StringUtils.isNotBlank(shebeiwz.getLabInfoId()) && !shebeiwz.getLabInfoId().equals(shebeiCfwz.getLabInfo().getId())){
				throw new ServiceException("所选择的试验室与父级试验室【"+shebeiwz.getLabInfoName()+"】不一致，请重新选择！");
			}
		}
		//试验室Id
		String labInfoId = shebeiCfwz.getLabInfo().getId();
		String labInfoName = labInfoDao.get(labInfoId).getName();
		//如果是修改父级的试验室信息，需要关联修改其子级的试验室数据
		ShebeiCfwz search = new ShebeiCfwz();
		search.setParentIds("%,"+shebeiCfwz.getId()+",%");
		List<ShebeiCfwz> shebeiwzChildList = this.dao.findByParentIdsLike(search);
		for (ShebeiCfwz shebeiwzChild : shebeiwzChildList) {
			shebeiwzChild = this.get(shebeiwzChild.getId());
			shebeiwzChild.setLabInfoId(labInfoId);
			shebeiwzChild.setLabInfoName(labInfoName);
			super.save(shebeiwzChild);
		}
		shebeiCfwz.setLabInfoId(labInfoId);
		shebeiCfwz.setLabInfoName(labInfoName);
		super.save(shebeiCfwz);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiCfwz shebeiCfwz) {
		super.delete(shebeiCfwz);
	}

	@Override
	public String getAllName(ShebeiCfwz cfwz,String funcName){
		return super.getAllName(cfwz,funcName);
	}


	public List<ShebeiCfwz> getByName(String name){
		return shebeiCfwzDao.getByName(name);
	}
}