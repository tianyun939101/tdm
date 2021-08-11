package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiKcDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.haocai.HaocaiKc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 耗材库存Service
 * @author zhangdengcai
 * @version 2017-09-01
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiKcService extends CrudService<HaocaiKcDao, HaocaiKc> {

	@Override
	public HaocaiKc get(String id) {
		return super.get(id);
	}

	@Override
	public List<HaocaiKc> findList(HaocaiKc haocaiKc) {
		return super.findList(haocaiKc);
	}
	
	@Override
	public Page<HaocaiKc> findPage(Page<HaocaiKc> page, HaocaiKc haocaiKc) {
		return super.findPage(page, haocaiKc);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiKc haocaiKc) {
		super.save(haocaiKc);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiKc haocaiKc) {
		super.delete(haocaiKc);
	}

	/**
	 * 根据 耗材名称和型号 精确查询
	 * @param haocaimc 耗材名称
	 * @param guigexh 耗材型号
	 * @return
	 */
	public HaocaiKc findListByMcXh(String haocaimc, String guigexh) {
		HaocaiKc haocaiKc = new HaocaiKc();
		haocaiKc.setHaocaimc(haocaimc);
		haocaiKc.setGuigexh(guigexh);
		List<HaocaiKc> kcs = this.dao.findListByMcXh(haocaiKc);
		if (kcs!=null && !kcs.isEmpty()) {
			haocaiKc = kcs.get(0);
		} else {
			haocaiKc = null;
		}
		return haocaiKc;
	}
}