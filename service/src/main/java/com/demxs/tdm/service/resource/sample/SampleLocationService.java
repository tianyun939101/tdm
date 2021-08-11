package com.demxs.tdm.service.resource.sample;

import java.util.List;

import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.sample.SampleLocationDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.sample.SampleLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 样品位置管理Service
 * @author sunjunhui
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class SampleLocationService extends TreeService<SampleLocationDao, SampleLocation> {


	@Autowired
	private LabInfoDao labInfoDao;

	public SampleLocation get(String id) {
		SampleLocation p = this.dao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public Page<SampleLocation> findPage(Page<SampleLocation> page, SampleLocation sampleLocation) {
		sampleLocation.getSqlMap().put("dsf", dataScopeFilter(sampleLocation.getCurrentUser(), "o", "u8"));
		Page<SampleLocation> samplePage = super.findPage(page, sampleLocation);
		return samplePage;
	}
	
	public List<SampleLocation> findList(SampleLocation sampleLocation) {
		if (StringUtils.isNotBlank(sampleLocation.getParentIds())){
			sampleLocation.setParentIds(","+sampleLocation.getParentIds()+",");
		}
		sampleLocation.getSqlMap().put("dsf", dataScopeFilter(sampleLocation.getCurrentUser(), "o", "u8"));
		return super.findList(sampleLocation);
	}
	
	@Transactional(readOnly = false)
	public void save(SampleLocation sampleLocation) {
		if(StringUtils.isNotBlank(sampleLocation.getParent().getId())){
			SampleLocation sl = this.get(sampleLocation.getParent().getId());
			//如果所选父级为自身时提示
			if(StringUtils.isNotBlank(sampleLocation.getId()) && sl.getId().equals(sampleLocation.getId())){
				throw new ServiceException("不能选择自身为父级，请重新选择！");
			}
			//如果修改父级信息，需要校验父级所属试验室是否与子级所选择的一致
			if(!sl.getLabInfoId().equals(sampleLocation.getLabInfo().getId())){
				throw new ServiceException("所选择的试验室与父级试验室【"+sl.getLabInfoName()+"】不一致，请重新选择！");
			}
		}
		//试验室Id
		String labInfoId = sampleLocation.getLabInfo().getId();
		String labInfoName = labInfoDao.get(labInfoId).getName();
		//如果是修改父级的试验室信息，需要关联修改其子级的试验室数据
		SampleLocation search = new SampleLocation();
		search.setParentIds("%,"+sampleLocation.getId()+",%");
		List<SampleLocation> slChildList = this.dao.findByParentIdsLike(search);
		for (SampleLocation slChild : slChildList) {
			slChild = this.get(slChild.getId());
			slChild.setLabInfoId(labInfoId);
			slChild.setLabInfoName(labInfoName);
			super.save(slChild);
		}
		sampleLocation.setLabInfoId(labInfoId);
		sampleLocation.setLabInfoName(labInfoName);
		super.save(sampleLocation);
	}
	
	@Transactional(readOnly = false)
	public void delete(SampleLocation sampleLocation) {
		super.delete(sampleLocation);
	}

	/**
	 *
	 * @param sampleLocation
	 * @param funcName 哪个属性
	 * @return
	 */
	public String getAllName(SampleLocation sampleLocation,String funcName){
		return super.getAllName(sampleLocation,funcName);
	}

}