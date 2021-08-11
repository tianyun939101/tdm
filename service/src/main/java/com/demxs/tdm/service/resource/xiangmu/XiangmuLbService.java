package com.demxs.tdm.service.resource.xiangmu;

import com.demxs.tdm.dao.resource.xiangmu.XiangmuLbDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.xiangmu.XiangmuLb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目类别Service
 * @author 詹小梅
 * @version 2017-06-23
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class XiangmuLbService extends CrudService<XiangmuLbDao, XiangmuLb> {

	@Override
	public XiangmuLb get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<XiangmuLb> findList(XiangmuLb xiangmuLb) {
		return super.findList(xiangmuLb);
	}
	
	@Override
	public Page<XiangmuLb> findPage(Page<XiangmuLb> page, XiangmuLb xiangmuLb) {
//		page.setOrderBy("a.update_date desc");
		/*if(UserUtils.getUser()!=null){
			//xiangmuLb.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "a2", "a1"));
			xiangmuLb.setDsf(dataScopeFilter(UserUtils.getUser(), "a2", "a1"));
			}*/
		return super.findPage(page, xiangmuLb);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(XiangmuLb entity) {
		if (!entity.getFuzhuj().equals("0")){
			entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
		}else{
			entity.setFuzhujs(",0,");
		}
		if(StringUtils.isNotBlank(entity.getId())){
			//更新数据中心 项目类别父主键
			/*IDataSjzxService iDataSjzxService = SpringContextHolder.getBean(IDataSjzxService.class);
			iDataSjzxService.updateXMLB(entity.getFuzhujs(),entity.getId());*/
		}
		super.save(entity);

	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(XiangmuLb xiangmuLb) {
		super.delete(xiangmuLb);
	}


	
}