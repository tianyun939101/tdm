package com.demxs.tdm.service.resource.feiyong;

import com.demxs.tdm.dao.resource.feiyong.FeiyongDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.domain.resource.feiyong.Feiyong;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 试验费用Service
 * @author sunjunhui
 * @version 2017-11-07
 */
@Service
@Transactional(readOnly = true)
public class FeiyongService extends CrudService<FeiyongDao, Feiyong> {

	public Feiyong get(String id) {
		return super.get(id);
	}
	
	public List<Feiyong> findList(Feiyong feiyong) {
		return super.findList(feiyong);
	}
	
	public Page<Feiyong> findPage(Page<Feiyong> page, Feiyong feiyong) {

		feiyong.getSqlMap().put("dsf", dataScopeFilter(feiyong.getCurrentUser(), "o", "u8"));
		return super.findPage(page, feiyong);
	}
	
	@Transactional(readOnly = false)
	public void save(Feiyong feiyong) {
		super.save(feiyong);
	}
	
	@Transactional(readOnly = false)
	public void delete(Feiyong feiyong) {
		super.delete(feiyong);
	}

	@Transactional(readOnly = false)
	public void updateCost(String id,String cost){
		this.dao.updateCost(id,cost);
	}

	@Transactional(readOnly = false)
	public void updateWeights(String id,String weights){
		this.dao.updateWeights(id,weights);
	}

	public Feiyong findByItemId(String itemId,String labId) {
		return dao.findByItemIdAndLabId(itemId,labId);
	}


	
}