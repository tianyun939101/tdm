package com.demxs.tdm.dao.resource.yangpin;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yangpin.YangpinLb;

import java.util.List;

/**
 * 样品类型维护DAO接口
 * @author 詹小梅
 * @version 2017-06-16
 */
@MyBatisDao
public interface YangpinLbDao extends CrudDao<YangpinLb> {

     List<YangpinLb> findYangpinLb(YangpinLb yangpinLb);

     /**
      * 更新有效性
      * @param entity
      */
     public void modifyYxxByfzj(YangpinLb entity);
	
}