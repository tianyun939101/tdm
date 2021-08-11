package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinLbsxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.yangpin.YangpinLbsx;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品类别属性关系表Service
 * @author 詹小梅
 * @version 2017-07-12
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinLbsxService extends CrudService<YangpinLbsxDao, YangpinLbsx> {

	@Override
	public YangpinLbsx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinLbsx> findList(YangpinLbsx yangpinLbsx) {
		return super.findList(yangpinLbsx);
	}
	
	@Override
	public Page<YangpinLbsx> findPage(Page<YangpinLbsx> page, YangpinLbsx yangpinLbsx) {
		return super.findPage(page, yangpinLbsx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinLbsx yangpinLbsx) {
		super.save(yangpinLbsx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinLbsx yangpinLbsx) {
		super.delete(yangpinLbsx);
	}

	/**
	 * 获取样品类别属性
	 * @param yangpinLbsx
	 * @return
	 */
	public List<YangpinLbsx> findyangpinLbsxsList(YangpinLbsx yangpinLbsx){
		return this.dao.findyangpinLbsxsList(yangpinLbsx);
	}

	/**
	 * 根据样品类型 删除 样品类型和属性管理数据
	 * @param yangpinlbzj
	 */
	public void deleteByLx(String yangpinlbzj){
        YangpinLbsx yangpinLbsx = new YangpinLbsx();
        yangpinLbsx.setYangpinlbzj(yangpinlbzj);
		this.dao.deleteByLx(yangpinLbsx);
	}
}