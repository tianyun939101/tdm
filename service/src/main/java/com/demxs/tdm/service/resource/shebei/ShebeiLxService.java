package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.resource.shebei.ShebeiLxDao;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.ShebeiLx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备类型Service
 * @author zhangdengcai
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiLxService extends TreeService<ShebeiLxDao, ShebeiLx> {

	@Override
	public ShebeiLx get(String id) {
		ShebeiLx p = this.dao.get(id);
		if(p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	@Override
	public List<ShebeiLx> findList(ShebeiLx shebeiLx) {
		if (StringUtils.isNotBlank(shebeiLx.getParentIds())){
			shebeiLx.setParentIds(","+shebeiLx.getParentIds()+",");
		}
		return super.findList(shebeiLx);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiLx shebeiLx) {
		super.save(shebeiLx);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiLx shebeiLx) {
		super.delete(shebeiLx);
	}

}