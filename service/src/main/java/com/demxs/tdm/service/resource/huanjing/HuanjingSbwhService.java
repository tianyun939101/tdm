package com.demxs.tdm.service.resource.huanjing;

import com.demxs.tdm.dao.resource.huanjing.HuanjingSbwhDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.huanjing.HuanjingSbwh;
import com.demxs.tdm.service.resource.shebei.ShebeiCfwzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 设备环境维护Service
 * @author zhangdengcai
 * @version 2017-07-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HuanjingSbwhService extends CrudService<HuanjingSbwhDao, HuanjingSbwh> {
	@Autowired
	private ShebeiCfwzService shebeiCfwzService;

	@Override
	public HuanjingSbwh get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HuanjingSbwh> findList(HuanjingSbwh huanjingSbwh) {
		return super.findList(huanjingSbwh);
	}

	/**
	 * 分页查询列表
	 * @param page 分页对象
	 * @param huanjingSbwh
	 * @return
	 */
	@Override
	public Page<HuanjingSbwh> findPage(Page<HuanjingSbwh> page, HuanjingSbwh huanjingSbwh) {
//		page.setOrderBy("a.update_date desc");
//		if(UserUtils.getUser()!=null){
//			huanjingSbwh.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
//		}
//		Page<HuanjingSbwh> dataValue = super.findPage(page, huanjingSbwh);
//		List<HuanjingSbwh> sbwhs = dataValue.getList();
//		if (sbwhs!=null && !sbwhs.isEmpty()) {
//			for (HuanjingSbwh sbwh : sbwhs) {
//				if(StringUtils.isNotBlank(sbwh.getShebeifj())){
//					sbwh.setShebeifjmc(shebeiCfwzService.cunfangddmc(sbwh.getShebeifj())==null?"":shebeiCfwzService.cunfangddmc(sbwh.getShebeifj()));
//				}
//			}
//		}
//		dataValue.setList(sbwhs);
//		return dataValue;
		return null;
	}

	/**
	 * 分页查询列表
	 * @param page 分页对象
	 * @param huanjingSbwh
	 * @return
	 */
	public Page<HuanjingSbwh> findPageForOther(Page<HuanjingSbwh> page, HuanjingSbwh huanjingSbwh) {
//		page.setOrderBy("a.update_date desc");
//		Page<HuanjingSbwh> dataValue = super.findPage(page, huanjingSbwh);
//		List<HuanjingSbwh> sbwhs = dataValue.getList();
//		if (sbwhs!=null && !sbwhs.isEmpty()) {
//			for (HuanjingSbwh sbwh : sbwhs) {
//				if(StringUtils.isNotBlank(sbwh.getShebeifj())){
//					sbwh.setShebeifjmc(shebeiCfwzService.cunfangddmc(sbwh.getShebeifj())==null?"":shebeiCfwzService.cunfangddmc(sbwh.getShebeifj()));
//				}
//			}
//		}
//		dataValue.setList(sbwhs);
//		return dataValue;
		return null;
	}

	/**
	 * 保存
	 * @param huanjingSbwh
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HuanjingSbwh huanjingSbwh) {
		super.save(huanjingSbwh);
	}

	/**
	 * 详情
	 * @param huanjingSbwh
	 * @return
	 */
	@Override
	public HuanjingSbwh get(HuanjingSbwh huanjingSbwh) {
//		HuanjingSbwh sbwh = super.get(huanjingSbwh);
//		if(sbwh!=null){
//			if(StringUtils.isNotBlank(sbwh.getShebeifj())){
//				sbwh.setShebeifjmc(shebeiCfwzService.cunfangddmc(sbwh.getShebeifj())==null?"":shebeiCfwzService.cunfangddmc(sbwh.getShebeifj()));
//			}
//		}
//		return sbwh;
		return null;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HuanjingSbwh huanjingSbwh) {
		super.delete(huanjingSbwh);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteMore(String ids){
		String[] idArray = null;
		if(StringUtils.isNotBlank(ids)){
			if(ids.contains(",")){
				idArray = ids.split(",");
			}else {
				idArray = new String[1];
				idArray[0] = ids;
			}
		}
		HuanjingSbwh huanjingSbwh = new HuanjingSbwh();
		huanjingSbwh.setArrIDS(idArray);
		this.dao.deleteMore(huanjingSbwh);
	}
}