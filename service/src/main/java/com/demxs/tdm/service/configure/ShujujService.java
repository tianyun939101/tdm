package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.ShujujDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Shujuj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 数据集Service
 * @author 张仁
 * @version 2017-08-03
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShujujService extends CrudService<ShujujDao, Shujuj> {
	@Autowired
	private ShujujDao shujujDao;
	@Override
	public Shujuj get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Shujuj> findList(Shujuj shujuj) {
		return super.findList(shujuj);
	}
	
	@Override
	public Page<Shujuj> findPage(Page<Shujuj> page, Shujuj shujuj) {
		return super.findPage(page, shujuj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Shujuj shujuj) {
		shujujDao.insert(shujuj);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void update(Shujuj shujuj) {
		shujujDao.update(shujuj);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Shujuj shujuj) {
		super.delete(shujuj);
	}

	public  List<String> queryTables(){
		return shujujDao.queryTables();
	}

	public List<Map> queryMeta(String tname){
		return shujujDao.queryMeta(tname);
	}

	public List<Map> queryDataSetMeta(String datasetid){
		return shujujDao.queryDataSetMeta(datasetid);
	}
}