package com.demxs.tdm.common.service;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.TreeEntity;
import com.demxs.tdm.common.utils.Reflections;
import com.demxs.tdm.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Service基类
 * @author ThinkGem
 * @version 2014-05-16
 */
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public abstract class TreeService<D extends TreeDao<T>, T extends TreeEntity<T>> extends CrudService<D, T> {
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(T entity) {
		
		@SuppressWarnings("unchecked")
		Class<T> entityClass = Reflections.getClassGenricType(getClass(), 1);
		
		// 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
		if (entity.getParent() == null || StringUtils.isBlank(entity.getParentId())
				|| "0".equals(entity.getParentId())){
			entity.setParent(null);
		}else{
			entity.setParent(super.get(entity.getParentId()));
		}
		if (entity.getParent() == null){
			T parentEntity = null;
			try {
				parentEntity = entityClass.getConstructor(String.class).newInstance("0");
			} catch (Exception e) {
				throw new ServiceException(e);
			}
			entity.setParent(parentEntity);
			entity.getParent().setParentIds(StringUtils.EMPTY);
		}
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = entity.getParentIds();

		// 设置新的父节点串
		entity.setParentIds(entity.getParent().getParentIds()+entity.getParent().getId()+",");
		
		// 保存或更新实体
		super.save(entity);
		
		// 更新子节点 parentIds
		T o = null;
		try {
			o = entityClass.newInstance();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		o.setParentIds("%,"+entity.getId()+",%");
		List<T> list = dao.findByParentIdsLike(o);
		for (T e : list){
			if (e.getParentIds() != null && oldParentIds != null){
				e.setParentIds(e.getParentIds().replace(oldParentIds, entity.getParentIds()));
				preUpdateChild(entity, e);
				dao.updateParentIds(e);
			}
		}
		
	}
	
	/**
	 * 预留接口，用户更新子节前调用
	 * @param childEntity
	 */
	protected void preUpdateChild(T entity, T childEntity) {
		
	}


	/**
	 * 获取全名称
	 * @param entity
	 * @param funcName 哪个属性
	 * @return
	 */
	protected  String getAllName(T entity,String funcName){
		StringBuilder allName = new StringBuilder();
		T endEntity = get(entity.getId());
		String paentIds = endEntity == null ? "" : endEntity.getParentIds();
		if(StringUtils.isNotEmpty(paentIds)){
			if(paentIds.indexOf(",")>-1){
				String[] idArr = paentIds.split(",");
				for(String id:idArr){
					if(id == null || id.equals("") || id.equals("0")){//表示根节点 不需要查询

					}else{
						allName.append(getFieldValueByName(funcName,get(id)));
					}
				}
			}
		}
		allName.append(getFieldValueByName(funcName,endEntity));
		return allName.toString();
	}


	/**
	 * 根据属性名获取属性值
	 * @param fieldName
	 * @param o
	 * @return
	 */
	private Object getFieldValueByName(String fieldName, Object o) {
		try {
			String firstLetter = fieldName.substring(0, 1).toUpperCase();
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			return null;
		}
	}

}
