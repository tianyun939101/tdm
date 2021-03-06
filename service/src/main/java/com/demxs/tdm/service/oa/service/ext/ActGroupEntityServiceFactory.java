package com.demxs.tdm.service.oa.service.ext;

import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Activiti Group Entity Factory
 * @author ThinkGem
 * @version 2013-11-03
 */
public class ActGroupEntityServiceFactory implements SessionFactory {
	
	@Autowired
	private ActGroupEntityService actGroupEntityService;
	
	@Override
	public Class<?> getSessionType() {
		// 返回原始的GroupIdentityManager类型
		return GroupIdentityManager.class;
	}

	@Override
	public Session openSession() {
		// 返回自定义的GroupEntityManager实例
		return actGroupEntityService;
	}

}
