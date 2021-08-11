package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.ZyProjectTreeDao;
import com.demxs.tdm.dao.resource.haocai.HaocaiLxDao;
import com.demxs.tdm.domain.dataCenter.ZyProjectTree;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目建设Service
 * @author zwm
 * @version 2020-12-9
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyProjectTreeService extends TreeService<ZyProjectTreeDao, ZyProjectTree> {

	@Autowired
	private  ZyProjectTreeDao  zyProjectTreeDao;

	@Override
	public ZyProjectTree get(String id) {
		ZyProjectTree p = zyProjectTreeDao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(zyProjectTreeDao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public List<ZyProjectTree> findList(ZyProjectTree entity) {
		if (StringUtils.isNotBlank(entity.getParentIds())){
			entity.setParentIds(","+entity.getParentIds()+",");
		}
		return super.findList(entity);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZyProjectTree entity) {
		super.save(entity);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZyProjectTree entity) {
		super.delete(entity);
	}

	public List<ZyProjectTree> getDept(String parentId){
		return zyProjectTreeDao.getDept(parentId);
	}

}