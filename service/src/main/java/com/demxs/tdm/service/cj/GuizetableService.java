package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.CjTableDao;
import com.demxs.tdm.dao.cj.GuizeDao;
import com.demxs.tdm.dao.cj.GuizetableDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.domain.cj.CjTable;
import com.demxs.tdm.domain.cj.Guize;
import com.demxs.tdm.domain.cj.Guizetable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 采集表格关系Service
 * @author 张仁
 * @version 2017-08-19
 */
@Service
@Transactional(readOnly = true)
public class GuizetableService extends CrudService<GuizetableDao, Guizetable> {
	@Autowired
	private GuizeDao guizeDao;
	@Autowired
	private CjTableDao cjTableDao;

	@Override
	public Guizetable get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Guizetable> findList(Guizetable guizetable) {
		return super.findList(guizetable);
	}
	
	@Override
	public Page<Guizetable> findPage(Page<Guizetable> page, Guizetable guizetable) {
		return super.findPage(page, guizetable);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(Guizetable guizetable) {
		String guizeid = guizetable.getGuizeid();
		Guize guize = guizeDao.get(guizeid);
		String cjTableid = guizetable.getTableid();
		CjTable cjTable = cjTableDao.get(cjTableid);
		if("colinfo".equals(cjTable.getBiaolx())){
			guize.setInfoid(cjTableid);
		}else if(DictUtils.getDictValue("数据表","cj_biaolx",null).equals(cjTable.getBiaolx())){
			guize.setDataid(cjTableid);
		}
		guize.preUpdate();
		guizeDao.update(guize);
		super.save(guizetable);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Guizetable guizetable) {
		super.delete(guizetable);
	}
	
}