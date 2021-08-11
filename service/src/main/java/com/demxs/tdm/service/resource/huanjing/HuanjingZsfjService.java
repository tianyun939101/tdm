package com.demxs.tdm.service.resource.huanjing;

import com.demxs.tdm.dao.resource.huanjing.HuanjingZsfjDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.huanjing.HuanjingZsfj;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 环境数据，设置展示的房间Service
 * @author zhangdengcai
 * @version 2017-10-09
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HuanjingZsfjService extends CrudService<HuanjingZsfjDao, HuanjingZsfj> {

	@Override
	public HuanjingZsfj get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HuanjingZsfj> findList(HuanjingZsfj huanjingZsfj) {
		return super.findList(huanjingZsfj);
	}
	
	@Override
	public Page<HuanjingZsfj> findPage(Page<HuanjingZsfj> page, HuanjingZsfj huanjingZsfj) {
		page.setOrderBy("a.fangjianmc");
		return super.findPage(page, huanjingZsfj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HuanjingZsfj huanjingZsfj) {
		super.save(huanjingZsfj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HuanjingZsfj huanjingZsfj) {
		super.delete(huanjingZsfj);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void batchDelete(String ids){
		if(StringUtils.isNotBlank(ids)){
			String[] idArr = ids.split(",");
			HuanjingZsfj huanjingZsfj = new HuanjingZsfj();
			huanjingZsfj.setArrIDS(idArr);
			this.dao.batchDelete(huanjingZsfj);
		}
	}
}