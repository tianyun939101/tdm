package com.demxs.tdm.dao.configure;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.configure.Baogaomb;

import java.util.List;

/**
 * 报告模板DAO接口
 * @author 张仁
 * @version 2017-06-20
 */
@MyBatisDao
public interface BaogaombDao extends CrudDao<Baogaomb> {
    public List<Baogaomb> findQiyongList(Baogaomb entity);
}