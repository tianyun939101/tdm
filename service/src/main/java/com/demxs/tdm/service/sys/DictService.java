package com.demxs.tdm.service.sys;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.DictDao;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.common.utils.CacheUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 字典Service
 * @author ThinkGem
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	public Date getLastUpdateTime(){
		return dao.getLastUpdateTime();
	}

	public String findSortByType(String type){
		return dao.findSortByType(type);
	}
}
