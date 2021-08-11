package com.demxs.tdm.dao.sys;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.sys.SysAttachment;

/**
 * 附件管理DAO接口
 * @author 张仁
 * @version 2017-07-01
 */
@MyBatisDao
public interface SysAttachmentDao extends CrudDao<SysAttachment> {
	
}