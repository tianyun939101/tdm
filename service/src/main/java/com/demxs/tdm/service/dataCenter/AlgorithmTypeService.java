package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.AlgorithmTypeDao;
import com.demxs.tdm.domain.dataCenter.AlgorithmType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 算法类别service
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class AlgorithmTypeService extends TreeService<AlgorithmTypeDao, AlgorithmType> {

	@Override
	public AlgorithmType get(String id) {
		AlgorithmType p = this.dao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public List<AlgorithmType> findList(AlgorithmType algorithmType) {
		if (StringUtils.isNotBlank(algorithmType.getParentIds())){
			algorithmType.setParentIds(","+algorithmType.getParentIds()+",");
		}
		return super.findList(algorithmType);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(AlgorithmType algorithmType) {
		super.save(algorithmType);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(AlgorithmType algorithmType) {
		super.delete(algorithmType);
	}


}