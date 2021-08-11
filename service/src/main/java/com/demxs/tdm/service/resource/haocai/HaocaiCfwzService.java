package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.haocai.HaocaiCfwzDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.consumeables.HaocaiCfwz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 耗材存放位置Service
 * @author zhangdengcai
 * @version 2017-07-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiCfwzService extends TreeService<HaocaiCfwzDao, HaocaiCfwz> {

	@Autowired
	private LabInfoDao labInfoDao;

	@Override
	public HaocaiCfwz get(String id) {
		HaocaiCfwz p = this.dao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public Page<HaocaiCfwz> findPage(Page<HaocaiCfwz> page, HaocaiCfwz haocaiCfwz) {
		haocaiCfwz.getSqlMap().put("dsf", dataScopeFilter(haocaiCfwz.getCurrentUser(), "o", "u8"));
		Page<HaocaiCfwz> haocaiPage = super.findPage(page, haocaiCfwz);
		return haocaiPage;
	}

	@Override
	public List<HaocaiCfwz> findList(HaocaiCfwz haocaiCfwz) {
		if (StringUtils.isNotBlank(haocaiCfwz.getParentIds())){
			haocaiCfwz.setParentIds(","+haocaiCfwz.getParentIds()+",");
		}
		haocaiCfwz.getSqlMap().put("dsf", dataScopeFilter(haocaiCfwz.getCurrentUser(), "o", "u8"));
		return super.findList(haocaiCfwz);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiCfwz haocaiCfwz) {
		if(StringUtils.isNotBlank(haocaiCfwz.getParent().getId())){
			HaocaiCfwz haicaiwz = this.get(haocaiCfwz.getParent().getId());
			//如果所选父级为自身时提示
			if(StringUtils.isNotBlank(haocaiCfwz.getId()) && haicaiwz.getId().equals(haocaiCfwz.getId())){
				throw new ServiceException("不能选择自身为父级，请重新选择！");
			}
			//如果修改父级信息，需要校验父级所属试验室是否与子级所选择的一致
			if(StringUtils.isNotBlank(haicaiwz.getLabInfoId()) && !haicaiwz.getLabInfoId().equals(haocaiCfwz.getLabInfo().getId())){
				throw new ServiceException("所选择的试验室与父级试验室【"+haicaiwz.getLabInfoName()+"】不一致，请重新选择！");
			}
		}
		//试验室Id
		String labInfoId = haocaiCfwz.getLabInfo().getId();
		String labInfoName = labInfoDao.get(labInfoId).getName();
		//如果是修改父级的试验室信息，需要关联修改其子级的试验室数据
		HaocaiCfwz search = new HaocaiCfwz();
		search.setParentIds("%,"+haocaiCfwz.getId()+",%");
		List<HaocaiCfwz> haocaiwzChildList = this.dao.findByParentIdsLike(search);
		for (HaocaiCfwz haocaiwzChild : haocaiwzChildList) {
			haocaiwzChild = this.get(haocaiwzChild.getId());
			haocaiwzChild.setLabInfoId(labInfoId);
			haocaiwzChild.setLabInfoName(labInfoName);
			super.save(haocaiwzChild);
		}
		haocaiCfwz.setLabInfoId(labInfoId);
		haocaiCfwz.setLabInfoName(labInfoName);
		super.save(haocaiCfwz);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiCfwz haocaiCfwz) {
		super.delete(haocaiCfwz);
	}

}