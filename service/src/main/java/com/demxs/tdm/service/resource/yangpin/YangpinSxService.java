package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinSxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.YangpinSx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 样品属性Service
 * @author 詹小梅
 * @version 2017-07-04
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinSxService extends CrudService<YangpinSxDao, YangpinSx> implements IYangpinSxService {
	@Autowired
	private YangpinCkfkdService yangpinCkfkdService;



	@Override
	public YangpinSx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinSx> findList(YangpinSx yangpinSx) {
		return super.findList(yangpinSx);
	}
	
	@Override
	public Page<YangpinSx> findPage(Page<YangpinSx> page, YangpinSx yangpinSx) {
		return super.findPage(page, yangpinSx);
	}

	/**
	 * 1.每加一个属性就往样品出库单中加一个对应的属性，filed1,filed2........filed99......filedN
	 * 2.同时回写到样品属性表中。
	 *   比如加了一个高的属性，在出库单中加一个字段filed1，
	 *   因为两个表的字段不一样无法对应取值，需要将filed1回写到属性表,
	 *   属性表中有一个字段filed 将属性高和filed1 对应上(filed1赋值给filed)
	 * @param yangpinSx 属性实体
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinSx yangpinSx) {
		if(StringUtils.isNoneBlank(yangpinSx.getId())){
			super.save(yangpinSx);//保存属性
		}else{
			super.save(yangpinSx);//保存属性
			yangpinCkfkdService.saveYpsx(yangpinSx);//在出库单中加上该属性。回写是在出库单添加一列成功后。
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinSx yangpinSx) {
		super.delete(yangpinSx);
	}

	/**
	 * 属性删除
	 * @param yangpinSx
	 */
	public void deleteData(YangpinSx yangpinSx){
		this.dao.deleteData(yangpinSx);
	}

	/**
	 * 出库单动态加列后更新样品属性的filed字段值
	 * @param id 属性id
	 * @param filedValue   属性对应的filed value
	 */
	@Override
	public void updateFiled(String id, String filedValue){
		this.dao.updateFiled(id,filedValue);
	}
	
}