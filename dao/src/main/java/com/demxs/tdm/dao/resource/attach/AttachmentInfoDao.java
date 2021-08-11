package com.demxs.tdm.dao.resource.attach;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 附件管理DAO接口
 * @author sunjunhui
 * @version 2017-11-23
 */
@MyBatisDao
public interface AttachmentInfoDao extends CrudDao<AttachmentInfo> {

    void updateBusinessId(@Param("id")String id,@Param("businessId")String businessId);
	
}