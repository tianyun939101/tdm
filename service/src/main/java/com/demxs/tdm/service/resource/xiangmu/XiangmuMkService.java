package com.demxs.tdm.service.resource.xiangmu;

import com.demxs.tdm.dao.resource.xiangmu.XiangmuMkDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.xiangmu.XiangmuMk;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目模块信息维护Service
 * @author 詹小梅
 * @version 2017-06-14
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class XiangmuMkService extends CrudService<XiangmuMkDao, XiangmuMk> {

	@Override
	public XiangmuMk get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<XiangmuMk> findList(XiangmuMk xiangmuMk) {
		return super.findList(xiangmuMk);
	}
	
	@Override
	public Page<XiangmuMk> findPage(Page<XiangmuMk> page, XiangmuMk xiangmuMk) {
//		page.setOrderBy("a.update_date desc");
		/*if(UserUtils.getUser()!=null){
			//xiangmuMk.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			xiangmuMk.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}*/
		return super.findPage(page, xiangmuMk);
	}

	/**
	 * 保存项目模块信息
	 * @param entity
	 */
	@Override
	public void save(XiangmuMk entity) {
		if(StringUtils.isNotBlank(entity.getId())){
			if(entity.getFuzhuj() != null && !"0".equals(entity.getFuzhuj())){
				entity.setMokuaimcs(super.get(entity.getFuzhuj()).getMokuaimcs()+"_"+entity.getMokuaimc());
				entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
			}else{
				entity.setFuzhujs(",0,");
				entity.setFuzhuj("0");
				entity.setMokuaimcs(entity.getMokuaimc());
			}
			super.save(entity);
			this.modifyYxxByfzj(entity);
			if(StringUtils.isNotBlank(entity.getId())){
				//更新数据中心 模块类别父主键
				/*IDataSjzxService iDataSjzxService = SpringContextHolder.getBean(IDataSjzxService.class);
				iDataSjzxService.updateMKLB(entity.getId(),entity.getFuzhujs());*/
			}
		}else{
			if (entity.getFuzhuj() != null && !"0".equals(entity.getFuzhuj())){
				entity.setFuzhujs(super.get(entity.getFuzhuj()).getFuzhujs()+entity.getFuzhuj()+",");
				entity.setMokuaimcs(super.get(entity.getFuzhuj()).getMokuaimcs()+"_"+entity.getMokuaimc());
				entity.setXiangmuzj(super.get(entity.getFuzhuj()).getXiangmuzj());
				//Xiangmu xiangmu = xiangmuService.get(super.get(entity.getFuzhuj()).getXiangmuzj());
				//entity.setSuoshuxmlb(","+entity.getXiangmuzj()+","+xiangmu.getXiangmulb()+",");//模块所属项目和类别(便于查询某个模块属于哪个项目或项目类别)
			}else{
				entity.setFuzhujs(",0,");
				entity.setFuzhuj("0");
				entity.setMokuaimcs(entity.getMokuaimc());
				//entity.setSuoshuxmlb(","+entity.getXiangmuzj()+",");//模块所属项目和类别(便于查询某个模块属于哪个项目或项目类别)
			}
			super.save(entity);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(XiangmuMk xiangmuMk) {
		super.delete(xiangmuMk);
	}

	/**
	 * 更新有效性
	 * @param entity
	 */
	public void modifyYxxByfzj(XiangmuMk entity){
		String fuzhuj = ","+entity.getId()+",";
		entity.setFuzhuj(fuzhuj);
		this.dao.modifyYxxByfzj(entity);
	}
}