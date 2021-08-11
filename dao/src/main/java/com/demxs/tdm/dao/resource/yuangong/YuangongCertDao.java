package com.demxs.tdm.dao.resource.yuangong;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.domain.resource.yuangong.YuangongCert;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工DAO接口
 * @author sunjunhui
 * @version 2017-11-08
 */
@MyBatisDao
public interface YuangongCertDao extends CrudDao<YuangongCert> {

}