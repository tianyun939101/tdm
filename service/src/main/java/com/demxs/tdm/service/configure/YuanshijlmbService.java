package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.YuanshijlmbDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.configure.Yuanshijlmb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 原始记录模板Service
 * @author 张仁
 * @version 2017-06-08
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YuanshijlmbService extends CrudService<YuanshijlmbDao, Yuanshijlmb> implements IYuanshimbservice {

	@Override
	public Yuanshijlmb get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Yuanshijlmb> findList(Yuanshijlmb yuanshijlmb) {
		return super.findList(yuanshijlmb);
	}
	
	@Override
	public Page<Yuanshijlmb> findPage(Page<Yuanshijlmb> page, Yuanshijlmb yuanshijlmb) {
		return super.findPage(page, yuanshijlmb);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Yuanshijlmb yuanshijlmb) {
		super.save(yuanshijlmb);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Yuanshijlmb yuanshijlmb) {
		super.delete(yuanshijlmb);
	}
	/**
	 * 获取原始记录模板名称
	 * @param yuanshijlmbid
	 * @return
	 */
	@Override
	public String getYuanshijlmbmc(String yuanshijlmbid) {
		if (StringUtils.isNotBlank(yuanshijlmbid)){
			return this.get(yuanshijlmbid).getName();
		}
		return "";
	}

	/**
	 * 获取原始记录模板的配置的采集规则id
	 * @param yuanshijlmbid
	 * @return
	 */
	@Override
	public String getYuanshijlcaijigzid(String yuanshijlmbid){
		if (StringUtils.isNotBlank(yuanshijlmbid)){
			return this.get(yuanshijlmbid).getCaijigzid();
		}
		return "";
	}
}