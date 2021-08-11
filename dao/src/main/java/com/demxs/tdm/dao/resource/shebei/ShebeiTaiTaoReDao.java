package com.demxs.tdm.dao.resource.shebei;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiTaiTaoRe;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 设备台套关联DAO接口
 * @author liangqf
 * @version 2020-01-09
 */
@MyBatisDao
public interface ShebeiTaiTaoReDao extends CrudDao<ShebeiTaiTaoRe> {

    List<Shebei> getShebeiList(@Param("id") String id);

    void removeShebei(@Param("id") String id);

}