package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiTaiTao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 设备台套DAO接口
 * @author liangqf
 * @version 2020-01-09
 */
@MyBatisDao
public interface ShebeiTaiTaoDao extends CrudDao<ShebeiTaiTao> {

}