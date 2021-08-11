package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.BaogaombDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.configure.Baogaomb;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报告模板Service
 * @author 张仁
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BaogaombService extends CrudService<BaogaombDao, Baogaomb> implements IBaogaombservice{

	@Override
	public Baogaomb get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Baogaomb> findList(Baogaomb baogaomb) {
		return super.findList(baogaomb);
	}
	
	@Override
	public Page<Baogaomb> findPage(Page<Baogaomb> page, Baogaomb baogaomb) {
		return super.findPage(page, baogaomb);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Baogaomb baogaomb) {
		super.save(baogaomb);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Baogaomb baogaomb) {
		super.delete(baogaomb);
	}


	/**
	 * @Author：谭冬梅
	 * @param： * @param arrIds
	 * @Description：
	 * @Date：10:50 2017/7/12
	 * @Return：
	 * @Exception：
	 */
	@Override
	public List<Map> getBaogaomblist(String arrIds) {
		Baogaomb entity = new Baogaomb();
		entity.setArrIDS(arrIds.split(","));
		List<Baogaomb> lis = this.findList(entity);
		List<Map> maplist = new ArrayList<Map>();
		Map m = null;
		for(Baogaomb e :lis){
			m= new HashMap();
			m.put("id",e.getId());
			m.put("name",e.getName());
			maplist.add(m);
		}
		return maplist;
	}
}