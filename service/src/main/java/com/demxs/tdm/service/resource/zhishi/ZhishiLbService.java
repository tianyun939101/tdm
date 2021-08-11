package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiLbDao;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.zhishi.ZhishiLb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识类别Service
 * @author 詹小梅
 * @version 2017-06-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiLbService extends TreeService<ZhishiLbDao, ZhishiLb> {

	public ZhishiLb get(String id) {
		ZhishiLb p = this.dao.get(id);
		if(null != p && p.getParent()!=null && StringUtils.isNotBlank(p.getParent().getId())){
			p.setParent(this.dao.get(p.getParentId()));
		}
		return p;
	}

	public List<ZhishiLb> findList(ZhishiLb shebeiLx) {
		if (StringUtils.isNotBlank(shebeiLx.getParentIds())){
			shebeiLx.setParentIds(","+shebeiLx.getParentIds()+",");
		}
		return super.findList(shebeiLx);
	}

	@Transactional(readOnly = false)
	public void save(ZhishiLb shebeiLx) {
		super.save(shebeiLx);
	}

	@Transactional(readOnly = false)
	public void delete(ZhishiLb shebeiLx) {
		super.delete(shebeiLx);
	}

	@Override
	public String getAllName(ZhishiLb zhishiLb,String funcName){
		return super.getAllName(zhishiLb,funcName);
	}
}