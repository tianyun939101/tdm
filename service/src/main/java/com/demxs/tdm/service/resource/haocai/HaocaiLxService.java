package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiLxDao;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 耗材类型Service
 * @author zhangdengcai
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiLxService extends TreeService<HaocaiLxDao, HaocaiLx> {

	@Override
	public HaocaiLx get(String id) {
		HaocaiLx p = this.dao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public List<HaocaiLx> findList(HaocaiLx haocaiCfwz) {
		if (StringUtils.isNotBlank(haocaiCfwz.getParentIds())){
			haocaiCfwz.setParentIds(","+haocaiCfwz.getParentIds()+",");
		}
		return super.findList(haocaiCfwz);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiLx haocaiCfwz) {
		super.save(haocaiCfwz);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiLx haocaiCfwz) {
		super.delete(haocaiCfwz);
	}

}