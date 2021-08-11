package com.demxs.tdm.service.resource.xiangmu;

import com.demxs.tdm.dao.resource.xiangmu.XiangmuCzjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.xiangmu.XiangmuCzjl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目操作记录Service
 * @author 谭冬梅
 * @version 2017-09-04
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class XiangmuCzjlService extends CrudService<XiangmuCzjlDao, XiangmuCzjl> {

	@Override
	public XiangmuCzjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<XiangmuCzjl> findList(XiangmuCzjl xiangmuCzjl) {
		return super.findList(xiangmuCzjl);
	}
	
	@Override
	public Page<XiangmuCzjl> findPage(Page<XiangmuCzjl> page, XiangmuCzjl xiangmuCzjl) {
		return super.findPage(page, xiangmuCzjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(XiangmuCzjl xiangmuCzjl) {
		super.save(xiangmuCzjl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(XiangmuCzjl xiangmuCzjl) {
		super.delete(xiangmuCzjl);
	}
	
}