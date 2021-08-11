package com.demxs.tdm.dao.cj;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.cj.Guize;

import java.util.List;
import java.util.Map;

/**
 * 采集规则DAO接口
 * @author 张仁
 * @version 2017-08-11
 */
@MyBatisDao
public interface GuizeDao extends CrudDao<Guize> {
    public List<Map> findGuizeZiduan(Map maps);

    public List<Map> getAddressInfo(Map maps);

    public Map findGuiZeMingCheng(Guize guize);

    public List<Map> findGuiZe(Guize guize);
}