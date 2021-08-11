package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinLbDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.YangpinLb;
import com.demxs.tdm.domain.resource.yangpin.YangpinLbsx;
import com.demxs.tdm.comac.common.constant.YpConstans;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品类型维护Service
 * @author 詹小梅
 * @version 2017-06-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinLbService extends CrudService<YangpinLbDao, YangpinLb> {
	@Autowired
	private YangpinLbsxService yangpinLbsxService;

	@Override
	public YangpinLb get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinLb> findList(YangpinLb yangpinLb) {
		return super.findList(yangpinLb);
	}
	@Override
	public Page<YangpinLb> findPage(Page<YangpinLb> page, YangpinLb yangpinLb) {
		if(UserUtils.getUser()!=null){
			yangpinLb.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page,yangpinLb);
	}

	
	public Page<YangpinLb> findYangpinLb(Page<YangpinLb> page, YangpinLb yangpinLb) {
		String dsf = dataScopeFilter(UserUtils.getUser(), "o", "u");
		page.setOrderBy("a.update_date desc");
		yangpinLb.setPage(page);
		if(UserUtils.getUser()!=null){
			yangpinLb.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		List<YangpinLb> list = this.dao.findYangpinLb(yangpinLb);
		page.setList(list);
		return page;
	}


	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinLb entity) {
		String yangpinlbid="";
		if(StringUtils.isNotBlank(entity.getId())){
			yangpinlbid = entity.getId();
			if (!entity.getFuzhuj().equals("0")){
				entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
				entity.setLeixingmcs(super.get(entity.getFuzhuj()).getLeixingmcs()+"_"+entity.getLeixingmc());
			}else{
				entity.setFuzhujs(",0,");
				entity.setLeixingmcs(entity.getLeixingmc());
			}
			//样品列表属性
			YangpinLbsx[] lbsxs = entity.getYangpinsx();
			yangpinLbsxService.deleteByLx(yangpinlbid);//先删除 类型和属性 关联表
			if(lbsxs!=null) {
				for (int i = 0; i < lbsxs.length; i++) {
					lbsxs[i].setYangpinlbzj(yangpinlbid);
					yangpinLbsxService.save(lbsxs[i]);
					/*if (lbsxs[i].getStatus()==1){//需删除
						yangpinLbsxService.delete(lbsxs[i]);
					}else{
						lbsxs[i].setYangpinlbzj(yangpinlbid);
						yangpinLbsxService.save(lbsxs[i]);
					}*/
				}
			}
			super.save(entity);
			if(entity.getYouxiaox().equals(YpConstans.optYouxiaox.WUXIAO)){
				this.modifyYxxByfzj(entity);
			}
			if(StringUtils.isNotBlank(entity.getId())){
				//更新数据中心 样品类别父主键
				/*IDataSjzxService iDataSjzxService = SpringContextHolder.getBean(IDataSjzxService.class);
				iDataSjzxService.updateYPLB(entity.getId(),entity.getFuzhujs());*/
			}
		}else{
			yangpinlbid = IdGen.uuid();
			entity.preInsert();
			entity.setId(yangpinlbid);
			if (!entity.getFuzhuj().equals("0")){
				entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
				entity.setLeixingmcs(super.get(entity.getFuzhuj()).getLeixingmcs()+"_"+entity.getLeixingmc());
			}else{
				entity.setFuzhujs(",0,");
				entity.setLeixingmcs(entity.getLeixingmc());
			}
			//样品列表属性
			YangpinLbsx[] lbsxs = entity.getYangpinsx();
			if(lbsxs!=null) {
				for (int i = 0; i < lbsxs.length; i++) {
					lbsxs[i].setYangpinlbzj(yangpinlbid);
					yangpinLbsxService.save(lbsxs[i]);
				}
			}
			this.dao.insert(entity);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinLb yangpinLb) {
		super.delete(yangpinLb);
	}

	/**
	 * 更新有效性
	 * @param entity
	 */
	public void modifyYxxByfzj(YangpinLb entity){
		String fuzhuj = ","+entity.getId()+",";
		entity.setFuzhuj(fuzhuj);
		this.dao.modifyYxxByfzj(entity);
	}
	
}